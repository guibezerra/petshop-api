package com.petshopapi.api.model.input;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EnderecoInput {
    private String logradouro;
    private String cidade;
    private String bairro;
    private String complemento;
    private String tag;
}
