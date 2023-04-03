package com.petshopapi.domain.model;

public enum TipoPerfil {
    CLIENTE("ROLE_USER"),
    ADMIN("ROLE_ADMIN");

    private String valor;
    TipoPerfil(String valor) {
        this.valor = valor;
    }

    public String getValor() {
        return valor;
    }
}
