package com.petshopapi.api.assembler;

import com.petshopapi.api.assembler.generic.ObjectModelAssembler;
import com.petshopapi.api.model.ClienteModel;
import com.petshopapi.api.model.input.ClienteInput;
import com.petshopapi.domain.model.Cliente;
import com.petshopapi.domain.model.Contato;
import com.petshopapi.domain.model.Usuario;
import org.modelmapper.PropertyMap;
import org.modelmapper.TypeMap;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@Component
public class ClienteModelAssembler extends ObjectModelAssembler<ClienteModel, Cliente> {
    public ClienteModel toModelCustomaized(Cliente cliente, Usuario usuario) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        modelMapper.getConfiguration().setAmbiguityIgnored(true);

        TypeMap<ClienteModel, Cliente> typeMap = modelMapper.getTypeMap(ClienteModel.class, Cliente.class);
        if(Objects.isNull(typeMap)) {
            modelMapper.addMappings(new PropertyMap<ClienteInput, Cliente>() {
                @Override
                protected void configure() {
//                    map().getUsuario().setNome(usuario.getNome());
                }
            });
        }

        return modelMapper.map(cliente, ClienteModel.class);
    }
}

