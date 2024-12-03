package com.fiesc.api.domain.service;

import com.fiesc.api.DTO.pessoa.PessoaRequestDTO;
import com.fiesc.api.DTO.pessoa.PessoaResponseDTO;
import com.fiesc.api.config.exception.ServiceException;
import com.fiesc.api.domain.entity.Pessoa;
import com.fiesc.api.domain.repository.PessoaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PessoaServiceTest {

    @Mock
    private PessoaRepository pessoaRepository;

    @InjectMocks
    private PessoaService pessoaService;

    private PessoaRequestDTO pessoaRequestDTO;

    @BeforeEach
    void setUp() {
        pessoaRequestDTO = new PessoaRequestDTO(
                "João",
                LocalDate.of(1990, 1, 1),
                "12345678901",
                "joao@gmail.com"
        );
    }

    @Test
    void testCadastrarPessoa() throws ServiceException {
        when(pessoaRepository.findByCpf(anyString())).thenReturn(Optional.empty());
        when(pessoaRepository.save(any(Pessoa.class))).thenAnswer(invocation -> {
            Pessoa pessoa = invocation.getArgument(0);
            pessoa.setIdPessoa(1);
            return pessoa;
        });

        PessoaResponseDTO response = pessoaService.cadastrarPessoa(pessoaRequestDTO);

        assertEquals(1, response.idPessoa());
        assertEquals("operação realizada com sucesso", response.mensagem());
        verify(pessoaRepository, times(1)).save(any(Pessoa.class));
    }

    @Test
    void testCadastrarPessoaThrowsExceptionWhenCpfExists() {
        when(pessoaRepository.findByCpf(anyString())).thenReturn(Optional.of(new Pessoa()));

        ServiceException exception = assertThrows(ServiceException.class, () -> {
            pessoaService.cadastrarPessoa(pessoaRequestDTO);
        });

        assertEquals("Já existe uma pessoa cadastrada com este CPF.", exception.getMessage());
        verify(pessoaRepository, never()).save(any(Pessoa.class));
    }
}
