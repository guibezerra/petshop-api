package com.petshopapi.domain.repository;

import com.petshopapi.domain.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario,Long> {
    @Query("from Usuario u where u.cpf = :cpf")
    Optional<Usuario> findByCpf(@Param("cpf") String cpf);

    @Query("from Usuario where idUsuario= :idUsuario")
    Optional<Usuario> findByIdUsuario(@Param("idUsuario") Long idUsuario);

    @Query("select case when (count(idUsuario) > 0) then true else false end from Usuario " +
            "where cpf = :cpf")
    boolean existsByCpfETipo(@Param("cpf") String cpf);
}
