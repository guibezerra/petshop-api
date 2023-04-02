package com.petshopapi.api.model.input;

import com.petshopapi.domain.model.TagContato;
import com.petshopapi.domain.model.TipoContato;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
public class ContatoInput {
    @NotNull
    private TagContato tagContato;

    @NotNull
    private TipoContato tipoContato;

    @NotBlank
    @Size(max = 40)
    private String valor;
}
