package com.fiesc.api.domain.repository;

import com.fiesc.api.domain.entity.Inscricao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface InscricaoRepository extends JpaRepository<Inscricao, Long> {

    int countByCursoIdCurso(Integer idCurso);

    boolean existsByPessoaCpfAndCursoIdCurso(String cpf, Integer integer);

}
