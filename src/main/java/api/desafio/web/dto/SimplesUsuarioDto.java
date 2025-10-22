package api.desafio.web.dto;

import api.desafio.model.Autorizacao;
import lombok.Data;

import java.util.UUID;

@Data
public class SimplesUsuarioDto {
    UUID idUsuario;
    String nome;
    String email;
    Autorizacao role;
}
