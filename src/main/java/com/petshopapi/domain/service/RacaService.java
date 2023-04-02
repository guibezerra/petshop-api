package com.petshopapi.domain.service;

import com.petshopapi.domain.exception.EntidadeNaoEncontradaException;
import com.petshopapi.domain.model.Raca;
import com.petshopapi.domain.repository.RacaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class RacaService {
    @Autowired
    RacaRepository racaRepository;

    private final String MSG_RACA_NAO_ENCONTRADA = "A raça não foi encontrada a partir do id informado. Por favor, tente novamente.";

    public Page<Raca> buscarTodos(Pageable pageable) {
        return racaRepository.findAll(pageable);
    }

    public Raca buscarPorId(Long idRaca) {
        return racaRepository.findById(idRaca)
                .orElseThrow(() -> new EntidadeNaoEncontradaException(MSG_RACA_NAO_ENCONTRADA));
    }

    public Raca salvar(Raca raca) {
        return racaRepository.save(raca);
    }

    public void deletar(Long idRaca) {
        racaRepository.deleteById(idRaca);
    }
}
