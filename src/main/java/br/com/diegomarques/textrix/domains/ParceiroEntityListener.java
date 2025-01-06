package br.com.diegomarques.textrix.domains;

import jakarta.persistence.PrePersist;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class ParceiroEntityListener {

    @PrePersist
    public void prePersist(Parceiro parceiro) {
        if (parceiro.getDataCriacao() == null)
            parceiro.setDataCriacao(LocalDateTime.now());
    }
}
