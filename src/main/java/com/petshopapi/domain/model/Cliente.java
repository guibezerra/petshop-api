package com.petshopapi.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "cliente")
@AllArgsConstructor
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cliente")
    private Long idCliente;

    @Column(name = "nome")
    private String nome;

    @Column(name = "cpf")
    private String cpf;

    @Column(name = "data_de_cadastro")
    private LocalDate dataDeCadastro;

    @OneToOne(mappedBy = "cliente")
    private Endereco endereco;

    @OneToMany(mappedBy = "cliente")
    private List<Contato> contatos;

    public Cliente(String nome, String cpf, LocalDate dataDeCadastro) {
        this.nome = nome;
        this.cpf = cpf;
        this.dataDeCadastro = dataDeCadastro;
    }

    public Cliente() {

    }
}