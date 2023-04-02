package com.petshopapi.api.assembler;

import com.petshopapi.api.assembler.generic.ObjectModelAssembler;
import com.petshopapi.api.model.AtendimentoModel;
import com.petshopapi.domain.model.Atendimento;
import org.springframework.stereotype.Component;

@Component
public class AtendimentoModelAssembler extends ObjectModelAssembler<AtendimentoModel, Atendimento> {
}
