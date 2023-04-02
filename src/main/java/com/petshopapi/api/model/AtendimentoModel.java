package com.petshopapi.api.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class AtendimentoModel {
    private Long idAtendimento;
    private PetModel pet;
    private String descricao;
    private float valor;
    private LocalDate data;
}
