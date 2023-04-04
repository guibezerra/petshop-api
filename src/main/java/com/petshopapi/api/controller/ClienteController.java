package com.petshopapi.api.controller;

import com.petshopapi.api.assembler.*;
import com.petshopapi.api.controller.openapi.ClienteControllerOpenApi;
import com.petshopapi.api.model.ClienteModel;
import com.petshopapi.api.model.ContatoModel;
import com.petshopapi.api.model.input.ClienteInput;
import com.petshopapi.api.model.input.ContatoInput;
import com.petshopapi.domain.model.Cliente;
import com.petshopapi.domain.model.Endereco;
import com.petshopapi.domain.model.Usuario;
import com.petshopapi.domain.service.ClienteService;
import com.petshopapi.domain.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping(value="/cliente")
public class ClienteController implements ClienteControllerOpenApi {
    @Autowired
    private ClienteService clienteService;

    @Autowired
    private ClienteInputDisassembler clienteInputDisassembler;

    @Autowired
    private ClienteModelAssembler clienteModelAssembler;

    @Autowired
    private ContatoModelAssembler contatoModelAssembler;

    @Autowired
    private EnderecoInputDisassembler enderecoInputDisassembler;

    @Autowired
    EnderecoModelAssembler enderecoModelAssembler;

    @Autowired
    UsuarioService usuarioService;

    @GetMapping("/{idCliente}")
    public ClienteModel buscarClientePorId(@PathVariable Long idCliente) {
        Cliente cliente = clienteService.buscarPorId(idCliente);

        ClienteModel clienteModel = clienteModelAssembler.toModel(cliente);

        return clienteModel;
    }

    @PutMapping("/{idCliente}")
    public ClienteModel alterarCliente(@PathVariable Long idCliente, @Valid @RequestBody ClienteInput clienteInput) {
        Cliente clienteAtual = clienteService.buscarPorId(idCliente);
        Usuario usuarioAtual = usuarioService.buscarUsuarioPorId(clienteAtual.getUsuario().getIdUsuario());

        clienteInputDisassembler.copyToDomainObjectSkippingProperties(clienteInput, clienteAtual);
        clienteAtual.setIdCliente(idCliente);

        usuarioService.atualizaDadosDeUsuarioAPartirDeClienteInput(clienteInput, usuarioAtual);
        clienteAtual.setUsuario(usuarioAtual);

        if(Objects.isNull(clienteAtual.getEndereco())) {
            Endereco endereco = enderecoInputDisassembler.toDomainObject(clienteInput.getEndereco());

            clienteAtual.setEndereco(endereco);

        } else {
            enderecoInputDisassembler.copyToDomainObject(clienteInput.getEndereco(), clienteAtual.getEndereco());
        }

        List<ContatoInput> contatoInputs = clienteInput.getContato();

        Cliente clienteSalvo = clienteService.salvarRegistrosEAtualizar(clienteAtual, contatoInputs);

        return retornaClienteModel(clienteSalvo);
    }

    private ClienteModel retornaClienteModel(Cliente clienteSalvo) {
        ClienteModel clienteModel = clienteModelAssembler.toModel(clienteSalvo);

        List<ContatoModel> contatoModels = contatoModelAssembler.toCollectionModel(clienteSalvo.getContatos());

        clienteModel.setContato(contatoModels);

        return clienteModel;
    }

}
