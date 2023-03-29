package com.petshopapi.api.assembler;

import com.petshopapi.api.assembler.generic.ObjectModelAssembler;
import com.petshopapi.api.model.ClienteModel;
import com.petshopapi.domain.model.Cliente;
import org.springframework.stereotype.Component;

@Component
public class ClienteModelAssembler extends ObjectModelAssembler<ClienteModel, Cliente> {
}
