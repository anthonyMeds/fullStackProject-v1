package com.fiesc.backend.DTO.pessoa;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fiesc.backend.domain.entity.UserRole;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.time.LocalDate;

public record PessoaRequestDTO(
        @NotBlank(message = "O nome é obrigatório")
        @Pattern(regexp = "^[A-Z][a-z]+(?: [A-Z][a-z]+)+$",
                message = "O nome deve ter pelo menos dois nomes, iniciando com letra maiúscula")
        @Schema(example = "joao teste")
        String nome,

        @NotNull(message = "O CPF é obrigatório")
        @Pattern(regexp = "\\d{11}", message = "O CPF deve conter 11 dígitos")
        String cpf,

        @Email(message = "E-mail inválido")
        @Schema(example = "joao@gmail.com")
        String email,

        @NotBlank(message = "A senha é obrigatória")
        @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%!&*])[A-Za-z\\d@#$%!&*]{8,}$",
                message = "A senha deve ter no mínimo 8 caracteres, com letras maiúsculas, minúsculas, números e caracteres especiais")
        @Schema(example = "Passw0rd#")
        String senha,

        @NotNull
        @JsonFormat(pattern = "dd/MM/yyyy")
        @Schema(type = "string", pattern = "dd/MM/yyyy", description = "Data de nascimento", example = "11/05/2012")
        LocalDate nascimento,

        UserRole role

) {
}
