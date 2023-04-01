package com.petshopapi.api.model.input;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ClienteInput {
    private String nome;
    private String cpf;
    private EnderecoInput endereco;
    private List<ContatoInput> contato;
}
