package com.petshopapi.api.controller;

import com.petshopapi.api.assembler.*;
import com.petshopapi.api.model.ClienteModel;
import com.petshopapi.api.model.input.ClienteInput;
import com.petshopapi.api.model.input.ContatoInput;
import com.petshopapi.domain.model.Cliente;
import com.petshopapi.domain.model.Contato;
import com.petshopapi.domain.model.Endereco;
import com.petshopapi.domain.service.ClienteService;
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


    @GetMapping("/{idCliente}")
    public ClienteModel buscarClientePorId(@PathVariable Long idCliente) {
        Cliente cliente = clienteService.buscarPorId(idCliente);

        ClienteModel clienteModel = clienteModelAssembler.toModel(cliente);

        return clienteModel;
    }

//    @PostMapping
//    @ResponseStatus(HttpStatus.CREATED)
//    public ClienteModel salvarCliente(@RequestBody ClienteInput clienteInput) {
//        Cliente cliente = clienteInputDisassembler.toDomainObject(clienteInput);
//
//        List<Contato> contatos = new ArrayList<>();
//        converterContatosInputParaDomainObjectEAssociaACliente(contatos, clienteInput, cliente);
//
//        cliente = clienteService.salvarCliente(cliente);
//
//        return clienteModelAssembler.toModel(cliente);
//    }

    private void converterContatosInputParaDomainObjectEAssociaACliente(List<Contato> contatos, ClienteInput clienteInput, Cliente cliente) {
        clienteInput.getContato().stream()
                .forEach(contatoInput ->
                        contatos.add(contatoInputDisassembler.toDomainObject(contatoInput)));

        cliente.setContatos(contatos);
    }

    private ClienteModel associaResultadosAosDomainObjectsERetornaClienteModel(List<Object> resultadoCadastramento, Cliente cliente, Endereco endereco,  List<Contato> contatos) {
        ClienteModel clienteModel = clienteModelAssembler.toModel(cliente);
        clienteModel.setEndereco(enderecoModelAssembler.toModel(endereco));
        clienteModel.setContato(contatoModelAssembler.toCollectionModel(contatos));

        return clienteModel;
    }

    @PutMapping("/{idCliente}")
    public Cliente alterarCliente(@PathVariable Long idCliente, @RequestBody ClienteInput clienteInput) {
        Cliente clienteAtual = clienteService.buscarPorId(idCliente);

        clienteInputDisassembler.copyToDomainObject(clienteInput, clienteAtual);
        clienteAtual.setIdCliente(idCliente);

        if(Objects.isNull(clienteAtual.getEndereco())) {
            Endereco endereco = enderecoInputDisassembler.toDomainObject(clienteInput.getEndereco());

            clienteAtual.setEndereco(endereco);

        } else {
            enderecoInputDisassembler.copyToDomainObject(clienteInput.getEndereco(), clienteAtual.getEndereco());
        }

        List<ContatoInput> contatoInputs = clienteInput.getContato();

        Cliente clienteSalvo = clienteService.salvarRegistrosEAtualizar(clienteAtual, contatoInputs);

        return clienteSalvo;
    }

}
