package com.fiesc.api.domain.service;

import com.fiesc.api.DTO.PageResultDTO;
import com.fiesc.api.DTO.curso.CursoDTO;
import com.fiesc.api.DTO.curso.CursoRequestDTO;
import com.fiesc.api.DTO.curso.CursoResponseDTO;
import com.fiesc.api.domain.entity.Curso;
import com.fiesc.api.domain.repository.CursoRepository;
import com.fiesc.api.domain.repository.InscricaoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CursoServiceTest {

    @InjectMocks
    private CursoService cursoService;

    @Mock
    private CursoRepository cursoRepository;

    @Mock
    private InscricaoRepository inscricaoRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void listarCursos_deveRetornarCursosPaginados() {

        Pageable pageable = PageRequest.of(0, 10);
        Curso curso1 = new Curso(1, "Curso 1", 20 , null);
        Curso curso2 = new Curso(2, "Curso 2", 15, null);
        List<Curso> cursos = List.of(curso1, curso2);

        when(cursoRepository.findAll(pageable)).thenReturn(new PageImpl<>(cursos));
        when(inscricaoRepository.countByCursoIdCurso(1)).thenReturn(5);
        when(inscricaoRepository.countByCursoIdCurso(2)).thenReturn(3);

        PageResultDTO<CursoDTO> result = cursoService.listarCursos(pageable);

        assertNotNull(result);
        assertEquals(2, result.dados().size());
        assertEquals("Curso 1", result.dados().get(0).nome());
        assertEquals(5, result.dados().get(0).numeroInscricoes());
        assertEquals("Curso 2", result.dados().get(1).nome());
        assertEquals(3, result.dados().get(1).numeroInscricoes());

        verify(cursoRepository, times(1)).findAll(pageable);
        verify(inscricaoRepository, times(2)).countByCursoIdCurso(anyInt());
    }

    @Test
    void cadastrarCurso_deveSalvarCursoERetornarResposta() {

        CursoRequestDTO cursoRequestDTO = new CursoRequestDTO("Curso de Java", 30);
        Curso curso = new Curso();
        curso.setIdCurso(1);
        curso.setNome("Curso de Java");
        curso.setNumeroVagas(30);

        when(cursoRepository.save(Mockito.any(Curso.class))).thenReturn(curso);

        CursoResponseDTO response = cursoService.cadastrarCurso(cursoRequestDTO);

        assertNotNull(response);
        assertEquals("operação realizada com sucesso", response.mensagem());

    }



}
