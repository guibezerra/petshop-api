package com.petshopapi.api.model.input;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
public class EnderecoInput {
    @NotBlank
    @Size(max = 60)
    private String logradouro;

    @NotBlank
    @Size(max = 30)
    private String cidade;

    @NotBlank
    @Size(max = 30)
    private String bairro;

    @Size(max = 40)
    private String complemento;

    @Size(max = 20)
    private String tag;
}
