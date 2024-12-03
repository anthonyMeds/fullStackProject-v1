package com.fiesc.backend.domain.service;

import com.fiesc.backend.DTO.pessoa.PessoaRequestDTO;
import com.fiesc.backend.DTO.pessoa.PessoaResponseDTO;
import com.fiesc.backend.config.exception.ServiceException;
import com.fiesc.backend.domain.entity.Pessoa;
import com.fiesc.backend.domain.repository.PessoaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class PessoaService {

    @Autowired
    private PessoaRepository pessoaRepository;


    public PessoaResponseDTO cadastrarPessoa(PessoaRequestDTO pessoaRequestDTO) throws ServiceException {

        if (pessoaRepository.findByLogin(pessoaRequestDTO.cpf()).isPresent()) {
            throw new ServiceException("Usuário já cadastrado");
        }

        String senhaCriptografada = new BCryptPasswordEncoder().encode(pessoaRequestDTO.senha());

        Pessoa pessoa = new Pessoa();
        pessoa.setNome(pessoaRequestDTO.nome());
        pessoa.setCpf(pessoaRequestDTO.cpf());
        pessoa.setEmail(pessoaRequestDTO.email());
        pessoa.setNascimento(pessoaRequestDTO.nascimento());
        pessoa.setSenha(senhaCriptografada);
        pessoa.setRole(pessoaRequestDTO.role());
        pessoa.setLogin(pessoaRequestDTO.cpf());

        pessoaRepository.save(pessoa);

        return new PessoaResponseDTO(pessoa.getIdPessoa(), "Cadastro realizado com sucesso");
    }

}
