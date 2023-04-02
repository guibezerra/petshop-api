package com.petshopapi.domain.service;

import com.petshopapi.api.model.input.AtendimentoInput;
import com.petshopapi.domain.exception.EntidadeNaoEncontradaException;
import com.petshopapi.domain.model.Atendimento;
import com.petshopapi.domain.model.Pet;
import com.petshopapi.domain.repository.AtendimentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AtendimetoService {
    @Autowired
    AtendimentoRepository atendimentoRepository;

    @Autowired
    PetService petService;

    private final String MSG_ATENDIMENTO_NA_ENCONTRADO = "Não foram encontrados registros de atendimento com o id informado. Por favor, tente novamente.";

    @Transactional
    public Page<Atendimento> buscarTodosParaCliente(Long idCliente, Pageable pageable) {
        return atendimentoRepository.findAllByIdCliente(idCliente, pageable);
    }

    @Transactional
    public Atendimento buscarPorId(Long idAtendimento) {
        return atendimentoRepository.findById(idAtendimento)
                                    .orElseThrow(() -> new EntidadeNaoEncontradaException(MSG_ATENDIMENTO_NA_ENCONTRADO));
    }

    @Transactional
    public boolean existisAtendimentoParaCliente(Long idCliente) {
        return atendimentoRepository.existsByIdCliente(idCliente);
    }

    @Transactional
    public Atendimento salvarAtentimento(Atendimento atendimento) {
        prepararAtendimentoParaSalvar(atendimento);

        return atendimentoRepository.save(atendimento);
    }

    private void prepararAtendimentoParaSalvar(Atendimento atendimento) {
        Pet pet = petService.buscarPorId(atendimento.getPet().getIdPet());

        atendimento.setPet(pet);
    }

    @Transactional
    public void deletarTodos(List<Atendimento> atendimentoList) {
        atendimentoRepository.deleteAll(atendimentoList);
    }

    public void deletarAtendimento(Long idAtendimento) {
        Atendimento atendimento = buscarPorId(idAtendimento);

        atendimentoRepository.delete(atendimento);
    }
}
