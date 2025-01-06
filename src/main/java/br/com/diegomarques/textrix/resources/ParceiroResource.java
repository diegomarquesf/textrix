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
        List<ParceiroDTO> parceiros = parceiroServices.findAll();
        return ResponseEntity.ok(parceiros);
    }

    @GetMapping("/ativos/{chave}")
    public ResponseEntity<ParceiroDTO> findById(@PathVariable Long chave) {
        try {
            return ResponseEntity.ok(parceiroServices.findById(chave));
        } catch (ObjectNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/ativos")
    public ResponseEntity<List<ParceiroDTO>> findAtivos() {
        List<ParceiroDTO> parceiros = parceiroServices.findAtivos();
        return parceiros.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(parceiros);
    }
}
