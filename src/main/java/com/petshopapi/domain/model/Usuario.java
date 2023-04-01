package com.petshopapi.domain.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;


@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Getter
@Setter
@Entity
@Table(name="usuario")
public class Usuario {
    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUsuario;

    @Column(name="cpf")
    private String cpf;

    @Column(name="nome")
    private String nome;

    @Column(name="perfil")
    @Enumerated(EnumType.STRING)
    private TipoPerfil tipoPerfil;

    @Column(name="senha")
    private String senha;

    @Transient
    @OneToOne(mappedBy = "usuario")
    private Cliente cliente;
}
