package com.petshopapi.domain.repository;

import com.petshopapi.domain.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    @Query("from Cliente where cpf = :cpf")
    Optional<Cliente> findByCpf(@Param("cpf") String cpf);
}
