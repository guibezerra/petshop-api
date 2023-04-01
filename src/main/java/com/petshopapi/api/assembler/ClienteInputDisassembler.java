package com.petshopapi.api.assembler;


import com.petshopapi.api.assembler.generic.ObjectInputDisassembler;
import com.petshopapi.api.model.input.ClienteInput;
import com.petshopapi.api.model.input.ContatoInput;
import com.petshopapi.domain.model.Cliente;
import com.petshopapi.domain.model.Contato;
import com.petshopapi.domain.model.Usuario;
import org.modelmapper.Converter;
import org.modelmapper.PropertyMap;
import org.modelmapper.TypeMap;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class ClienteInputDisassembler extends ObjectInputDisassembler<ClienteInput, Cliente> {
    public Cliente toDomainObjectSkipingProperties(ClienteInput clienteInput) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        modelMapper.getConfiguration().setAmbiguityIgnored(true);

        TypeMap<ClienteInput, Cliente> typeMap = modelMapper.getTypeMap(ClienteInput.class, Cliente.class);
        if(Objects.isNull(typeMap)) {
            modelMapper.addMappings(new PropertyMap<ClienteInput, Cliente>() {
                @Override
                protected void configure() {
                    map().getEndereco().setIdEndereco(null);
                    map().getEndereco().setCliente(null);
                }
            });
        }

        return modelMapper.map(clienteInput, Cliente.class);
    }

    public void copyToDomainObjectSkippingProperties(ClienteInput clienteInput, Cliente cliente) {
        TypeMap<ClienteInput, Cliente> typeMap = modelMapper.getTypeMap(ClienteInput.class, Cliente.class);
        if(Objects.isNull(typeMap)) {
            modelMapper.createTypeMap(ClienteInput.class, Cliente.class)
                    .addMapping(src -> src.getEndereco().getLogradouro(),(dest, value) -> dest.getEndereco().setLogradouro((String) value));

        }

    }
}
