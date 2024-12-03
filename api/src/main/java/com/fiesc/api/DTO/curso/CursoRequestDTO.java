package com.fiesc.api.DTO.curso;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record CursoRequestDTO(
        @NotBlank(message = "O nome do curso é obrigatório")
        @Size(max = 150)
        @Schema(example = "nome do curso")
        String nome,

        @NotNull(message = "O número de vagas é obrigatório")
        @Positive(message = "O número de vagas deve ser maior que zero")
        @Max(999)
        @Schema(example = "10")
        Integer numeroVagas
) {
}
