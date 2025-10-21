package api.desafio.service;

import api.desafio.model.Autorizacao;
import api.desafio.model.Usuario;

import java.util.UUID;


public interface UsuarioService {
    void criandoUsuario (Usuario usuario);

    Usuario buscarUsuarioPorEmail(String email);

    Autorizacao buscarRoleByEmail(String email);

    //Usuario alterarSenha (UUID idUsuario, String senhaAtual, String novaSenha, String ConfirmaSenha);
}