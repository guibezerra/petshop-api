package com.petshopapi.api.assembler;

import com.petshopapi.api.assembler.generic.ObjectInputDisassembler;
import com.petshopapi.api.model.ContatoModel;
import com.petshopapi.api.model.input.ClienteInput;
import com.petshopapi.api.model.input.ContatoInput;
import com.petshopapi.domain.model.Cliente;
import com.petshopapi.domain.model.Contato;
import com.petshopapi.domain.model.TagContato;
import org.modelmapper.PropertyMap;
import org.modelmapper.TypeMap;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class ContatoInputDisassembler extends ObjectInputDisassembler<ContatoInput, Contato> {
    public Contato toDomainObject(ContatoInput contatoInput) {
        modelMapper.getConfiguration().setAmbiguityIgnored(true);

        TypeMap<ContatoInput, Contato> typeMap = modelMapper.getTypeMap(ContatoInput.class, Contato.class);
        if(Objects.isNull(typeMap)) {
            modelMapper.createTypeMap(ContatoInput.class, Contato.class)
                    .addMapping(src -> src.getTagContato(),(dest, value) -> dest.setTagContato((TagContato) value));
        }

        return modelMapper.map(contatoInput,Contato.class);
    }
}
