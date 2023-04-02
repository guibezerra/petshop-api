package com.petshopapi.api.model.input;

import com.petshopapi.domain.model.TipoPerfil;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
public class UsuarioInput {
    @NotBlank
    @Size(max = 14)
    private String cpf;

    @NotBlank
    @Size(max = 50)
    private String nome;

    @NotNull
    private TipoPerfil tipoPerfil;

    @NotBlank
    @Size(max = 8)
    private String senha;
}
