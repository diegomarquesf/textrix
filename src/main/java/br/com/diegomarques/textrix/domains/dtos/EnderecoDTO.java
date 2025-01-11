package br.com.diegomarques.textrix.domains.dtos;

import br.com.diegomarques.textrix.domains.Endereco;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

import java.io.Serializable;

public class EnderecoDTO implements Serializable {

    @Schema(description = "Logradouro", example = "Rua das Flores")
    @NotBlank(message = "O campo 'Logradouro' é necessário!")
    private String logradouro;

    @Schema(description = "Número do endereço", example = "10")
    @NotBlank(message = "O campo 'Número' é necessário!")
    private String numero;

    @Schema(description = "Bairro", example = "Centro")
    @NotBlank(message = "O campo 'Bairro' é necessário!")
    private String bairro;

    @Schema(description = "Complemento do endereço", example = "Casa A")
    private String complemento;

    @Schema(description = "Cidade", example = "São Paulo")
    @NotBlank(message = "O campo 'Cidade' é necessário!")
    private String cidade;

    @Schema(description = "Estado", example = "SP")
    @NotBlank(message = "O campo 'Estado' é necessário!")
    private String estado;

    @Schema(description = "CEP", example = "01010-001")
    @NotBlank(message = "O campo 'CEP' é necessário!")
    private String cep;

    private boolean excluido;

    public EnderecoDTO() { }

    public EnderecoDTO(Endereco endereco) {
        this.logradouro = endereco.getLogradouro();
        this.numero = endereco.getNumero();
        this.bairro = endereco.getBairro();
        this.complemento = endereco.getComplemento();
        this.cidade = endereco.getCidade();
        this.estado = endereco.getEstado();
        this.cep = endereco.getCep();
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public boolean isExcluido() {
        return excluido;
    }

    public void setExcluido(boolean excluido) {
        this.excluido = excluido;
    }
}