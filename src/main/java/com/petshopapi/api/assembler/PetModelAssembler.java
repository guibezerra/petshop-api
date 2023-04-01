package com.petshopapi.api.assembler;

import com.petshopapi.api.assembler.generic.ObjectModelAssembler;
import com.petshopapi.api.model.PetModel;
import com.petshopapi.domain.model.Pet;
import org.springframework.stereotype.Component;

@Component
public class PetModelAssembler extends ObjectModelAssembler<PetModel, Pet> {
}
