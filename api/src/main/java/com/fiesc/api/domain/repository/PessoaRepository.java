package com.fiesc.api.domain.repository;

import com.fiesc.api.domain.entity.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PessoaRepository extends JpaRepository<Pessoa, Integer> {
    boolean existsByCpf(String cpf);

    Optional<Pessoa> findByCpf(String cpf);
}
