package com.petshopapi.api.assembler;


import com.petshopapi.api.assembler.generic.ObjectInputDisassembler;
import com.petshopapi.api.model.input.ClienteInput;
import com.petshopapi.domain.model.Cliente;
import org.springframework.stereotype.Component;

@Component
public class ClienteInputDisassembler extends ObjectInputDisassembler<ClienteInput, Cliente> {
}
