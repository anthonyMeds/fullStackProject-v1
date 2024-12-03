package com.fiesc.backend.domain.repository;

import com.fiesc.backend.domain.entity.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

public interface PessoaRepository extends JpaRepository<Pessoa, Integer> {

    boolean existsByCpf(String cpf);
    Optional<Pessoa> findByCpf(String cpf);

    Optional<Pessoa> findByLogin(String login);
}
