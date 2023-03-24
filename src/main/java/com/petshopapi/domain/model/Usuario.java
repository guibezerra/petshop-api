package com.petshopapi.domain.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name="usuario")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_usuario")
    private Long idUsuario;

    @Column(name="cpf")
    private String cpf;

    @Column(name="nome")
    private String nome;

//    @Column(name="tipo_usuario")
//    private TipoUsuario tipoUsuario;

    @Column(name="senha")
    private String senha;

}
