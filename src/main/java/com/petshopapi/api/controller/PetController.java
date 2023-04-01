package com.petshopapi.api.controller;

import com.petshopapi.api.assembler.PetInputDisassembler;
import com.petshopapi.api.assembler.PetModelAssembler;
import com.petshopapi.api.model.PetModel;
import com.petshopapi.api.model.input.PetInput;
import com.petshopapi.domain.model.Pet;
import com.petshopapi.domain.service.PetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pet")
public class PetController {
    @Autowired
    PetService petService;

    @Autowired
    PetInputDisassembler petInputDisassembler;

    @Autowired
    PetModelAssembler petModelAssembler;

    @GetMapping
    public Page<PetModel> buscarTodos(Long idCliente, @PageableDefault(size = 10) Pageable pageable) {
        Page<Pet> petsPage = petService.buscarTodos(idCliente, pageable);

        return retornaPetsModelPaginados(petsPage, pageable);
    }

    private Page<PetModel> retornaPetsModelPaginados(Page<Pet> petsPage, Pageable pageable) {
        Page<PetModel> PetsModel = new PageImpl<>(petModelAssembler.toCollectionModel(petsPage.getContent()),
                                                                                      pageable,
                                                                                      petsPage.getTotalElements());

        return PetsModel;
    }

    @GetMapping("/{idPet}")
    public PetModel buscarPorId(@PathVariable Long idPet) {
        Pet pet = petService.buscarPorId(idPet);

        return petModelAssembler.toModel(pet);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PetModel cadastrarPet(@RequestBody PetInput petInput) {
        Pet pet = petInputDisassembler.toDomainObject(petInput);

        pet = petService.salvar(pet);

        return petModelAssembler.toModel(pet);
    }

    @PutMapping("/{idPet}")
    public PetModel atualizarPet(@PathVariable Long idPet, @RequestBody PetInput petInput) {
        Pet petAtual = petService.buscarPorId(idPet);

        petInputDisassembler.copyToDomainObject(petInput, petAtual);
        petAtual.setIdPet(idPet);

        petAtual = petService.salvar(petAtual);

        return petModelAssembler.toModel(petAtual);
    }

    @DeleteMapping("/{idPet}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletarPet(@PathVariable Long idPet) {
        petService.deletar(idPet);
    }
}
