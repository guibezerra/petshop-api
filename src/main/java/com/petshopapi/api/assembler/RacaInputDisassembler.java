package com.petshopapi.api.assembler;

import com.petshopapi.api.assembler.generic.ObjectInputDisassembler;
import com.petshopapi.api.model.input.RacaInput;
import com.petshopapi.domain.model.Raca;
import org.springframework.stereotype.Component;

@Component
public class RacaInputDisassembler extends ObjectInputDisassembler<RacaInput, Raca> {
}
