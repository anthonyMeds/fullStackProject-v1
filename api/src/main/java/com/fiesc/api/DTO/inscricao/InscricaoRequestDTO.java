package com.fiesc.api.DTO.inscricao;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record InscricaoRequestDTO(
        @NotNull(message = "O id do curso é obrigatório")
        @Schema(example = "1")
        Integer idCurso,

        @NotBlank(message = "O CPF é obrigatório")
        @Pattern(regexp = "\\d{11}", message = "O CPF deve conter 11 dígitos")
        @Schema(example = "12345678911")
        String cpf

) {
}
