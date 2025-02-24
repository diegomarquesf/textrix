package br.com.diegomarques.textrix.domains.enums;

public enum TipoRole {

    ROLE_ADM("Administrador"),
    ROLE_USER1("Usuário 1"),
    ROLE_USER2("Usuário 2");

    private String descricao;

    private TipoRole(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
