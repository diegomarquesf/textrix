package br.com.diegomarques.textrix.domains.enums;

public enum TipoNaturezaJuridica {
    PESSOA_FISICA("PF"),
    PESSOA_JURIDICA("PJ");

    private String descricao;

    private TipoNaturezaJuridica(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
