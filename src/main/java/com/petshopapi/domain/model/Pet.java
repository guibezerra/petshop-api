package com.petshopapi.domain.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "pet")
public class Pet {
    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pet")
    private Long idPet;

    @ManyToOne
    @JoinColumn(name = "id_cliente")
    private Cliente cliente;

    @OneToOne
    @JoinColumn(name = "id_raca")
    private Raca raca;

    @Column(name="data_de_nascimento")
    private LocalDate dataDeNascimento;

    @Column(name = "nome")
    private String nome;
}
