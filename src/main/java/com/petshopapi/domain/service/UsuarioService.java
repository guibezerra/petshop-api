package com.petshopapi.domain.service;

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
        return usuarioRepository.findByCpf(cpf)
                .orElseThrow(() -> new RuntimeException());
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
    public List<Object> salvarUsuario(Usuario usuario) {
        List<Object> resultadoDoCadastro = new ArrayList<>();

        verificaSeUsuarioEClienteESalva(usuario, resultadoDoCadastro);

        usuario = usuarioRepository.save(usuario);

        resultadoDoCadastro.add(usuario);

        return resultadoDoCadastro;
    }

    private void verificaSeUsuarioEClienteESalva(Usuario usuario, List<Object> resultadoDoCadastro) {
        if(usuario.getTipoPerfil().equals(TipoPerfil.CLIENTE)) {
            Cliente cliente = new Cliente(usuario.getNome(), usuario.getCpf(), LocalDate.now());
            cliente = clienteRepository.save(cliente);

            resultadoDoCadastro.add(cliente);
        }
    }

    @Transactional
    public Usuario alterarUsuario(Usuario usuarioAtual, String cpfAntigo) {
        entityManager.detach(usuarioAtual);

        verificaSeUsuarioEClienteEAtualizaDados(usuarioAtual, cpfAntigo);

        usuarioRepository.updateUsuario(usuarioAtual.getCpf(), usuarioAtual.getNome(), usuarioAtual.getSenha(), usuarioAtual.getIdUsuario());

        return usuarioAtual;
    }

    private void verificaSeUsuarioEClienteEAtualizaDados(Usuario usuarioAtual, String cpfAntigo) {
        Cliente cliente = clienteService.buscarPorCpf(cpfAntigo);

        if(!Objects.isNull(cliente)) {
            cliente.setNome(usuarioAtual.getNome());
            cliente.setCpf(usuarioAtual.getCpf());

            clienteRepository.save(cliente);
        }
    }

    @Transactional
    public void excluirUsuario(String cpf) {
        verificaSeUsuarioEClienteEDeletaRegistros(cpf);

        usuarioRepository.deleteByCpf(cpf);
    }

    private void verificaSeUsuarioEClienteEDeletaRegistros(String cpf) {
        Cliente cliente = clienteService.buscarPorCpf(cpf);

        if(!Objects.isNull(cliente)) {
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
    public void atualizaDadosDeUsuarioAPartirDeCliente(Cliente clienteAtual, String cpfAntigo) {
        Usuario usuarioAtual = usuarioRepository.findByCpf(cpfAntigo).get();
        Long idUsuario = usuarioAtual.getIdUsuario();

        entityManager.detach(usuarioAtual);

        usuarioAtual.setIdUsuario(idUsuario);
        usuarioAtual.setNome(clienteAtual.getNome());
        usuarioAtual.setCpf(clienteAtual.getCpf());

        usuarioRepository.updateUsuario(usuarioAtual.getCpf(), usuarioAtual.getNome(),
                usuarioAtual.getSenha(), usuarioAtual.getIdUsuario());
    }
}
