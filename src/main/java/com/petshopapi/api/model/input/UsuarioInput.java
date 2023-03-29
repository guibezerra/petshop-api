package com.petshopapi.api.model.input;

import com.petshopapi.domain.model.TipoPerfil;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioInput {
    private UsuarioIdInput usuarioIdInput;
    private TipoPerfil tipoPerfil;
    private String senha;
}
