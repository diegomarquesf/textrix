package br.com.diegomarques.textrix.domains.dtos;

import br.com.diegomarques.textrix.domains.Endereco;
import jakarta.validation.constraints.NotBlank;

import java.io.Serializable;

public class EnderecoDTO implements Serializable {

    @NotBlank(message = "O campo 'Logradouro' é necessário!")
    private String logradouro;

    @NotBlank(message = "O campo 'Número' é necessário!")
    private String numero;

    @NotBlank(message = "O campo 'Bairro' é necessário!")
    private String bairro;

    private String complemento;

    @NotBlank(message = "O campo 'Cidade' é necessário!")
    private String cidade;

    @NotBlank(message = "O campo 'Estado' é necessário!")
    private String estado;

    @NotBlank(message = "O campo 'CEP' é necessário!")
    private String cep;

    private boolean excluido;

    public EnderecoDTO(Endereco endereco) { }

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