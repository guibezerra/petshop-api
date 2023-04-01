package com.petshopapi.domain.repository;

import com.petshopapi.domain.model.Endereco;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EnderecoRepository extends JpaRepository<Endereco, Long> {
    @Query("from Endereco where cliente.idCliente = :idCliente")
    Optional<Endereco> findByIdCliente(@Param("idCliente") Long idCliente);

    @Query("select case when (count(idEndereco) > 0) then true else false end from Endereco where cliente.idCliente = :idCliente")
    boolean existsByIdCliente(@Param("idCliente") Long idCliente);
}
