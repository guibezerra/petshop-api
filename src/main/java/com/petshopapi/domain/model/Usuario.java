package com.petshopapi.domain.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;


@Getter
@Setter
@Entity
@Table(name="usuario")
@IdClass(UsuarioId.class)
public class Usuario {
    @Id
    @Column(name="cpf")
    private String cpf;

    @Id
    @Column(name="nome")
    private String nome;


    @Column(name="id_usuario")
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_USUARIO")
    @SequenceGenerator(name = "SEQ_USUARIO", sequenceName = "usuario_id_seq", allocationSize = 1)
    private Long idUsuario;

    @Column(name="perfil")
    @Enumerated(EnumType.STRING)
    private TipoPerfil tipoPerfil;

    @Column(name="senha")
    private String senha;
}
