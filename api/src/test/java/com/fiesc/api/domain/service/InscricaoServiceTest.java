package com.fiesc.api.domain.service;

import com.fiesc.api.DTO.inscricao.InscricaoRequestDTO;
import com.fiesc.api.DTO.inscricao.InscritosResponseDTO;
import com.fiesc.api.config.exception.ServiceException;
import com.fiesc.api.domain.entity.Curso;
import com.fiesc.api.domain.entity.Inscricao;
import com.fiesc.api.domain.entity.Pessoa;
import com.fiesc.api.domain.repository.CursoRepository;
import com.fiesc.api.domain.repository.InscricaoRepository;
import com.fiesc.api.domain.repository.PessoaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class InscricaoServiceTest {

    @Mock
    private CursoRepository cursoRepository;

    @Mock
    private InscricaoRepository inscricaoRepository;

    @Mock
    private PessoaRepository pessoaRepository;

    @InjectMocks
    private InscricaoService inscricaoService;

    private InscricaoRequestDTO inscricaoRequestDTO;
    private Curso curso;
    private Pessoa pessoa;

    @BeforeEach
    void setUp() {
        inscricaoRequestDTO = new InscricaoRequestDTO(1, "12345678901");
        curso = new Curso();
        curso.setIdCurso(1);
        pessoa = new Pessoa();
        pessoa.setCpf("12345678901");
    }

    @Test
    void testInscrever() throws ServiceException {
        when(cursoRepository.findById(anyInt())).thenReturn(Optional.of(curso));
        when(pessoaRepository.findByCpf(anyString())).thenReturn(Optional.of(pessoa));
        when(inscricaoRepository.existsByPessoaCpfAndCursoIdCurso(anyString(), anyInt())).thenReturn(false);

        var response = inscricaoService.inscrever(inscricaoRequestDTO);

        assertEquals("Inscrição realizada com sucesso", response.get("mensagem"));
        verify(inscricaoRepository, times(1)).save(any(Inscricao.class));
    }

    @Test
    void testInscreverThrowsExceptionWhenCursoNotFound() {
        when(cursoRepository.findById(anyInt())).thenReturn(Optional.empty());

        ServiceException exception = assertThrows(ServiceException.class, () -> {
            inscricaoService.inscrever(inscricaoRequestDTO);
        });

        assertEquals("Curso não encontrado", exception.getMessage());
        verify(inscricaoRepository, never()).save(any(Inscricao.class));
    }

    @Test
    void testInscreverThrowsExceptionWhenPessoaNotFound() {
        when(cursoRepository.findById(anyInt())).thenReturn(Optional.of(curso));
        when(pessoaRepository.findByCpf(anyString())).thenReturn(Optional.empty());

        ServiceException exception = assertThrows(ServiceException.class, () -> {
            inscricaoService.inscrever(inscricaoRequestDTO);
        });

        assertEquals("Pessoa não encontrada", exception.getMessage());
        verify(inscricaoRepository, never()).save(any(Inscricao.class));
    }

    @Test
    void testInscreverThrowsExceptionWhenPessoaAlreadyInscribed() {
        when(cursoRepository.findById(anyInt())).thenReturn(Optional.of(curso));
        when(pessoaRepository.findByCpf(anyString())).thenReturn(Optional.of(pessoa));
        when(inscricaoRepository.existsByPessoaCpfAndCursoIdCurso(anyString(), anyInt())).thenReturn(true);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            inscricaoService.inscrever(inscricaoRequestDTO);
        });

        assertEquals("Pessoa já inscrita nesse curso", exception.getMessage());
        verify(inscricaoRepository, never()).save(any(Inscricao.class));
    }

    @Test
    void testListarInscritosPorCurso() throws ServiceException {
        Inscricao inscricao = new Inscricao();
        inscricao.setPessoa(pessoa);
        curso.setInscricoes(List.of(inscricao));

        when(cursoRepository.findById(anyInt())).thenReturn(Optional.of(curso));

        InscritosResponseDTO response = inscricaoService.listarInscritosPorCurso(1);

        assertEquals(1, response.idCurso());
        assertEquals(1, response.inscritos().size());
        assertEquals("12345678901", response.inscritos().get(0).cpf());
    }
}
