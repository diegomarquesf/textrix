package br.com.diegomarques.textrix.domains;

import br.com.diegomarques.textrix.domains.enums.TipoNaturezaJuridica;
import br.com.diegomarques.textrix.domains.enums.TipoParceiro;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "tb_parceiro")
@EntityListeners(ParceiroEntityListener.class)
public class Parceiro implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long chave;

    @Column(name = "dataCriacao")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime dataCriacao;

    @Column(name = "tipoNaturezaJuridica")
    @Enumerated(EnumType.STRING)
    private TipoNaturezaJuridica tipoNaturezaJuridica;

    @Column(name = "tipoParceiro")
    @Enumerated(EnumType.STRING)
    private TipoParceiro tipoParceiro;

    @Column(name = "nome")
    private String nome;
    @Column(name = "nomeFantasia")
    private String nomeFantasia;

    @Column(name = "cnpj")
    private String cnpj;

    @Column(name = "cpf")
    private String cpf;

    @JsonIgnore
    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "endereco_chave")
    private Endereco endereco;

    @Column(name = "excluido")
    private boolean excluido = false;

    public Parceiro() {}

    public Parceiro(TipoNaturezaJuridica tipoNaturezaJuridica, TipoParceiro tipoParceiro, String nome, String nomeFantasia, String cpf, String cnpj) {
        this.tipoNaturezaJuridica = tipoNaturezaJuridica;
        this.tipoParceiro = tipoParceiro;
        this.nome = nome;
        this.nomeFantasia = nomeFantasia;
        this.cpf = cpf;
        this.cnpj = cnpj;
    }

    public Long getChave() {
        return chave;
    }

    public void setChave(Long chave) {
        this.chave = chave;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

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

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public boolean isExcluido() {
        return excluido;
    }

    public void setExcluido(boolean excluido) {
        this.excluido = excluido;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Parceiro parceiro = (Parceiro) o;
        return Objects.equals(chave, parceiro.chave);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(chave);
    }
}