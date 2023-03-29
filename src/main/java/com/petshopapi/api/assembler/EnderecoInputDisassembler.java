package com.petshopapi.api.assembler;

import com.petshopapi.api.assembler.generic.ObjectInputDisassembler;
import com.petshopapi.api.model.input.EnderecoInput;
import com.petshopapi.domain.model.Endereco;
import org.springframework.stereotype.Component;

@Component
public class EnderecoInputDisassembler extends ObjectInputDisassembler<EnderecoInput, Endereco> {
}
