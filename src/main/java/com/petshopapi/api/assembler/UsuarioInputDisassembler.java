package com.petshopapi.api.assembler;

import com.petshopapi.api.assembler.generic.ObjectInputDisassembler;
import com.petshopapi.api.model.input.UsuarioInput;
import com.petshopapi.domain.model.Usuario;
import org.modelmapper.PropertyMap;
import org.modelmapper.TypeMap;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class UsuarioInputDisassembler extends ObjectInputDisassembler<UsuarioInput, Usuario> {
    public Usuario toDomainObjectSkippingProperties(UsuarioInput usuarioInput) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);

        TypeMap<UsuarioInput, Usuario> typeMap = modelMapper.getTypeMap(UsuarioInput.class, Usuario.class);
        if(Objects.isNull(typeMap)) {
            modelMapper.addMappings(new PropertyMap<UsuarioInput, Usuario>() {
                @Override
                protected void configure() {
                    map().setIdUsuario(null);
                }
            });
        }

        return modelMapper.map(usuarioInput, Usuario.class);
    }

    public void copyToDomainObjectSkippingProperties(UsuarioInput usuarioInput, Usuario usuario) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);

        TypeMap<UsuarioInput, Usuario> typeMap = modelMapper.getTypeMap(UsuarioInput.class, Usuario.class);
        if(Objects.isNull(typeMap)) {
            modelMapper.addMappings(new PropertyMap<UsuarioInput, Usuario>() {
                @Override
                protected void configure() {
                    map().setIdUsuario(null);
                }
            });
        }

        modelMapper.map(usuarioInput, usuario);
    }
}
