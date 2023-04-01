package com.petshopapi.api.assembler;

import com.petshopapi.api.assembler.generic.ObjectModelAssembler;
import com.petshopapi.api.model.ContatoModel;
import com.petshopapi.domain.model.Contato;
import org.springframework.stereotype.Component;

@Component
public class ContatoModelAssembler extends ObjectModelAssembler<ContatoModel, Contato> {
}
