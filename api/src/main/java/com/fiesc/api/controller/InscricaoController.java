package com.fiesc.api.controller;

import com.fiesc.api.DTO.inscricao.InscricaoRequestDTO;
import com.fiesc.api.DTO.inscricao.InscritosResponseDTO;
import com.fiesc.api.config.exception.ServiceException;
import com.fiesc.api.domain.repository.CursoRepository;
import com.fiesc.api.domain.repository.InscricaoRepository;
import com.fiesc.api.domain.repository.PessoaRepository;
import com.fiesc.api.domain.service.InscricaoService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/inscricao")
public class InscricaoController {

    @Autowired
    private InscricaoService inscricaoService;

    @PostMapping
    public ResponseEntity<Map<String, Object>> inscrever(@Valid @RequestBody InscricaoRequestDTO inscricaoRequestDTO) throws ServiceException {
        var response = inscricaoService.inscrever(inscricaoRequestDTO);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{idCurso}")
    public ResponseEntity<InscritosResponseDTO> listarInscritos(@PathVariable @NotNull Integer idCurso) throws ServiceException {
        var response = inscricaoService.listarInscritosPorCurso(idCurso);
        return ResponseEntity.ok(response);
    }
}
