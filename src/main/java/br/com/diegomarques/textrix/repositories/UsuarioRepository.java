package br.com.diegomarques.textrix.repositories;

import br.com.diegomarques.textrix.domains.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    List<Usuario> findByAtivoTrue();
    Optional<Usuario> findByEmail(String email);
}
