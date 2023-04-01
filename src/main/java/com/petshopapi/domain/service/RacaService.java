package com.petshopapi.domain.service;

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

    public Page<Raca> buscarTodos(Pageable pageable) {
        return racaRepository.findAll(pageable);
    }

    public Raca buscarPorId(Long idRaca) {
        return racaRepository.findById(idRaca)
                .orElseThrow(() -> new RuntimeException());
    }

    public Raca salvar(Raca raca) {
        return racaRepository.save(raca);
    }

    public void deletar(Long idRaca) {
        racaRepository.deleteById(idRaca);
    }
}
