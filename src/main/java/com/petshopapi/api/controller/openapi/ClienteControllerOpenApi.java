package com.petshopapi.api.controller.openapi;

import com.petshopapi.api.model.ClienteModel;
import com.petshopapi.api.model.input.ClienteInput;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

@Tag(name = "Cliente", description = "Endpoint responsável por realizar a gerencimento das informações cadastrais da entidade cliente.")
public interface ClienteControllerOpenApi {
    @Operation(summary = "Alterar ou salvar informações cadastrais de um usuário do tipo CLIENTE.",
            description = "Pode ser acessado por qualquer um dos perfis (*CLIENTE* ou *ADMIN*) e tem como objetivo não só alterar os campos *nome* " +
                    "e *cpf* cadastrados previamente, como também inserir/alterar as informações de *endereço* e *contato* associados ao cliente." +
                    " Deve-se salientar que, o método recebe um objeto em que um de seus atributos corresponde a uma lista de contatos em que esta " +
                    "não pode ultrapassar o tamaho máximo de dois elementos.")
    public ClienteModel alterarCliente(@PathVariable Long idCliente, @Valid @RequestBody ClienteInput clienteInput);

    @Operation(summary = "Buscar um usuário do tipo CLIENTE",
               description = "Pode ser acessado por qualquer um dos perfis (*CLIENTE* ou *ADMIN*) e tem como objetivo buscar um *CLIENTE* " +
                       "por meio de seu id.")
    public ClienteModel buscarClientePorId(@PathVariable Long idCliente);
}
