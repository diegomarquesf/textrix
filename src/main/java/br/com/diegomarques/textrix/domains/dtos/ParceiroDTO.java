package br.com.diegomarques.textrix.domains.dtos;

import br.com.diegomarques.textrix.domains.Parceiro;
import br.com.diegomarques.textrix.domains.enums.TipoNaturezaJuridica;
import br.com.diegomarques.textrix.domains.enums.TipoParceiro;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;
import java.time.LocalDateTime;

public class ParceiroDTO implements Serializable {

    @Schema(description = "Identificador único do parceiro", example = "1")
    private Long chave;

    @Schema(description = "Data de criação do parceiro")
    private LocalDateTime dataCriacao;

    @Schema(description = "Tipo de natureza jurídica do parceiro", example = "PESSOA_JURIDICA")
    private TipoNaturezaJuridica tipoNaturezaJuridica;

    @Schema(description = "Tipo de parceiro (Cliente ou Fornecedor)", example = "CLIENTE")
    private TipoParceiro tipoParceiro;

    @Schema(description = "Nome/Empresa do parceiro", example = "Empresa XX")
    private String nome;

    @Schema(description = "Nome fantasia do parceiro", example = "Empresa ABC")
    private String nomeFantasia;

    @Schema(description = "CNPJ do parceiro, se aplicável", example = "12345678000199")
    private String cnpj;

    @Schema(description = "CPF do parceiro, se aplicável", example = "12345678900")
    private String cpf;

    @Schema(description = "Dados do endereço do parceiro")
    private EnderecoDTO endereco;

    private boolean excluido;

    public ParceiroDTO() {}

    public ParceiroDTO(Parceiro parceiro) {
        this.chave = parceiro.getChave();
        this.dataCriacao = parceiro.getDataCriacao();
        this.tipoNaturezaJuridica = parceiro.getTipoNaturezaJuridica();
        this.tipoParceiro = parceiro.getTipoParceiro();
        this.nome = parceiro.getNome();
        this.nomeFantasia = parceiro.getNomeFantasia();
        this.cnpj = parceiro.getCnpj();
        this.cpf = parceiro.getCpf();
        this.endereco = new EnderecoDTO(parceiro.getEndereco());
        this.excluido = parceiro.isExcluido();
    }

    @JsonProperty("chave")
    public Long getChave() {
        return chave;
    }

    @JsonProperty("dataCriacao")
    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    @JsonProperty("tipoPessoa")
    public TipoNaturezaJuridica getTipoNaturezaJuridica() {
        return tipoNaturezaJuridica;
    }

    @JsonProperty("tipoParceiro")
    public TipoParceiro getTipoParceiro() {
        return tipoParceiro;
    }

    @JsonProperty("nome")
    public String getNome() {
        return nome;
    }

    @JsonProperty("nomeFantasia")
    public String getNomeFantasia() {
        return nomeFantasia;
    }

    @JsonProperty("cnpj")
    public String getCnpj() {
        return cnpj;
    }

    @JsonProperty("cpf")
    public String getCpf() {
        return cpf;
    }

    @JsonProperty("endereco")
    public EnderecoDTO getEndereco() {
        return endereco;
    }

    @JsonProperty("excluido")
    public boolean isExcluido() {
        return excluido;
    }
}