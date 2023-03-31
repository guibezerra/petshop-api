package com.petshopapi.api.controller;

import com.petshopapi.api.assembler.*;
import com.petshopapi.api.model.ClienteModel;
import com.petshopapi.api.model.ContatoModel;
import com.petshopapi.api.model.EnderecoModel;
import com.petshopapi.api.model.input.ClienteInput;
import com.petshopapi.api.model.input.ContatoInput;
import com.petshopapi.domain.model.Cliente;
import com.petshopapi.domain.model.Contato;
import com.petshopapi.domain.model.Endereco;
import com.petshopapi.domain.service.ClienteService;
import com.petshopapi.domain.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping(value="/cliente")
public class ClienteController {
    @Autowired
    private ClienteService clienteService;

    @Autowired
    private ClienteInputDisassembler clienteInputDisassembler;

    @Autowired
    private ClienteModelAssembler clienteModelAssembler;

    @Autowired
    private ContatoInputDisassembler contatoInputDisassembler;

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
    public ClienteModel alterarCliente(@PathVariable Long idCliente, @RequestBody ClienteInput clienteInput) {
        Cliente clienteAtual = clienteService.buscarPorId(idCliente);
        String cpfAntesDeAtualizar = clienteAtual.getCpf();

        clienteInputDisassembler.copyToDomainObjectSkippingProperties(clienteInput, clienteAtual);
        clienteAtual.setIdCliente(idCliente);

        usuarioService.atualizaDadosDeUsuarioAPartirDeCliente(clienteAtual, cpfAntesDeAtualizar);

        if(Objects.isNull(clienteAtual.getEndereco())) {
            Endereco endereco = enderecoInputDisassembler.toDomainObjectSkippingProperties(clienteInput.getEndereco());

            clienteAtual.setEndereco(endereco);

        } else {
            enderecoInputDisassembler.copyToDomainObject(clienteInput.getEndereco(), clienteAtual.getEndereco());
        }

        List<ContatoInput> contatoInputs = clienteInput.getContato();

        List<Object> resultadoCadastramento = clienteService.salvarRegistrosEAtualizar(clienteAtual, contatoInputs);

        return retornaClienteModel(resultadoCadastramento);
    }

    private ClienteModel retornaClienteModel(List<Object> resultadoCadastramento) {
        ClienteModel clienteModel = new ClienteModel();
        for (Object objeto : resultadoCadastramento) {
            if(objeto.getClass().equals(Cliente.class)) {
                clienteModel = clienteModelAssembler.toModel((Cliente) objeto);
            }

            if (objeto.getClass().equals(Endereco.class)) {
                EnderecoModel enderecoModel = enderecoModelAssembler.toModel((Endereco) objeto);

                clienteModel.setEndereco(enderecoModel);
            }

            if (objeto instanceof List<?>) {
                List<ContatoModel> contatoModels = contatoModelAssembler.toCollectionModel((List<Contato>) objeto);

                clienteModel.setContato(contatoModels);
            }
        }

        return clienteModel;
    }

}
