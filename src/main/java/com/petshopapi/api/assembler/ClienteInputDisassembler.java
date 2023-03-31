package com.petshopapi.api.assembler;


import com.petshopapi.api.assembler.generic.ObjectInputDisassembler;
import com.petshopapi.api.model.input.ClienteInput;
import com.petshopapi.domain.model.Cliente;
import org.modelmapper.PropertyMap;
import org.modelmapper.TypeMap;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class ClienteInputDisassembler extends ObjectInputDisassembler<ClienteInput, Cliente> {
    public Cliente toDomainObject(ClienteInput clienteInput) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);

        TypeMap<ClienteInput, Cliente> typeMap = modelMapper.getTypeMap(ClienteInput.class, Cliente.class);
        if(Objects.isNull(typeMap)) {
            modelMapper.addMappings(new PropertyMap<ClienteInput, Cliente>() {
                @Override
                protected void configure() {
                    map().getEndereco().setIdEndereco(null);
                    map().getEndereco().setCliente(null);
                    map().getEndereco().setLogradouro(clienteInput.getEndereco().getLogradouro());
                    map().getEndereco().setCidade(clienteInput.getEndereco().getCidade());
                    map().getEndereco().setBairro(clienteInput.getEndereco().getBairro());
                    map().getEndereco().setComplemento(clienteInput.getEndereco().getComplemento());;
                    map().getEndereco().setTag(clienteInput.getEndereco().getTag());
                }
            });
        }

        return modelMapper.map(clienteInput, Cliente.class);
    }
}
