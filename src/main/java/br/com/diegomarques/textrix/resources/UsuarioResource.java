package br.com.diegomarques.textrix.resources;

import br.com.diegomarques.textrix.domains.dtos.UsuarioDTO;
import br.com.diegomarques.textrix.domains.dtos.UsuarioNovoDTO;
import br.com.diegomarques.textrix.services.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Tag(name = "Usuários", description = "Operações Relacionadas aos Usuários!")
@RestController
@RequestMapping(value = "api/usuarios")
public class UsuarioResource {

    @Autowired
    private UsuarioService usuarioServices;


    @Operation(summary = "Criar um novo usuário")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Usuário criado com sucesso", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UsuarioDTO.class))),
            @ApiResponse(responseCode = "400", description = "Requisição inválida", content = @Content)
    })
    @PreAuthorize("hasRole('ROLE_ADM')")
    @PostMapping
    public ResponseEntity<UsuarioDTO> create(@Valid @RequestBody UsuarioNovoDTO usuarioNovoDTO) {
        UsuarioDTO usuario = usuarioServices.create(usuarioNovoDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{chave}")
                .buildAndExpand(usuario.chave())
                .toUri();

        return ResponseEntity.created(uri).body(usuario);
    }

    @Operation(summary = "Busca todos os usuários")
    @ApiResponse(responseCode = "200", description = "Lista de todos os usuários retornado com sucesso", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = UsuarioDTO.class))))
    @PreAuthorize("hasAnyRole('ADM', 'USER1', 'USER2')")
    @GetMapping("/todos")
    public ResponseEntity<List<UsuarioDTO>> findAll() {
        return ResponseEntity.ok(usuarioServices.findAll());
    }

    @Operation(summary = "Busca todos os usuários ativos")
    @ApiResponse(responseCode = "200", description = "Lista de usuários ativos retornada com sucesso", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = UsuarioDTO.class))))
    @PreAuthorize("hasAnyRole('ADM', 'USER1', 'USER2')")
    @GetMapping("/ativos")
    public ResponseEntity<List<UsuarioDTO>> findAtivos() {
        return ResponseEntity.ok(usuarioServices.findAtivos());
    }

    @Operation(summary = "Busca um usuário pela chave")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário encontrado com sucesso", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UsuarioDTO.class))),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    @PreAuthorize("hasAnyRole('ADM', 'USER1', 'USER2')")
    @GetMapping("/ativos/{chave}")
    public ResponseEntity<UsuarioDTO> findById(@PathVariable Long chave) {
        return ResponseEntity.ok(usuarioServices.findByChave(chave));
    }

    @Operation(summary = "Atualiza um Usuário")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário atualizado com sucesso", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UsuarioDTO.class))),
            @ApiResponse(responseCode = "400", description = "Requisição inválida", content = @Content),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    @PutMapping("/{chave}")
    @PreAuthorize("hasRole('ADM')")
    public ResponseEntity<UsuarioDTO> update(@PathVariable Long chave, @RequestBody UsuarioDTO usuarioDTO) {
        return ResponseEntity.ok(usuarioServices.update(chave, usuarioDTO));
    }

    @Operation(summary = "Desativar um usuário")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Usuário desativado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    @PreAuthorize("hasRole('ADM')")
    @DeleteMapping("/{chave}")
    public ResponseEntity<Void> delete(@PathVariable Long chave) {
        usuarioServices.disable(chave);
        return ResponseEntity.noContent().build();
    }
}