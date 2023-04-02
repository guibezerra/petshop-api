package com.petshopapi.domain.service;

import com.petshopapi.api.model.input.ClienteInput;
import com.petshopapi.domain.exception.EntidadeNaoEncontradaException;
import com.petshopapi.domain.exception.NegocioException;
import com.petshopapi.domain.model.*;
import com.petshopapi.domain.repository.ContatoRepository;
import com.petshopapi.domain.repository.EnderecoRepository;
import com.petshopapi.domain.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class UsuarioService {
    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    private EnderecoRepository enderecoRepository;

    @Autowired
    private ContatoRepository contatoRepository;

    @Autowired
    private ClienteService clienteService;

    @Autowired PetService petService;

    @Autowired
    AtendimetoService atendimetoService;

    @PersistenceContext
    private EntityManager entityManager;

    private final String MSG_USUARIO_NAO_ENCONTRADO_POR_CPF = "Não existe registros de usuario para o CPF informado.";
    private final String MSG_USUARIO_NAO_ENCONTRADO_POR_ID = "Não existe registros de usuario para o id informado.";
    private final String MSG_CPF_JA_EXISTENTE = "Já existe registro de usuário com o CPF informado. Por Favor, insira um novo CPF.";

    @Transactional
    public Usuario buscarUsuarioPorCpf(String cpf) {
        Usuario usuario = usuarioRepository.findByCpf(cpf)
                .orElseThrow(() -> new EntidadeNaoEncontradaException(MSG_USUARIO_NAO_ENCONTRADO_POR_CPF));

        verificaSeUsuarioEClienteEBusca(usuario);

        return usuario;
    }

    private void verificaSeUsuarioEClienteEBusca(Usuario usuario) {
        if (usuario.getTipoPerfil().equals(TipoPerfil.CLIENTE)) {
            Cliente cliente = clienteService.buscarPorCpf(usuario.getCpf());

            usuario.setCliente(cliente);
        }
    }

    @Transactional
    public Usuario buscarUsuarioPorId(Long idUsuario) {
        return usuarioRepository.findByIdUsuario(idUsuario)
                .orElseThrow(() -> new EntidadeNaoEncontradaException(MSG_USUARIO_NAO_ENCONTRADO_POR_ID));
    }

    @Transactional
    public Page<Usuario> buscarTodosOsUsuarios(Pageable pageable) {
        return usuarioRepository.findAll(pageable);
    }

    @Transactional
    public Usuario salvarUsuario(Usuario usuario) {
        verificaSeCPFjaExiste(usuario);

        usuario = usuarioRepository.save(usuario);

        verificaSeUsuarioEClienteESalva(usuario);

        return usuario;
    }

    private void verificaSeCPFjaExiste(Usuario usuario) {
        if(usuarioRepository.existsByCpfETipo(usuario.getCpf())) {
            Usuario usuarioResultado = usuarioRepository.findByCpf(usuario.getCpf()).get();

            if(usuarioResultado.getIdUsuario() != usuario.getIdUsuario()) {
                throw new NegocioException(MSG_CPF_JA_EXISTENTE);
            }
        }
    }

    private void verificaSeUsuarioEClienteESalva(Usuario usuario) {
        if(usuario.getTipoPerfil().equals(TipoPerfil.CLIENTE)) {
            Cliente cliente = new Cliente(usuario, LocalDate.now());

            cliente = clienteService.salvar(cliente);

            usuario.setCliente(cliente);
        }
    }

    @Transactional
    public Usuario alterarUsuario(Usuario usuarioAtual) {
        entityManager.detach(usuarioAtual);

        verificaSeCPFjaExiste(usuarioAtual);

        usuarioRepository.save(usuarioAtual);

        return usuarioAtual;
    }

    @Transactional
    public void excluirUsuario(String cpf) {
<<<<<<< HEAD
        Usuario usuario = usuarioRepository.findByCpf(cpf).orElseThrow(() -> new RuntimeException());
=======
        verificaSeUsuarioEClienteEDeletaRegistros(cpf);

        Usuario usuario = buscarUsuarioPorCpf(cpf);

>>>>>>> d4d9440 (criação de classe para tratar exceções e implementação de validações)
        entityManager.detach(usuario);

        verificaSeUsuarioEClienteEDeletaRegistros(usuario);

        usuarioRepository.delete(usuario);
    }

<<<<<<< HEAD
    private void verificaSeUsuarioEClienteEDeletaRegistros(Usuario usuario) {
        if( usuario.getTipoPerfil().equals(TipoPerfil.CLIENTE) ) {
            Cliente cliente = clienteService.buscarPorCpf(usuario.getCpf());

=======
    private void verificaSeUsuarioEClienteEDeletaRegistros(String cpf) {
        Usuario usuario = buscarUsuarioPorCpf(cpf);

        if(usuario.getTipoPerfil().equals(TipoPerfil.CLIENTE)) {
            Cliente cliente = clienteService.buscarPorCpf(cpf);

>>>>>>> d4d9440 (criação de classe para tratar exceções e implementação de validações)
            entityManager.detach(cliente);

            if ( clienteService.existsEnderecoParaCliente(cliente.getIdCliente()) ){
                Endereco endereco =  enderecoRepository.findByIdCliente(cliente.getIdCliente()).get();

                enderecoRepository.delete(endereco);
            }

            if ( clienteService.existsContatosParaCliente(cliente.getIdCliente()) ) {
                List<Contato> contatos = contatoRepository.findByIdCliente(cliente.getIdCliente());

                contatoRepository.deleteAll(contatos);
            }

            if( atendimetoService.existisAtendimentoParaCliente(cliente.getIdCliente()) ) {
                Pageable pageable = null;
                List<Atendimento> atendimentoList = atendimetoService.buscarTodosParaCliente(cliente.getIdCliente(), pageable).toList();

                atendimetoService.deletarTodos(atendimentoList);
            }

            if( petService.existsPetParaCliente(cliente.getIdCliente()) ) {
                List<Pet> pet = petService.buscarPorIdCliente(cliente.getIdCliente());

                petService.deletarTodos(pet);
            }

            clienteService.deletarCliente(cliente);
        }
    }

    @Transactional
    public void atualizaDadosDeUsuarioAPartirDeClienteInput(ClienteInput clienteInput, Usuario usuarioAtual) {
        entityManager.detach(usuarioAtual);

        usuarioAtual.setNome(clienteInput.getNome());
        usuarioAtual.setCpf(clienteInput.getCpf());

        verificaSeCPFjaExiste(usuarioAtual);

        usuarioRepository.save(usuarioAtual);
    }
}
