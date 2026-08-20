package com.rayan.usuario.infrastructure.repository;


import com.rayan.usuario.infrastructure.entify.Telefone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TelefoneRepository extends JpaRepository<Telefone, Long> {
}
