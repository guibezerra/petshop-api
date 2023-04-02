package com.petshopapi.domain.service;

import com.petshopapi.api.assembler.ContatoInputDisassembler;
import com.petshopapi.api.model.input.ContatoInput;
import com.petshopapi.domain.exception.EntidadeNaoEncontradaException;
import com.petshopapi.domain.model.*;
import com.petshopapi.domain.repository.ClienteRepository;
import com.petshopapi.domain.repository.ContatoRepository;
import com.petshopapi.domain.repository.EnderecoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

@Service
public class ClienteService {
    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ContatoRepository contatoRepository;

    @Autowired
    private EnderecoRepository enderecoRepository;

    @Autowired
    private ContatoInputDisassembler contatoInputDisassembler;

    private final int QUANTIDADE_MAXIMA_DE_CONTATOS_PERMITIDA_NUMA_LISTA = 2;

    private final String MSG_CLIENTE_NAO_ENCONTRADO_POR_CPF = "Não existe registros de usuario para o CPF informado.";

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional(readOnly = true)
    public Cliente buscarPorCpf(String cpf) {
       Cliente cliente =  clienteRepository.findByCpf(cpf)
                .orElseThrow(() -> new EntidadeNaoEncontradaException(MSG_CLIENTE_NAO_ENCONTRADO_POR_CPF));

        return cliente;
    }

    @Transactional
    public Cliente buscarPorId(Long idCliente) {
        return clienteRepository.findById(idCliente)
                .orElseThrow(() -> new RuntimeException());
    }

    @Transactional
    public Cliente salvar(Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    @Transactional
    public Cliente salvarRegistrosEAtualizar(Cliente clienteAtual, List<ContatoInput> contatoInputs) {
        validaEAtualizaContatosAtuaisComContatosInput(clienteAtual, contatoInputs);
        preparaESalvaContatos(clienteAtual, clienteAtual.getContatos());
        preparaESalvaEndereco(clienteAtual, clienteAtual.getEndereco());

        return clienteAtual;
    }

    private void validaEAtualizaContatosAtuaisComContatosInput(Cliente cliente, List<ContatoInput> contatosInput) {
        if(contatosInput.size() > QUANTIDADE_MAXIMA_DE_CONTATOS_PERMITIDA_NUMA_LISTA) {
            throw new RuntimeException();
        }

        List<Contato> contatosAtuais = new ArrayList<>();

        boolean existsContatosParaCliente = contatoRepository.existsByIdCliente(cliente.getIdCliente());
        if(existsContatosParaCliente) {
            Long idContato;
            int cont = 0;
            contatosAtuais = contatoRepository.findByIdCliente(cliente.getIdCliente());

            if (contatosAtuais.size() == contatosInput.size()) {
                for (Contato contato : contatosAtuais) {
                    idContato = contato.getIdContato();

                    contatoInputDisassembler.copyToDomainObject(contatosInput.get(cont), contato);
                    contato.setIdContato(idContato);
                    contato.setCliente(cliente);

                    cont++;
                }

                cliente.setContatos(contatosAtuais);

            } else if(contatosAtuais.size() < contatosInput.size()) {
                idContato = contatosAtuais.get(cont).getIdContato();

                contatoInputDisassembler.copyToDomainObject(contatosInput.get(cont),  contatosAtuais.get(cont));
                contatosAtuais.get(cont).setIdContato(idContato);

                cont++;

                Contato contatoParaAdicionar = contatoInputDisassembler.toDomainObject(contatosInput.get(cont));

                contatosAtuais.add(contatoParaAdicionar);

                cliente.setContatos(contatosAtuais);
            }
        } else {
            converteContatosInputParaDomainObject(contatosAtuais, contatosInput);

            cliente.setContatos(contatosAtuais);
        }
    }

    private void converteContatosInputParaDomainObject(List<Contato> contatos, List<ContatoInput> clienteInput) {
        clienteInput.stream()
                .forEach(contatoInput ->
                        contatos.add(contatoInputDisassembler.toDomainObject(contatoInput)));
    }

    private void preparaESalvaContatos(Cliente clienteSalvo, List<Contato> contatos) {
        contatos.stream().forEach(contato -> contato.setCliente(clienteSalvo));

        contatos = contatoRepository.saveAll(contatos);

        clienteSalvo.setContatos(contatos);
    }

    private void preparaESalvaEndereco(Cliente clienteSalvo, Endereco endereco) {
        boolean existsEnderecoParaCliente = enderecoRepository.existsByIdCliente(clienteSalvo.getIdCliente());
        if(existsEnderecoParaCliente) {
            Endereco enderecoAtual = enderecoRepository.findByIdCliente(clienteSalvo.getIdCliente()).get();

            endereco.setIdEndereco(enderecoAtual.getIdEndereco());
        }

        endereco.setCliente(clienteSalvo);

        endereco = enderecoRepository.save(endereco);

        clienteSalvo.setEndereco(endereco);
    }

    public boolean existsEnderecoParaCliente(Long idCliente) {
        return enderecoRepository.existsByIdCliente(idCliente);
    }

    public boolean existsContatosParaCliente(Long idCliente) {
        return contatoRepository.existsByIdCliente(idCliente);
    }

    public void deletarCliente(Cliente cliente) {
        clienteRepository.delete(cliente);
    }
}
