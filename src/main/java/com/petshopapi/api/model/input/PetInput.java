package com.petshopapi.api.model.input;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class PetInput {
    private ClienteIdInput cliente;
    private RacaIdInput raca;
    private LocalDate dataDeNascimento;
    private String nome;
}
