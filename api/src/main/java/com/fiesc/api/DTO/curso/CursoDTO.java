package com.fiesc.api.DTO.curso;

import com.fiesc.api.domain.entity.Curso;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record CursoDTO(
        Integer idCurso,
        @NotBlank(message = "O nome do curso é obrigatório")
        String nome,
        @NotNull(message = "O número de vagas é obrigatório")
        @Positive(message = "O número de vagas deve ser maior que zero")
        Integer numeroVagas,
        Integer numeroInscricoes
) {
        public static CursoDTO fromEntity(Curso curso, Integer numeroInscricoes) {
                return new CursoDTO(curso.getIdCurso(), curso.getNome(), curso.getNumeroVagas(), numeroInscricoes);
        }

        public static Curso toEntity(CursoDTO dto) {
                var curso = new Curso();
                curso.setIdCurso(dto.idCurso());
                curso.setNome(dto.nome());
                curso.setNumeroVagas(dto.numeroVagas());
                return curso;
        }
}
