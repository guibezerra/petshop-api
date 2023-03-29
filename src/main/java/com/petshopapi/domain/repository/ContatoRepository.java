package com.petshopapi.domain.repository;

import com.petshopapi.domain.model.Contato;
import com.petshopapi.domain.model.Endereco;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ContatoRepository extends JpaRepository<Contato, Long> {
    @Query("from Contato where cliente.idCliente = :idCliente")
    List<Contato> findByIdCliente(@Param("idCliente") Long idCliente);

    @Query("select case when (count(idContato) > 0)  then true else false end  from Contato " +
            "where cliente.idCliente = :idCliente")
    boolean existsByIdCliente(@Param("idCliente") Long idCliente);
}
