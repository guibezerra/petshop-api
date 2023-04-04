package com.petshopapi.api.controller.openapi;

import com.petshopapi.api.model.AtendimentoModel;
import com.petshopapi.api.model.input.AtendimentoInput;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

@Tag(name = "Atendimento", description = "Endpoint responsável por realizar o gerenciamento dos atendimentos para os pets de um cliente.")
public interface AtendimentoControllerOpenApi {
    @Operation(summary = "Buscar por todos os atendimentos.",
    description = "Pode ser acessado por qualquer um dos perfis (*CLIENTE* ou *ADMIN*) e tem como objetivo retornar todos os " +
            "atendimentos associados ao id de um cliente, que deve ser passado como parâmetro da busca.")
    public Page<AtendimentoModel> buscarTodosAtendimentosParaCliente(Long idCliente, @PageableDefault(size = 10) Pageable pageable);

    @Operation(summary = "Buscar um atendimento.",
    description = "Pode ser acessado por qualquer um dos perfis (*CLIENTE* ou *ADMIN*) e tem como objetivo retornar o atendimento " +
            "associado ao id passado como parâmetro.")
    public AtendimentoModel buscarAtendimentoPorId(@PathVariable Long idAtendimento);

    @Operation(summary = "Cadastrar atendimento.",
    description = "Pode ser acessado por qualquer um dos perfis (*CLIENTE* ou *ADMIN*) e tem como objetivo realizar o cadastro de um " +
            "atendimento para um pet específco. Dessa forma, no objeto passado como parâmetro o atributo id referente ao pet deve " +
            "ser passado, para que o novo atendimento a ser cadastrado seja associado ao pet.")
    public AtendimentoModel salvarAtendimento(@Valid @RequestBody AtendimentoInput atendimentoInput);

    @Operation(summary = "Alterar registro de atendimento.",
    description = "Pode ser acessado por qualquer um dos perfis (*CLIENTE* ou *ADMIN*) e tem como objetivo realizar a alteração do atendimento " +
            "associado ao id informado.")
    public AtendimentoModel alterarAtendimento(@Valid @RequestBody AtendimentoInput atendimentoInput, @PathVariable Long idAtendimento);

    @Operation(summary = "Deletar registro de atendimento.",
    description = "Pode ser acessado por qualquer um dos perfis (*CLIENTE* ou *ADMIN*) e tem como objetivo realizar a exclusão do atendimento " +
            "associado ao id informado.")
    public void deletarAtendimento(@PathVariable Long idAtendimento);
}
