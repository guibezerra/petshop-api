package com.petshopapi.domain.repository;

import com.petshopapi.domain.model.Atendimento;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AtendimentoRepository extends JpaRepository<Atendimento, Long> {
    @Query(value = "select at from Atendimento at join Pet p on p.idPet = at.pet.idPet " +
            "join Cliente c on c.idCliente = p.cliente.idCliente where c.idCliente = :idCliente",
    countQuery = "select count(at.idAtendimento) from Atendimento at join Pet p on p.idPet = at.pet.idPet " +
            "join Cliente c on c.idCliente = p.cliente.idCliente where c.idCliente = :idCliente")
    Page<Atendimento> findAllByIdCliente(@Param("idCliente") Long idCliente, Pageable pageable);

    @Query("select case when (count(at.idAtendimento) > 0) then true else false end from Atendimento at join Pet p on p.idPet = at.pet.idPet " +
            "join Cliente c on c.idCliente = p.cliente.idCliente where c.idCliente = :idCliente")
    boolean existsByIdCliente(Long idCliente);
}
