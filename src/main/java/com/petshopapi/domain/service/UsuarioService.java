package com.petshopapi.domain.service;

import com.petshopapi.api.model.input.ClienteInput;
import com.petshopapi.domain.model.*;
import com.petshopapi.domain.repository.ClienteRepository;
import com.petshopapi.domain.repository.ContatoRepository;
import com.petshopapi.domain.repository.EnderecoRepository;
import com.petshopapi.domain.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class UsuarioService {
    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    ClienteRepository clienteRepository;

    @Autowired
    EnderecoRepository enderecoRepository;

    @Autowired
    ContatoRepository contatoRepository;

    @Autowired
    private ClienteService clienteService;

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public Usuario buscarUsuarioPorCpf(String cpf) {
        Usuario usuario = usuarioRepository.findByCpf(cpf)
                .orElseThrow(() -> new RuntimeException());

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
                .orElseThrow(() -> new RuntimeException());
    }

    @Transactional
    public Page<Usuario> buscarTodosOsUsuarios(Pageable pageable) {
        return usuarioRepository.findAll(pageable);
    }

    @Transactional
    public Usuario salvarUsuario(Usuario usuario) {
        List<Object> resultadoDoCadastro = new ArrayList<>();

        usuario = usuarioRepository.save(usuario);

        resultadoDoCadastro.add(usuario);

        verificaSeUsuarioEClienteESalva(usuario, resultadoDoCadastro);

        return usuario;
    }

    private void verificaSeUsuarioEClienteESalva(Usuario usuario, List<Object> resultadoDoCadastro) {
        if(usuario.getTipoPerfil().equals(TipoPerfil.CLIENTE)) {
            Cliente cliente = new Cliente(usuario, LocalDate.now());

            cliente = clienteRepository.save(cliente);

            usuario.setCliente(cliente);

            resultadoDoCadastro.add(cliente);
        }
    }

    @Transactional
    public Usuario alterarUsuario(Usuario usuarioAtual) {
        entityManager.detach(usuarioAtual);

        usuarioRepository.save(usuarioAtual);

        return usuarioAtual;
    }

    @Transactional
    public void excluirUsuario(String cpf) {
        verificaSeUsuarioEClienteEDeletaRegistros(cpf);

        Usuario usuario = usuarioRepository.findByCpf(cpf).orElseThrow(() -> new RuntimeException());

        entityManager.detach(usuario);

        usuarioRepository.delete(usuario);
    }

    private void verificaSeUsuarioEClienteEDeletaRegistros(String cpf) {
        Cliente cliente = clienteService.buscarPorCpf(cpf);

        entityManager.detach(cliente);

        if(Objects.nonNull(cliente)) {
            if ( existsEnderecoParaCliente(cliente.getIdCliente()) ){
                Endereco endereco =  enderecoRepository.findByIdCliente(cliente.getIdCliente()).get();

                enderecoRepository.delete(endereco);
            }

            if ( existsContatosParaCliente(cliente.getIdCliente()) ) {
                List<Contato> contatos = contatoRepository.findByIdCliente(cliente.getIdCliente());

                contatoRepository.deleteAll(contatos);
            }

            clienteRepository.delete(cliente);
        }
    }

    private boolean existsEnderecoParaCliente(Long idCliente) {
        return enderecoRepository.existsByIdCliente(idCliente);
    }

    private boolean existsContatosParaCliente(Long idCliente) {
        return contatoRepository.existsByIdCliente(idCliente);
    }

    @Transactional
    public void atualizaDadosDeUsuarioAPartirDeClienteInput(ClienteInput clienteInput, Usuario usuarioAtual) {
        entityManager.detach(usuarioAtual);

        usuarioAtual.setNome(clienteInput.getNome());
        usuarioAtual.setCpf(clienteInput.getCpf());

        usuarioRepository.save(usuarioAtual);
    }
}
