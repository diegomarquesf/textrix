package br.com.diegomarques.textrix.domains;

import br.com.diegomarques.textrix.domains.enums.TipoRole;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import org.hibernate.annotations.CreationTimestamp;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "tb_usuario")
public class Usuario implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long chave;

    @CreationTimestamp
    @Column(name = "dataCriacao", updatable = false)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime dataCriacao;

    @Column(name = "ativo")
    private boolean ativo;

    @Column(name = "nome")
    private String nome;

    @Column(name = "cpf", unique = true)
    private String cpf;

    @Column(name = "email", nullable = false, unique = true)
    @Email
    private String email;

    @Column(name = "senha")
    private String senha;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "tb_usuario_roles", joinColumns = @JoinColumn(name = "usuario_id"))
    @Column(name = "role_nome")
    @Enumerated(EnumType.STRING)
    private Set<TipoRole> roles = new HashSet<>();

    @JsonIgnore
    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "endereco_chave")
    private Endereco endereco;

    @Column(name = "excluido")
    private boolean excluido = false;

    @PrePersist
    private void prePersist() {
        this.ativo = true;
    }

    public Usuario() {}

    public Usuario(String nome, String cpf, String email, String senha) {
        this.nome = nome;
        this.cpf = cpf;
        this.email = email;
        this.senha = senha;
        addPerfil(TipoRole.ROLE_USER2);
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

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Set<TipoRole> getRoles() {
        return roles;
    }

    public void setRoles(Set<TipoRole> roles) {
        this.roles = roles;
    }

    public void addPerfil(TipoRole role) {
        this.roles.add(role);
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
        Usuario usuario = (Usuario) o;
        return Objects.equals(chave, usuario.chave);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(chave);
    }
}
