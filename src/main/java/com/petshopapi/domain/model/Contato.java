package com.petshopapi.domain.model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name="contato")
public class Contato {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_contato")
    private Long idContato;


    @ManyToOne
    @JoinColumn(name = "id_cliente")
    private Cliente cliente;

    @Column(name = "tag")
    @Enumerated(EnumType.STRING)
    private TagContato tagContato;

    @Column(name = "tipo")
    @Enumerated(EnumType.STRING)
    private TipoContato tipoContato;

    @Column(name = "valor")
    private String valor;
}
