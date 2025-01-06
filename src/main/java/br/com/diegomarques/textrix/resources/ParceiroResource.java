package br.com.diegomarques.textrix.resources;

import br.com.diegomarques.textrix.domains.dtos.ParceiroDTO;
import br.com.diegomarques.textrix.domains.dtos.ParceiroNovoDTO;
import br.com.diegomarques.textrix.services.ParceiroService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping(value = "api/parceiros")
public class ParceiroResource {

    @Autowired
    private ParceiroService parceiroServices;

    @PostMapping
    public ResponseEntity<ParceiroDTO> create(@Valid @RequestBody ParceiroNovoDTO parceiroNovoDTO) {
        ParceiroDTO parceiro = parceiroServices.create(parceiroNovoDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{chave}")
                .buildAndExpand(parceiro.getChave())
                .toUri();

        return ResponseEntity.created(uri).body(parceiro);
    }
}
