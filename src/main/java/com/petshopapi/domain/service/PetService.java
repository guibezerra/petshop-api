package com.petshopapi.domain.service;

import com.petshopapi.domain.exception.EntidadeNaoEncontradaException;
import com.petshopapi.domain.model.Cliente;
import com.petshopapi.domain.model.Pet;
import com.petshopapi.domain.model.Raca;
import com.petshopapi.domain.repository.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PetService {
    @Autowired
    PetRepository petRepository;

    @Autowired
    RacaService racaService;

    @Autowired
    ClienteService clienteService;

    private final String MSG_PET_NAO_ENCONTRADO = "Não foi encontrado registro de Pet com o id informado. Por favor, tente novamente.";

    @Transactional
    public Page<Pet> buscarTodos(Long idCliente, Pageable pageable) {
        return petRepository.findAllByCliente(idCliente, pageable);
    }

    @Transactional
    public Pet buscarPorId(Long idPet) {
        return petRepository.findById(idPet)
                .orElseThrow(() -> new EntidadeNaoEncontradaException(MSG_PET_NAO_ENCONTRADO));
    }

    @Transactional
    public Pet salvar(Pet pet) {
        preparaPetParaSalvar(pet);

        return petRepository.save(pet);
    }

    private void preparaPetParaSalvar(Pet pet) {
        Raca raca = racaService.buscarPorId(pet.getRaca().getIdRaca());
        Cliente cliente = clienteService.buscarPorId(pet.getCliente().getIdCliente());

        pet.setRaca(raca);
        pet.setCliente(cliente);
    }

    public void deletar(Long idPet) {
        Pet pet = buscarPorId(idPet);

        petRepository.delete(pet);
    }

    @Transactional
    public boolean existsPetParaCliente(Long idCliente) {
        return petRepository.existsByIdCliente(idCliente);
    }

    @Transactional
    public List<Pet> buscarPorIdCliente(Long idCliente) {
        return petRepository.findByIdCliente(idCliente);
    }

    @Transactional
    public void deletarTodos(List<Pet> petList) {
        petRepository.deleteAll(petList);
    }
}
