package com.petshopapi.api.model.input;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
public class RacaInput {
    @NotBlank
    @Size(max = 30)
    private String descricao;
}
