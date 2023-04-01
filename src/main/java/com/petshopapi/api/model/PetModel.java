package com.petshopapi.api.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class PetModel {
    private Long idPet;
    private ClienteModel cliente;
    private RacaModel raca;
    private LocalDate dataDeNascimento;
    private String nome;
}
