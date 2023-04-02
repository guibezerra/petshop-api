package com.petshopapi.api.model.input;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class AtendimentoInput {
    private PetIdInput pet;
    private String descricao;
    private float valor;
    private LocalDate data;
}
