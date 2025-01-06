package br.com.diegomarques.textrix.domains.dtos;

import br.com.diegomarques.textrix.domains.enums.TipoNaturezaJuridica;
import br.com.diegomarques.textrix.domains.enums.TipoParceiro;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.br.CNPJ;
import org.hibernate.validator.constraints.br.CPF;

import java.io.Serializable;

public class ParceiroNovoDTO implements Serializable {

    @NotNull(message = "O campo 'Tipo Pessoa' é necessário!")
    private TipoNaturezaJuridica tipoNaturezaJuridica;

    @NotNull(message = "O campo 'Tipo Parceiro' é necessário!")
    private TipoParceiro tipoParceiro;

    @NotBlank(message = "O campo 'Nome' é necessário!")
    private String nome;

    @NotBlank(message = "O campo 'Nome Fantasia' é necessário!")
    private String nomeFantasia;

    @CNPJ
    @Size(min = 14, max = 14)
    private String cnpj;

    @CPF
    @Size(min = 11, max = 11)
    private String cpf;

    @Valid
    private EnderecoDTO endereco;

    public ParceiroNovoDTO() { }

    public TipoNaturezaJuridica getTipoNaturezaJuridica() {
        return tipoNaturezaJuridica;
    }

    public void setTipoNaturezaJuridica(TipoNaturezaJuridica tipoNaturezaJuridica) {
        this.tipoNaturezaJuridica = tipoNaturezaJuridica;
    }

    public TipoParceiro getTipoParceiro() {
        return tipoParceiro;
    }

    public void setTipoParceiro(TipoParceiro tipoParceiro) {
        this.tipoParceiro = tipoParceiro;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNomeFantasia() {
        return nomeFantasia;
    }

    public void setNomeFantasia(String nomeFantasia) {
        this.nomeFantasia = nomeFantasia;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public EnderecoDTO getEndereco() {
        return endereco;
    }

    public void setEnderecoDTO(EnderecoDTO endereco) {
        this.endereco = endereco;
    }
}