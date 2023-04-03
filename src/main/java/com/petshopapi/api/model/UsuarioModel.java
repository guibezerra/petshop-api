package com.petshopapi.api.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.petshopapi.domain.model.TipoPerfil;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UsuarioModel {
    private Long idUsuario;
    private String cpf;
    private String nome;
    private String tipoPerfil;
    private ClienteModel cliente;
}
