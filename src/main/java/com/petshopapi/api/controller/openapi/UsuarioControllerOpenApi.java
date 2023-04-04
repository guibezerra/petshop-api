package com.petshopapi.api.controller.openapi;

import com.petshopapi.api.model.UsuarioModel;
import com.petshopapi.api.model.input.UsuarioInput;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

@Tag(name = "Usuário", description = "Endpoint responsável por realizar o gerenciamento dos usuários no sistema, desde a operação de cadastramento a de deleção.")
public interface UsuarioControllerOpenApi {
    @Operation(summary = "Buscar por um usuário utilizando o seu CPF.",
    description = "Somente pode ser acessado por usuários do tipo *ADMIN* e tem como objetivo buscar por um usuário por meio do CPF informado.")
    public UsuarioModel buscarUsuarioPorCpf(@PathVariable String cpf);

    @Operation(summary = "Buscar por todos os usuários.",
            description = "Somente pode ser acessado por usuários do tipo *ADMIN* e tem como objetivo  retornar todos os usuários cadastrados na base de dados.")
    public Page<UsuarioModel> buscarTodosOsUsarios(@PageableDefault(size = 10) Pageable pageable);

    @Operation(summary = "Cadastrar um novo usuário.",
            description = "Pode ser acessado por qualquer um, mesmo sem ter cadastro prévio no sistema, e tem como objetivo cadastrar um novo usuário na base de dados." +
                    " Durante o processo de cadastramento, caso seja identificado que o usuário é do tipo *CLIENTE* será inserido um registro de cliente para este.")
    public UsuarioModel cadastrarUsuario(@Valid @RequestBody UsuarioInput usuarioInput);

    @Operation(summary = "Alterar registro de um usuário.",
            description = "Somente pode ser acessado por usuários do tipo *ADMIN* e tem como objetivo alterar informações cadastrais de um usuário por meio de seu id.")
    public UsuarioModel alterarUsuario(@PathVariable Long idUsuario, @Valid @RequestBody UsuarioInput usuarioInput);

    @Operation(summary = "Deletar registro de usuário.",
            description = "Somente pode ser acessado por usuários do tipo *ADMIN* e tem como objetivo deletar um usuário por meio do CPF informado. Caso o usuário a ser deletado seja do tipo *CLIENTE*, são excluídos os registros de cliente, pet e atendimento associados a ele.")
    public void deletarUsuarioPorCpf(@PathVariable String cpf);
}
