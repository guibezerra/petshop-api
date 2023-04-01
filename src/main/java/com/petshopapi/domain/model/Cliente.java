package com.petshopapi.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

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

    @OneToOne
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;

    @Column(name = "data_de_cadastro")
    private LocalDate dataDeCadastro;

    @Transient
    @OneToOne(mappedBy = "cliente")
    private Endereco endereco;

    @Transient
    @OneToMany(mappedBy = "cliente")
    private List<Contato> contatos;

    public Cliente() {

    }

    public Cliente(Usuario usuario, LocalDate dataDeCadastro) {
        this.dataDeCadastro = dataDeCadastro;
        this.usuario = usuario;
    }
}
