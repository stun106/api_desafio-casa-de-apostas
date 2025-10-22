package api.desafio.service;

import api.desafio.jwt.JwtToken;
import api.desafio.web.dto.AuthUsuarioRecord;
import api.desafio.web.dto.SimplesUsuarioDto;
import jakarta.servlet.http.HttpServletRequest;

public interface AuthService {
    JwtToken autenticao(AuthUsuarioRecord authUsuarioRecord, HttpServletRequest request);
}
