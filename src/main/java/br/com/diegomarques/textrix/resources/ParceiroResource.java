package br.com.diegomarques.textrix.resources;

import br.com.diegomarques.textrix.domains.dtos.ParceiroDTO;
import br.com.diegomarques.textrix.domains.dtos.ParceiroNovoDTO;
import br.com.diegomarques.textrix.services.ParceiroService;
import jakarta.validation.Valid;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

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

    @GetMapping("/todos")
    public ResponseEntity<List<ParceiroDTO>> findAll() {
        return ResponseEntity.ok(parceiroServices.findAll());
    }

    @GetMapping("/ativos/{chave}")
    public ResponseEntity<ParceiroDTO> findById(@PathVariable Long chave) {
        return ResponseEntity.ok(parceiroServices.findById(chave));
    }

    @GetMapping("/ativos")
    public ResponseEntity<List<ParceiroDTO>> findAtivos() {
        return ResponseEntity.ok(parceiroServices.findAtivos());
    }

    @PutMapping("/{chave}")
    public ResponseEntity<ParceiroDTO> update(@PathVariable Long chave, @RequestBody ParceiroDTO parceiroDTO) {
        return ResponseEntity.ok(parceiroServices.update(chave, parceiroDTO));
    }
}
