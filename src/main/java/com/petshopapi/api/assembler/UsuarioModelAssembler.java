package com.petshopapi.api.assembler;

import com.petshopapi.api.assembler.generic.ObjectModelAssembler;
import com.petshopapi.api.model.UsuarioModel;
import com.petshopapi.domain.model.Usuario;
import org.springframework.stereotype.Component;

@Component
public class UsuarioModelAssembler extends ObjectModelAssembler<UsuarioModel, Usuario> {
}
