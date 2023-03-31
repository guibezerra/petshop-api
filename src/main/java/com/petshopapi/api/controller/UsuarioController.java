package com.petshopapi.api.controller;


import com.petshopapi.api.assembler.ClienteModelAssembler;
import com.petshopapi.api.assembler.UsuarioInputDisassembler;
import com.petshopapi.api.assembler.UsuarioModelAssembler;
import com.petshopapi.api.model.ClienteModel;
import com.petshopapi.api.model.UsuarioModel;
import com.petshopapi.api.model.input.UsuarioInput;
import com.petshopapi.domain.model.Cliente;
import com.petshopapi.domain.model.Usuario;
import com.petshopapi.domain.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value="/usuario")
public class UsuarioController {
    @Autowired
    UsuarioInputDisassembler usuarioInputDisassembler;

    @Autowired
    UsuarioModelAssembler usuarioModelAssembler;

    @Autowired
    private ClienteModelAssembler clienteModelAssembler;

    @Autowired
    UsuarioService usuarioService;

    @GetMapping("/{cpf}")
    public UsuarioModel buscarUsuarioPorCpf(@PathVariable String cpf) {
        Usuario usuario = usuarioService.buscarUsuarioPorCpf(cpf);
        UsuarioModel usuarioModel = usuarioModelAssembler.toModel(usuario);

        return usuarioModel;
    }

    @GetMapping
    public Page<UsuarioModel> buscarTodosOsUsarios(@PageableDefault(size = 10) Pageable pageable) {
        Page<Usuario> usuariosPage = usuarioService.buscarTodosOsUsuarios(pageable);

        return retornaUsuariosModelPaginado(usuariosPage, pageable);
    }

    private Page<UsuarioModel> retornaUsuariosModelPaginado(Page<Usuario> usuariosPage, Pageable pageable) {
        Page<UsuarioModel> usuariosModelPage = new PageImpl<>(usuarioModelAssembler.toCollectionModel(usuariosPage.getContent()),
                pageable,usuariosPage.getTotalElements());

        return usuariosModelPage;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UsuarioModel cadastrarUsuario(@RequestBody UsuarioInput usuarioInput) {
        Usuario usuario = usuarioInputDisassembler.toDomainObjectSkippingProperties(usuarioInput);

        List<Object> resultadoDoCadastramento = usuarioService.salvarUsuario(usuario);

        UsuarioModel usuarioModel = converteResultadoDoCadastramentoERetornaUsuarioModel(resultadoDoCadastramento);

        return usuarioModel;
    }

    private UsuarioModel converteResultadoDoCadastramentoERetornaUsuarioModel(List<Object> resultadoDoCadastramento) {
        UsuarioModel usuarioModel = new UsuarioModel();

        for (Object objeto : resultadoDoCadastramento) {
            if (objeto.getClass().equals(Cliente.class)) {
                ClienteModel clienteModel = clienteModelAssembler.toModel((Cliente) objeto);

                usuarioModel.setClienteModel(clienteModel);

            } else if(objeto.getClass().equals(Usuario.class)) {
                usuarioModel = usuarioModelAssembler.toModel((Usuario) objeto);
            }
        }

        return usuarioModel;
    }

    @PutMapping("/{idUsuario}")
    public UsuarioModel alterarUsuario(@PathVariable Long idUsuario, @RequestBody UsuarioInput usuarioInput) {
        Usuario usuarioAtual = usuarioService.buscarUsuarioPorId(idUsuario);
        String cpfAntigo = usuarioAtual.getCpf();

        usuarioInputDisassembler.copyToDomainObjectSkippingProperties(usuarioInput, usuarioAtual);

        usuarioAtual.setIdUsuario(idUsuario);

        usuarioAtual = usuarioService.alterarUsuario(usuarioAtual, cpfAntigo);

        return usuarioModelAssembler.toModel(usuarioAtual);
    }

    @DeleteMapping("/{cpf}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletarUsuarioPorCpf(@PathVariable String cpf) {

        usuarioService.excluirUsuario(cpf);
    }
}
