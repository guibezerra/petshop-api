package com.petshopapi.domain.repository;

import com.petshopapi.domain.model.Pet;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PetRepository extends JpaRepository<Pet, Long> {
    @Query("select case when (count(idPet) > 0) then true else false end from Pet " +
            "where cliente.idCliente = :idCliente")
    boolean existsByIdCliente(@Param("idCliente")Long idCliente);

    @Query("from Pet where cliente.idCliente = :idCliente")
    List<Pet> findByIdCliente(@Param("idCliente")Long idCliente);

    @Query(value = "select p from Pet p where p.cliente.idCliente = :idCliente",
    countQuery = "select count (p.idPet) from Pet p where p.cliente.idCliente = :idCliente")
    Page<Pet> findAllByCliente(@Param("idCliente") Long idCliente, Pageable pageable);
}
