package com.petshopapi.api.assembler;

import com.petshopapi.api.assembler.generic.ObjectInputDisassembler;
import com.petshopapi.api.model.input.ContatoInput;
import com.petshopapi.domain.model.Contato;
import org.springframework.stereotype.Component;

@Component
public class ContatoInputDisassembler extends ObjectInputDisassembler<ContatoInput, Contato> {
}
