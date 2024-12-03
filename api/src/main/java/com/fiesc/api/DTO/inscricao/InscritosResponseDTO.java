package com.fiesc.api.DTO.inscricao;

import java.util.List;

public record InscritosResponseDTO(
        Integer idCurso,
        List<InscritoDTO> inscritos
) {
    public record InscritoDTO(String cpf) {
    }
}