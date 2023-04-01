package com.petshopapi.api.assembler;

import com.petshopapi.api.assembler.generic.ObjectInputDisassembler;
import com.petshopapi.api.model.input.PetInput;
import com.petshopapi.domain.model.Pet;
import org.springframework.stereotype.Component;

@Component
public class PetInputDisassembler extends ObjectInputDisassembler<PetInput, Pet> {

}
