package com.petshopapi.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
public class UsuarioId implements Serializable {
    private String cpf;
    private String nome;
    private Long idUsuario;

    public UsuarioId() {
    }
}
