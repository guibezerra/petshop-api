package com.petshopapi.api.assembler;

import com.petshopapi.api.assembler.generic.ObjectInputDisassembler;
import com.petshopapi.api.model.input.AtendimentoInput;
import com.petshopapi.domain.model.Atendimento;
import org.springframework.stereotype.Component;

@Component
public class AtendimentoInputDisassembler extends ObjectInputDisassembler<AtendimentoInput, Atendimento> {
}
