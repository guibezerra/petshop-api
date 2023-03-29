package com.petshopapi.domain.service;

import com.petshopapi.api.assembler.ClienteInputDisassembler;
import com.petshopapi.api.assembler.ContatoInputDisassembler;
import com.petshopapi.api.model.input.ClienteInput;
import com.petshopapi.api.model.input.ContatoInput;
import com.petshopapi.domain.model.*;
import com.petshopapi.domain.repository.ClienteRepository;
import com.petshopapi.domain.repository.ContatoRepository;
import com.petshopapi.domain.repository.EnderecoRepository;
import com.petshopapi.domain.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class ClienteService {
    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ContatoRepository contatoRepository;

    @Autowired
    private EnderecoRepository enderecoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

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

//    @Transactional
//    public Cliente salvarCliente(Cliente cliente) {
//        vertificaSeClientePossuiCadastroDeUsuario(cliente);
//
//        Cliente clienteSalvo = atualizaSalvaERetornaObjetoCliente(cliente);
//
//        preparaESalvaEndereco(clienteSalvo, cliente.getEndereco());
//        preparaESalvaContatos(clienteSalvo, cliente.getContatos());
//
//        return clienteSalvo;
//    }
//
//    private void vertificaSeClientePossuiCadastroDeUsuario(Cliente cliente) {
//        boolean isUsuarioTipoCliente = usuarioRepository.existsByCpfETipo(cliente.getCpf(), TipoPerfil.CLIENTE);
//
//        if(isUsuarioTipoCliente) {
//            Usuario usuarioAtual = usuarioRepository.findByCpf(cliente.getCpf()).get();
//
//            entityManager.detach(usuarioAtual);
//
//            usuarioAtual.setNome(cliente.getNome());
//
//            usuarioRepository.save(usuarioAtual);
//
//        } else {
//            throw new RuntimeException();
//        }
//    }
//
//    private Cliente atualizaSalvaERetornaObjetoCliente(Cliente cliente) {
//        Cliente clienteAtual = buscarPorCpf(cliente.getCpf());
//
//        clienteAtual.setNome(cliente.getNome());
//
//        return clienteRepository.save(clienteAtual);
//    }
//
//    private void preparaESalvaEndereco(Cliente clienteSalvo, Endereco endereco) {
//        endereco.setCliente(clienteSalvo);
//
//        endereco = enderecoRepository.save(endereco);
//
//        clienteSalvo.setEndereco(endereco);
//    }
//
//    private void preparaESalvaContatos(Cliente clienteSalvo, List<Contato> contatos) {
//        contatos.stream().forEach(contato -> contato.setCliente(clienteSalvo));
//
//        contatos = contatoRepository.saveAll(contatos);
//
//        clienteSalvo.setContatos(contatos);
//    }

    @Transactional
    public Cliente salvarRegistrosEAtualizar(Cliente clienteAtual, List<ContatoInput> contatoInputs) {
        entityManager.detach(clienteAtual);

//        validaEAtualizaContatosAtuaisComContatosInput(clienteAtual, contatoInputs);
//        preparaESalvaContatos(clienteAtual, clienteAtual.getContatos());
//        preparaESalvaEndereco(clienteAtual, clienteAtual.getEndereco());
//        atualizaDadosDeUsuario(clienteAtual);

        return clienteRepository.save(clienteAtual);
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

    private void atualizaDadosDeUsuario(Cliente clienteAtual) {
        Usuario usuarioAtual = usuarioRepository.findByCpf(clienteAtual.getCpf()).get();

        entityManager.detach(usuarioAtual);

        usuarioAtual.setNome(clienteAtual.getNome());
        usuarioAtual.setCpf(clienteAtual.getCpf());

        usuarioRepository.save(usuarioAtual);
    }

}
