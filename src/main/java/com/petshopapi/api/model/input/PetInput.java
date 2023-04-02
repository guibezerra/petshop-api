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
public class PetInput {
    @Valid
    @NotNull
    private ClienteIdInput cliente;

    @Valid
    @NotNull
    private RacaIdInput raca;

    @NotNull
    private LocalDate dataDeNascimento;

    @NotBlank
    @Size(max = 50)
    private String nome;
}
