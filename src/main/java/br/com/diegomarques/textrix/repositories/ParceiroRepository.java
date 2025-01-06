package br.com.diegomarques.textrix.repositories;

import br.com.diegomarques.textrix.domains.Parceiro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ParceiroRepository extends JpaRepository<Parceiro, Long> {
    List<Parceiro> findByExcluidoFalse();
    Optional<Parceiro> findByChaveAndExcluidoFalse(Long chave);
}
