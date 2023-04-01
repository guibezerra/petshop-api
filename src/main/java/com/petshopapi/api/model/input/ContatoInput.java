package com.petshopapi.api.model.input;

import com.petshopapi.domain.model.TagContato;
import com.petshopapi.domain.model.TipoContato;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ContatoInput {
    private TagContato tagContato;
    private TipoContato tipoContato;
    private String valor;
}
