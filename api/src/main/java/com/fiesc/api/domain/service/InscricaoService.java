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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.stream.Collectors;

@Service
public class InscricaoService {

    @Autowired
    private CursoRepository cursoRepository;

    @Autowired
    private InscricaoRepository inscricaoRepository;

    @Autowired
    private PessoaRepository pessoaRepository;


    public Map<String, Object> inscrever(InscricaoRequestDTO inscricaoDTO) throws ServiceException {
        Curso curso = cursoRepository.findById(inscricaoDTO.idCurso())
                .orElseThrow(() -> new ServiceException("Curso não encontrado"));

        Pessoa pessoa = pessoaRepository.findByCpf(inscricaoDTO.cpf())
                .orElseThrow(() -> new ServiceException("Pessoa não encontrada"));

        if (inscricaoRepository.existsByPessoaCpfAndCursoIdCurso(inscricaoDTO.cpf(), inscricaoDTO.idCurso())) {
            throw new IllegalArgumentException("Pessoa já inscrita nesse curso");
        }

        Inscricao inscricao = new Inscricao();
        inscricao.setCurso(curso);
        inscricao.setPessoa(pessoa);
        inscricaoRepository.save(inscricao);

        return Map.of("mensagem", "Inscrição realizada com sucesso");
    }

    public InscritosResponseDTO listarInscritosPorCurso(Integer idCurso) throws ServiceException {
        Curso curso = cursoRepository.findById(idCurso)
                .orElseThrow(() -> new ServiceException("Curso não encontrado"));

        var inscritos = curso.getInscricoes().stream()
                .map(inscricao -> new InscritosResponseDTO.InscritoDTO(inscricao.getPessoa().getCpf()))
                .collect(Collectors.toList());

        return new InscritosResponseDTO(curso.getIdCurso(), inscritos);
    }
}
