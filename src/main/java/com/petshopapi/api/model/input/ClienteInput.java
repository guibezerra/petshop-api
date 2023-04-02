package com.petshopapi.api.model.input;

import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Setter
public class ClienteInput {
    @NotBlank
    @Size(max = 50)
    private String nome;

    @NotBlank
    @Size(max = 14)
    private String cpf;

    @NotNull
    @Valid
    private EnderecoInput endereco;

    @NotNull
    @Valid
    private List<ContatoInput> contato;
}
