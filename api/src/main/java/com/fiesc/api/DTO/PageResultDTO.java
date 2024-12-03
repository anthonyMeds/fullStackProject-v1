package com.fiesc.api.DTO;

import java.util.List;

public record PageResultDTO<T>(
        List<T> dados,
        int page,
        int pageSize,
        int totalPages,
        long totalElements,
        String mensagem
) {
}
