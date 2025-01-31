package br.com.diegomarques.textrix.domains.dtos;

import br.com.diegomarques.textrix.domains.enums.TipoRole;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
import java.util.Set;

public record UsuarioDTO(
        @Schema(description = "Identificador único do usuário", example = "1")
        Long chave,

        @Schema(description = "Data de criação do usuário")
        LocalDateTime dataCriacao,

        @Schema(description = "Indica se o usuário está ativo", example = "true")
        boolean ativo,

        @Schema(description = "Nome do usuário", example = "João")
        String nome,

        @Schema(description = "CPF do usuário", example = "12345678900")
        String cpf,

        @Schema(description = "E-mail do usuário", example = "joao@mail.com")
        String email,

        @Schema(description = "Permissões do usuário")
        Set<TipoRole> roles,

        @Schema(description = "Dados do endereço do usuário")
        EnderecoDTO endereco,

        @Schema(description = "Indica se o usuário foi excluído", example = "false")
        boolean excluido
)  { }
