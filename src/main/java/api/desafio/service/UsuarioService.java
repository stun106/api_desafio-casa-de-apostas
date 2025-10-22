package api.desafio.service;

import api.desafio.model.Autorizacao;
import api.desafio.model.Usuario;
import api.desafio.web.dto.SimplesUsuarioDto;

import java.util.UUID;


public interface UsuarioService {
    SimplesUsuarioDto criandoUsuario (Usuario usuario);

    Usuario buscarUsuarioPorEmail(String email);

    Autorizacao buscarRoleByEmail(String email);

    SimplesUsuarioDto usuarioLogado();

    //Usuario alterarSenha (UUID idUsuario, String senhaAtual, String novaSenha, String ConfirmaSenha);
}