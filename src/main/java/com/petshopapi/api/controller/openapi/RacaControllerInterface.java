package com.petshopapi.api.controller.openapi;

import com.petshopapi.api.model.RacaModel;
import com.petshopapi.api.model.input.RacaInput;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

@Tag(name = "Raça", description = "Endpoint responsável por realizar o gerenciamento das raças de animais no sistema, desde a operação de cadastramento a de deleção.")
public interface RacaControllerInterface {
    @Operation(summary = "Buscar todas as raças de animais.",
            description = "Pode ser acessado por qualquer um dos perfis (*CLIENTE* ou *ADMIN*) e tem como objetivo retornar todas " +
                    "as raças cadastradas na base dados.")
    public Page<RacaModel> buscarTodasAsRacas(@PageableDefault(size = 10) Pageable pageable);

    @Operation(summary = "Buscar uma raça de animal específica.",
                description = "Pode ser acessado por qualquer um dos perfis (*CLIENTE* ou *ADMIN*) e tem como objetivo retornar " +
                        "a raça associada ao id passado como parâmetro de busca.")
    public RacaModel buscarPorId(@PathVariable Long idRaca);

    @Operation(summary = "Cadastrar uma nova raça de animal.",
               description = "Pode ser acessado apenas por um usuário do tipo ADMIN e tem como objetivo cadastrar uma nova raça de " +
                       "animal na base de dados do sistema.")
    public RacaModel cadastrarRaca(@RequestBody @Valid RacaInput racaInput);

    @Operation(summary = "Alterar raça de animal.",
            description = "Pode ser acessado apenas por um usuário do tipo ADMIN e tem como objetivo alterar as informações da " +
                    "raça associada ao id passado como parâmetro.")
    public RacaModel atualizarRaca(@PathVariable Long idRaca, @Valid @RequestBody RacaInput racaInput);

    @Operation(summary = "Deletar raça de animal.",
    description = "Pode ser acessado apenas por um usuário do tipo ADMIN e tem como objetivo deletar a raça " +
            "associada ao id passado como parâmetro.")
    public void deletarRaca(@PathVariable Long idRaca);
}
