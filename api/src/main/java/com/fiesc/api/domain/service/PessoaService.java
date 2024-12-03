package com.fiesc.api.domain.service;

import com.fiesc.api.DTO.pessoa.PessoaRequestDTO;
import com.fiesc.api.DTO.pessoa.PessoaResponseDTO;
import com.fiesc.api.config.exception.ServiceException;
import com.fiesc.api.domain.entity.Pessoa;
import com.fiesc.api.domain.repository.PessoaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PessoaService {

    @Autowired
    private PessoaRepository pessoaRepository;

    public PessoaResponseDTO cadastrarPessoa(PessoaRequestDTO pessoaRequestDTO) throws ServiceException {

        validaPessoaExistente(pessoaRequestDTO.cpf());

        Pessoa pessoa = new Pessoa();
        pessoa.setNome(pessoaRequestDTO.nome());
        pessoa.setNascimento(pessoaRequestDTO.nascimento());
        pessoa.setCpf(pessoaRequestDTO.cpf());
        pessoa.setEmail(pessoaRequestDTO.email());

        pessoaRepository.save(pessoa);

        return new PessoaResponseDTO(pessoa.getIdPessoa(), "operação realizada com sucesso");
    }

    public void validaPessoaExistente(String cpf) throws ServiceException {
        Optional<Pessoa> pessoaExistente = pessoaRepository.findByCpf(cpf);

        if (pessoaExistente.isPresent()) {
            throw new ServiceException("Já existe uma pessoa cadastrada com este CPF.");
        }
    }

}
