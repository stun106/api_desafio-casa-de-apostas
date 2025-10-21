package api.desafio.service.impl;

import api.desafio.model.Autorizacao;
import api.desafio.model.Usuario;
import api.desafio.repository.UsuarioReprository;
import api.desafio.service.UsuarioService;
import api.desafio.service.exception.EntityNotfoundException;
import api.desafio.service.exception.UniqueViolationExeception;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private UsuarioReprository usuarioReprository;

    private static String passwordEncode(String senha) {
        return new BCryptPasswordEncoder().encode(senha);
    }

    private static boolean passwordDecode(String senhaAtual, String senha) {
        return new BCryptPasswordEncoder().matches(senhaAtual, senha);
    }

    @Override
    public void criandoUsuario(Usuario usuario) {

        Usuario usuarioExiste = usuarioReprository.findByNome(usuario.getNome());
        if (usuarioExiste != null) {
            throw new UniqueViolationExeception("Usuario já Existe...");
        }

        usuario.setSenha(passwordEncode(usuario.getSenha()));
        usuario.setRole(Autorizacao.USER);

        usuarioReprository.save(usuario);
    }

    @Override
    @Transactional(readOnly = true)
    public Usuario buscarUsuarioPorEmail(String email) {
        return usuarioReprository.findUsuarioByEmail(email)
                .orElseThrow(() -> new EntityNotfoundException("Usuario não encontrado"));
    }
    @Override
    @Transactional(readOnly = true)
    public Autorizacao buscarRoleByEmail(String email) {
        return usuarioReprository.buscarRolePorEmail(email);
    }

//    @Override
//    public Usuario alterarSenha(UUID idUsuario, String senhaAtual, String novaSenha, String confirmaSenha) {
//        if (!novaSenha.equals(confirmaSenha)){
//            log.error( "Confirmação de senha invalida..." );
//            throw new PasswordInvalidException("Confirmação de senha Invalida...");
//        }
//
//        Usuario esteUsuario = usuarioReprository.findById(idUsuario)
//                .orElseThrow(() -> new EntityNotfoundException( "Usuario não encontrado..." ) );
//        if (!(passwordDecode( senhaAtual, esteUsuario.getSenha() ))) throw new PasswordInvalidException( "As credenciais não conferem, verifique seus dados..." );
//        System.out.println(esteUsuario.getNome());
//        esteUsuario.setSenha( passwordEncode( novaSenha ) );
//        usuarioReprository.save( esteUsuario );
//
//        return esteUsuario;
//    }
}

