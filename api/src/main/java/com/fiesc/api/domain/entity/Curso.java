package com.fiesc.api.domain.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Curso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idCurso;

    @NotBlank(message = "Nome do curso é obrigatório")
    @Size(max = 150, message = "Nome do curso deve ter no máximo 150 caracteres")
    private String nome;

    @NotNull(message = "Número de vagas é obrigatório")
    @Min(value = 0, message = "Número de vagas deve ser zero ou positivo")
    @Max(value = 100, message = "Número de vagas deve ter no máximo 100")
    private Integer numeroVagas;

    @OneToMany(mappedBy = "curso", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Inscricao> inscricoes;

}
