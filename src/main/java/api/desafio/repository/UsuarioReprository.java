package api.desafio.repository;

import api.desafio.model.Autorizacao;
import api.desafio.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;


@Repository

public interface UsuarioReprository extends JpaRepository<Usuario, UUID> {
    Usuario findByNome (String nome);

   Optional<Usuario> findUsuarioByEmail (String email);

    @Query("SELECT u.role FROM Usuario u WHERE u.email = :email")
    Autorizacao buscarRolePorEmail(String email);
}
