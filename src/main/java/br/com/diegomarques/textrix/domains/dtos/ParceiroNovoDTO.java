package br.com.diegomarques.textrix.domains.dtos;

import br.com.diegomarques.textrix.domains.enums.TipoNaturezaJuridica;
import br.com.diegomarques.textrix.domains.enums.TipoParceiro;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.br.CNPJ;
import org.hibernate.validator.constraints.br.CPF;

import java.io.Serializable;

public class ParceiroNovoDTO implements Serializable {

    @Schema(description = "Tipo de natureza jurídica do parceiro", example = "PESSOA_JURIDICA")
    @NotNull(message = "O campo 'Tipo Pessoa' é necessário!")
    private TipoNaturezaJuridica tipoNaturezaJuridica;

    @Schema(description = "Tipo de parceiro (Cliente ou Fornecedor)", example = "CLIENTE")
    @NotNull(message = "O campo 'Tipo Parceiro' é necessário!")
    private TipoParceiro tipoParceiro;

    @Schema(description = "Nome/Empresa do parceiro", example = "Empresa XX")
    @NotBlank(message = "O campo 'Nome' é necessário!")
    private String nome;

    @Schema(description = "Nome fantasia do parceiro", example = "Empresa ABC")
    @NotBlank(message = "O campo 'Nome Fantasia' é necessário!")
    private String nomeFantasia;

    @Schema(description = "CNPJ do parceiro, se aplicável", example = "12345678000199")
    @CNPJ
    private String cnpj;

    @Schema(description = "CPF do parceiro, se aplicável", example = "12345678900")
    @CPF
    private String cpf;

    @Schema(description = "Dados do endereço do parceiro")
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

    public void setEndereco(EnderecoDTO endereco) {
        this.endereco = endereco;
    }
}