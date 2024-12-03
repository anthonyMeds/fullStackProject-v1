package com.fiesc.api.domain.service;

import com.fiesc.api.DTO.PageResultDTO;
import com.fiesc.api.DTO.curso.CursoDTO;
import com.fiesc.api.DTO.curso.CursoRequestDTO;
import com.fiesc.api.DTO.curso.CursoResponseDTO;
import com.fiesc.api.domain.entity.Curso;
import com.fiesc.api.domain.repository.CursoRepository;
import com.fiesc.api.domain.repository.InscricaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CursoService {

    @Autowired
    private CursoRepository cursoRepository;

    @Autowired
    private InscricaoRepository inscricaoRepository;

    public PageResultDTO<CursoDTO> listarCursos(Pageable pageable) {

        Page<Curso> pageCursos = cursoRepository.findAll(pageable);
       
        Page<CursoDTO> pageCursoDTOs = pageCursos.map(curso -> {

            Integer numeroInscricoes = inscricaoRepository.countByCursoIdCurso(curso.getIdCurso());

            return CursoDTO.fromEntity(curso, numeroInscricoes);
        });


        return new PageResultDTO<>(
                pageCursoDTOs.getContent(),
                pageCursoDTOs.getNumber(),
                pageCursoDTOs.getSize(),
                pageCursoDTOs.getTotalPages(),
                pageCursoDTOs.getTotalElements(),
                "operação realizada com sucesso"
        );
    }


    public CursoResponseDTO cadastrarCurso(CursoRequestDTO cursoRequestDTO) {
        Curso curso = new Curso();
        curso.setNome(cursoRequestDTO.nome());
        curso.setNumeroVagas(cursoRequestDTO.numeroVagas());
        cursoRepository.save(curso);

        return new CursoResponseDTO(curso.getIdCurso(), "operação realizada com sucesso");
    }
}
