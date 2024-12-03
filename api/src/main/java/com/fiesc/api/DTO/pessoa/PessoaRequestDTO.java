package com.fiesc.api.DTO.pessoa;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public record PessoaRequestDTO(
        @NotBlank(message = "O nome é obrigatório")
        @Size(max = 200, message = "Nome deve ter no máximo 200 caracteres")
        @Schema(example = "João")
        String nome,

        @NotNull
        @JsonFormat(pattern = "dd/MM/yyyy")
        @Schema(type = "string", pattern = "dd/MM/yyyy", description = "Data de nascimento", example = "11/05/2012")
        LocalDate nascimento,

        @NotBlank(message = "O CPF é obrigatório")
        @Pattern(regexp = "\\d{11}", message = "O CPF deve conter 11 dígitos")
        @Schema(example = "1234567841")
        String cpf,

        @Email(message = "E-mail inválido")
        @Schema(example = "joao@gmail.com")
        String email
) {
}
