package api.desafio.jwt;

import api.desafio.service.exception.ForbiddenExeceptionHandle;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
@Slf4j
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private final JwtUserDetailsService jwtUserDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        final String authorizationHeader = request.getHeader(JwtUtils.JWT_AUTHORIZATION);

        try {

            if (authorizationHeader == null || !authorizationHeader.startsWith(JwtUtils.JWT_BEARER)) {
                log.debug("Requisição sem JWT ou header malformado. Prosseguindo sem autenticação.");
                filterChain.doFilter(request, response);
                return;
            }

            final String token = authorizationHeader.substring(JwtUtils.JWT_BEARER.length()).trim();

            if (!JwtUtils.isTokenValid(token)) {
                log.warn("Tentativa de acesso com token inválido ou expirado.");
                throw new ForbiddenExeceptionHandle("Token inválido ou expirado.");
            }

            final String userEmail = JwtUtils.getUserEmailFromToken(token);

            if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                authenticateUser(request, userEmail);
            }

            filterChain.doFilter(request, response);

        } catch (ForbiddenExeceptionHandle ex) {
            log.error("Erro de autorização JWT: {}", ex.getMessage());
            response.sendError(HttpServletResponse.SC_FORBIDDEN, ex.getMessage());
        } catch (Exception ex) {
            log.error("Erro inesperado durante a validação do JWT.", ex);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erro interno no processamento do token JWT.");
        }
    }

    private void authenticateUser(HttpServletRequest request, String username) {
        UserDetails userDetails = jwtUserDetailsService.loadUserByUsername(username);

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());

        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        log.debug("Usuário autenticado via JWT: {}", username);
    }
}
