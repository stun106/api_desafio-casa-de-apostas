package api.desafio.service.impl;

import api.desafio.jwt.JwtToken;
import api.desafio.jwt.JwtUserDetailsService;
import api.desafio.model.Usuario;
import api.desafio.repository.UsuarioReprository;
import api.desafio.service.AuthService;
import api.desafio.service.exception.EntityNotfoundException;
import api.desafio.service.exception.ForbiddenExeceptionHandle;
import api.desafio.service.exception.UnnauthorizedException;
import api.desafio.web.dto.AuthUsuarioRecord;
import api.desafio.web.dto.SimplesUsuarioDto;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AuthServiceImpl implements AuthService {
    @Autowired
    private JwtUserDetailsService jwtUserDetailsService;
    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    public JwtToken autenticao(AuthUsuarioRecord authUsuarioRecord, HttpServletRequest request) {
        log.info("processo de authenticação pelo login {}", authUsuarioRecord.email());
        try {
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(authUsuarioRecord.email(), authUsuarioRecord.senha());
            authenticationManager.authenticate(authenticationToken);

            JwtToken token = jwtUserDetailsService.getTokenAuthenticated(authUsuarioRecord.email());
            return token;
        } catch (UnnauthorizedException ex) {
            log.warn("Credencial invalida por email {}", authUsuarioRecord.email());
        }
        throw new ForbiddenExeceptionHandle("token inválido ou expirado...");
    }
}
