package com.petshopapi.api.assembler;

import com.petshopapi.api.assembler.generic.ObjectInputDisassembler;
import com.petshopapi.api.model.input.ClienteInput;
import com.petshopapi.api.model.input.EnderecoInput;
import com.petshopapi.domain.model.Cliente;
import com.petshopapi.domain.model.Endereco;
import org.modelmapper.PropertyMap;
import org.modelmapper.TypeMap;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class EnderecoInputDisassembler extends ObjectInputDisassembler<EnderecoInput, Endereco> {
    public Endereco toDomainObject(EnderecoInput enderecoInput) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);

        TypeMap<EnderecoInput, Endereco> typeMap = modelMapper.getTypeMap(EnderecoInput.class, Endereco.class);
        if(Objects.isNull(typeMap)) {
            modelMapper.addMappings(new PropertyMap<ClienteInput, Cliente>() {
                @Override
                protected void configure() {
                    map().getEndereco().setIdEndereco(null);
                    map().getEndereco().setCliente(null);
                    map().getEndereco().setLogradouro(enderecoInput.getLogradouro());
                    map().getEndereco().setCidade(enderecoInput.getCidade());
                    map().getEndereco().setBairro(enderecoInput.getBairro());
                    map().getEndereco().setComplemento(enderecoInput.getComplemento());;
                    map().getEndereco().setTag(enderecoInput.getTag());
                }
            });
        }

        return modelMapper.map(enderecoInput, Endereco.class);
    }
}
