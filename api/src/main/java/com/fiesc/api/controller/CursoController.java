package com.fiesc.api.controller;

import com.fiesc.api.DTO.curso.CursoDTO;
import com.fiesc.api.DTO.curso.CursoRequestDTO;
import com.fiesc.api.DTO.PageResultDTO;
import com.fiesc.api.DTO.curso.CursoResponseDTO;
import com.fiesc.api.domain.service.CursoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/curso")
public class CursoController {

    @Autowired
    private CursoService cursoService;

    @GetMapping
    public ResponseEntity<PageResultDTO<CursoDTO>> listarCursos(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
            @RequestParam(value = "sort", defaultValue = "nome") String sort,
            @RequestParam(value = "direction", defaultValue = "asc") String direction
    ) {
        var pageable = PageRequest.of(page, pageSize, Sort.Direction.fromString(direction), sort);
        var result = cursoService.listarCursos(pageable);
        return ResponseEntity.ok(result);
    }

    @PostMapping
    public ResponseEntity<CursoResponseDTO> cadastrarCurso(@Valid @RequestBody CursoRequestDTO cursoDTO) {
        var response = cursoService.cadastrarCurso(cursoDTO);
        return ResponseEntity.ok(response);
    }

}
