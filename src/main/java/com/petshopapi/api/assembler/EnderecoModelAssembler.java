package com.petshopapi.api.assembler;

import com.petshopapi.api.assembler.generic.ObjectModelAssembler;
import com.petshopapi.api.model.EnderecoModel;
import com.petshopapi.domain.model.Endereco;
import org.springframework.stereotype.Component;

@Component
public class EnderecoModelAssembler extends ObjectModelAssembler<EnderecoModel, Endereco> {
}
