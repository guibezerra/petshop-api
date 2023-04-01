package com.petshopapi.api.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EnderecoModel {
    private Long idEndereco;
    private String logradouro;
    private String cidade;
    private String bairro;
    private String complemento;
    private String tag;
}
