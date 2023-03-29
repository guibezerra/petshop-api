package com.petshopapi.api.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ClienteModel {
    private Long idCliente;
    private String nome;
    private String cpf;
    private LocalDate dataDeCadastro;
    private List<ContatoModel> contato;
    private EnderecoModel endereco;
}
