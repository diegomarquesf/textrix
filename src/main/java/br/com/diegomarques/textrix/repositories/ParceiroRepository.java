package br.com.diegomarques.textrix.repositories;

import br.com.diegomarques.textrix.domains.Parceiro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParceiroRepository extends JpaRepository<Parceiro, Long> {

}
