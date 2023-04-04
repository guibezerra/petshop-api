package com.petshopapi.api.controller.openapi;

import com.petshopapi.api.model.input.LoginInput;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "Login", description = "Endpoint responsável por permitir que um usuário logue no sistema.")
public interface AuthControllerOpenApi {
    @Operation(summary = "Logar usuário no sistema.",
    description = "Pode ser acessado por qualquer um, mesmo não sendo cadastrado no sistema. Este método recebe como parâmetro" +
            " um objeto contendo o *username (cpf)* e *senha* de um usuário para que seja verificado se este possui " +
            "cadastro no sistema. Caso seja um usuário devidamente cadastrado, é retornado um token JWT que deve ser utilizado " +
            "para realizar qualquer uma das operações disponíveis pelos endpoints da aplicação, que irão funcionar apenas com o token" +
            " sendo informado no HEAD da requisição, com exceção do método *cadastrarUsuario* do endpoint */usuario*," +
            " que também pode ser acessado por pessoas não autenticadas.")
    public String login(@RequestBody LoginInput login);
}
