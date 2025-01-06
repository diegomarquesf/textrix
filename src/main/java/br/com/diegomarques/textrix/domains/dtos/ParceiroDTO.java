package br.com.diegomarques.textrix.domains.dtos;

import br.com.diegomarques.textrix.domains.Parceiro;
import br.com.diegomarques.textrix.domains.enums.TipoNaturezaJuridica;
import br.com.diegomarques.textrix.domains.enums.TipoParceiro;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.time.LocalDateTime;

public class ParceiroDTO implements Serializable {

    private Long chave;

    private LocalDateTime dataCriacao;
    private TipoNaturezaJuridica tipoNaturezaJuridica;
    private TipoParceiro tipoParceiro;
    private String nome;
    private String nomeFantasia;
    private String cnpj;
    private String cpf;
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