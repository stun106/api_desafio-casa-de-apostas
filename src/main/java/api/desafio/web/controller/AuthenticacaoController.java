package api.desafio.web.controller;

import api.desafio.jwt.JwtToken;
import api.desafio.jwt.JwtUserDetailsService;
import api.desafio.service.AuthService;
import api.desafio.service.exception.ForbiddenExeceptionHandle;
import api.desafio.service.exception.UnnauthorizedException;
import api.desafio.web.dto.AuthUsuarioRecord;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/auth")
public class AuthenticacaoController {

    @Autowired
    private AuthService authService;

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public JwtToken auth (@RequestBody AuthUsuarioRecord authUsuarioRecord, HttpServletRequest request) {
        return authService.autenticao(authUsuarioRecord,request);
    }
}
