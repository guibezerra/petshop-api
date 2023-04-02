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
@Table(name = "atendimento")
public class Atendimento {
    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idAtendimento;

    @ManyToOne
    @JoinColumn(name = "id_pet")
    private Pet pet;

    @Column(name = "descricao_do_atendimento")
    private String descricao;

    @Column(name = "valor")
    private float valor;

    @Column(name = "data_do_atendimento")
    private LocalDate data;
}
