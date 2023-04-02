package com.petshopapi.api.model.input;

import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Getter
@Setter
public class AtendimentoInput {
    @NotNull
    @Valid
    private PetIdInput pet;

    @NotBlank
    @Size(max = 60)
    private String descricao;

    @NotNull
    private float valor;

    @NotNull
    private LocalDate data;
}
