package com.petshopapi.api.model.input;

import com.petshopapi.domain.model.TipoPerfil;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioInput {
    private String cpf;
    private String nome;
    private TipoPerfil tipoPerfil;
    private String senha;
}
