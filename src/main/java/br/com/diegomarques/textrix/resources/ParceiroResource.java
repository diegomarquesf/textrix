package br.com.diegomarques.textrix.resources;

import br.com.diegomarques.textrix.domains.dtos.ParceiroDTO;
import br.com.diegomarques.textrix.domains.dtos.ParceiroNovoDTO;
import br.com.diegomarques.textrix.services.ParceiroService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Tag(name = "Parceiros", description = "Operações Relacionadas aos Parceiros!")
@RestController
@RequestMapping(value = "api/parceiros")
public class ParceiroResource {

    @Autowired
    private ParceiroService parceiroServices;

    @Operation(summary = "Cria um novo parceiro")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Parceiro criado com sucesso", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ParceiroDTO.class))),
            @ApiResponse(responseCode = "400", description = "Requisição inválida", content = @Content)
    })
    @PostMapping
    public ResponseEntity<ParceiroDTO> create(@Valid @RequestBody ParceiroNovoDTO parceiroNovoDTO) {
        ParceiroDTO parceiro = parceiroServices.create(parceiroNovoDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{chave}")
                .buildAndExpand(parceiro.getChave())
                .toUri();

        return ResponseEntity.created(uri).body(parceiro);
    }

    @Operation(summary = "Busca todos os parceiros")
    @ApiResponse(responseCode = "200", description = "Lista de todos os parceiros retornada com sucesso", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = ParceiroDTO.class))))
    @GetMapping("/todos")
    public ResponseEntity<List<ParceiroDTO>> findAll() {
        return ResponseEntity.ok(parceiroServices.findAll());
    }

    @Operation(summary = "Busca um parceiro pela chave")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Parceiro encontrado com sucesso", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ParceiroDTO.class))),
            @ApiResponse(responseCode = "404", description = "Parceiro não encontrado")
    })
    @GetMapping("/ativos/{chave}")
    public ResponseEntity<ParceiroDTO> findById(@PathVariable Long chave) {
        return ResponseEntity.ok(parceiroServices.findById(chave));
    }

    @Operation(summary = "Busca todos os parceiros ativos")
    @ApiResponse(responseCode = "200", description = "Lista de parceiros ativos retornada com sucesso", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = ParceiroDTO.class))))
    @GetMapping("/ativos")
    public ResponseEntity<List<ParceiroDTO>> findAtivos() {
        return ResponseEntity.ok(parceiroServices.findAtivos());
    }

    @Operation(summary = "Atualiza um parceiro")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Parceiro atualizado com sucesso", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ParceiroDTO.class))),
            @ApiResponse(responseCode = "400", description = "Requisição inválida", content = @Content),
            @ApiResponse(responseCode = "404", description = "Parceiro não encontrado")
    })
    @PutMapping("/{chave}")
    public ResponseEntity<ParceiroDTO> update(@PathVariable Long chave, @RequestBody ParceiroDTO parceiroDTO) {
        return ResponseEntity.ok(parceiroServices.update(chave, parceiroDTO));
    }

    @Operation(summary = "Remove um parceiro")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Parceiro removido com sucesso"),
            @ApiResponse(responseCode = "404", description = "Parceiro não encontrado")
    })
    @DeleteMapping("/{chave}")
    public ResponseEntity<Void> delete(@PathVariable Long chave) {
        parceiroServices.delete(chave);
        return ResponseEntity.noContent().build();
    }
}
