package br.com.diegomarques.textrix.domains.dtos;

import br.com.diegomarques.textrix.domains.enums.TipoRole;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.br.CPF;

import java.util.Set;

public record UsuarioNovoDTO(
        @Schema(description = "Nome do usuário", example = "João")
        @NotBlank(message = "O campo 'Nome' é necessário!")
        String nome,

        @Schema(description = "CPF do usuário", example = "12345678900")
        @CPF
        String cpf,

        @Schema(description = "E-mail do usuário", example = "joao@mail.com")
        @Email
        @NotBlank(message = "O campo 'E-mail' é necessário!")
        String email,

        @Schema(description = "Senha do usuário", example = "Senha@123")
        @NotBlank(message = "O campo 'Senha' é necessário!")
        @Size(min = 8, message = "A senha deve ter no mínimo 8 caracteres")
        String senha,

        @Schema(description = "Permissões do usuário")
        Set<TipoRole> roles,

        @Schema(description = "Dados do endereço do usuário")
        EnderecoDTO endereco
) { }
