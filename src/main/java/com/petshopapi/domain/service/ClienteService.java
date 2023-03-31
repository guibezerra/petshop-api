package com.petshopapi.domain.service;

import com.petshopapi.api.assembler.ContatoInputDisassembler;
import com.petshopapi.api.model.input.ContatoInput;
import com.petshopapi.domain.model.Cliente;
import com.petshopapi.domain.model.Contato;
import com.petshopapi.domain.model.Endereco;
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

    final int QUANTIDADE_MAXIMA_DE_CONTATOS_PERMITIDA_NUMA_LISTA = 2;

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional(readOnly = true)
    public Cliente buscarPorCpf(String cpf) {
        return clienteRepository.findByCpf(cpf)
                .orElseThrow(() -> new RuntimeException());
    }

    @Transactional
    public Cliente buscarPorId(Long idCliente) {
        return clienteRepository.findById(idCliente)
                .orElseThrow(() -> new RuntimeException());
    }

    @Transactional
    public List<Object> salvarRegistrosEAtualizar(Cliente clienteAtual, List<ContatoInput> contatoInputs) {
        entityManager.detach(clienteAtual);

        validaEAtualizaContatosAtuaisComContatosInput(clienteAtual, contatoInputs);
        preparaESalvaContatos(clienteAtual, clienteAtual.getContatos());
        preparaESalvaEndereco(clienteAtual, clienteAtual.getEndereco());

        return salvaEPreparaClienteParaRetorno(clienteAtual);
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

        contatos = contatoRepository.saveAllAndFlush(contatos);

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

    private List<Object> salvaEPreparaClienteParaRetorno(Cliente clienteAtual) {
        Endereco enderecoSalvo = clienteAtual.getEndereco();
        List<Contato> contatosSalvos = clienteAtual.getContatos();

        clienteRepository.save(clienteAtual);

        List<Object> resultadoCadastramentro = new ArrayList<>();

        resultadoCadastramentro.add(clienteAtual);
        resultadoCadastramentro.add(enderecoSalvo);
        resultadoCadastramentro.add(contatosSalvos);

        return resultadoCadastramentro;
    }
}
