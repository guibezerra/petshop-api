package com.petshopapi.api.controller.openapi;

import com.petshopapi.api.model.PetModel;
import com.petshopapi.api.model.input.PetInput;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

@Tag(name = "Pet",description = "Endpoint responsável por gerenciar os pet's dos clientes existentes no sistema.")
public interface PetControllerOpenAPI {
    @Operation(summary = "Buscar por todos os pets cadastrados para um cliente.",
            description = "Pode ser acessado por qualquer um dos perfis (*CLIENTE* ou *ADMIN*) e tem como objetivo retornar todos" +
                    " os pets cadastrados por um determinado cliente. Dessa forma, o id do cliente deve ser passado como parâmetro de busca.")
    public Page<PetModel> buscarTodos(Long idCliente, @PageableDefault(size = 10) Pageable pageable);


    @Operation(summary = "Buscar um Pet.",
            description = "Pode ser acessado por qualquer um dos perfis (*CLIENTE* ou *ADMIN*) e tem como objetivo retornar o" +
                    " pet associado ao id passado como parâmetro.")
    public PetModel buscarPorId(@PathVariable Long idPet);

    @Operation(summary = "Cadastrar um novo pet.",
    description = "Pode ser acessado por qualquer um dos perfis (*CLIENTE* ou *ADMIN*) e tem como objetivo cadastrar um pet" +
            " para um determinado cliente. Dessa forma, o método recebe um objeto no qual um de seus atributos corresponde ao" +
            " id do cliente para o qual o pet será associado.")
    public PetModel cadastrarPet(@RequestBody @Valid PetInput petInput);

    @Operation(summary = "Alterar registro de um pet",
               description = "Pode ser acessado por qualquer um dos perfis (*CLIENTE* ou *ADMIN*) e tem como objetivo alterar" +
                       " as informações cadastradas para o pet associado ao id passado como parâmetro.")
    public PetModel atualizarPet(@PathVariable Long idPet,@Valid @RequestBody PetInput petInput);

    @Operation(summary = "Deletar registro de um pet.",
               description = "Pode ser acessado por qualquer um dos perfis (*CLIENTE* ou *ADMIN*) e tem como objetivo excluir" +
                       " o registro do pet associado ao id passado como parâmetro.")
    public void deletarPet(@PathVariable Long idPet);



}