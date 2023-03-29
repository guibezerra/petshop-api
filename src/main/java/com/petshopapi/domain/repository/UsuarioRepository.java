package com.petshopapi.domain.repository;

import com.petshopapi.domain.model.TipoPerfil;
import com.petshopapi.domain.model.Usuario;
import com.petshopapi.domain.model.UsuarioId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, UsuarioId> {
    @Query("from Usuario where cpf = :cpf")
    Optional<Usuario> findByCpf(@Param("cpf") String cpf);

    @Query("from Usuario where idUsuario= :idUsuario")
    Optional<Usuario> findByIdUsuario(@Param("idUsuario") Long idUsuario);

    @Query("select case when (count(idUsuario) > 0) then true else false end from Usuario " +
            "where cpf = :cpf and tipoPerfil = :tipoPerfil")
    boolean existsByCpfETipo(@Param("cpf") String cpf, @Param("tipoPerfil") TipoPerfil tipoPerfil);

    @Modifying
    @Query("delete from Usuario where cpf = :cpf")
    void deleteByCpf(@Param("cpf") String cpf);

    @Modifying
    @Query("update Usuario u set u.cpf = :cpf, u.nome = :nome, u.senha = :senha where u.idUsuario = :idUsuario ")
    void updateUsuario(@Param("cpf") String cpf, @Param("nome") String nome,
                          @Param("senha") String senha, @Param("idUsuario") Long idUsuario);
}
