package com.fiesc.backend.controller;

import com.fiesc.backend.DTO.authentication.AuthenticationRequestDTO;
import com.fiesc.backend.DTO.authentication.AuthenticationResponseDTO;
import com.fiesc.backend.config.exception.ServiceException;
import com.fiesc.backend.config.security.TokenService;
import com.fiesc.backend.domain.entity.Pessoa;
import com.fiesc.backend.domain.repository.PessoaRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponseDTO> login(@RequestBody @Valid AuthenticationRequestDTO dados) throws ServiceException {
        var usernamePassword = new UsernamePasswordAuthenticationToken(dados.cpf(), dados.senha());
        var auth = this.authenticationManager.authenticate(usernamePassword);

        var pessoa = (Pessoa) auth.getPrincipal();
        var token = tokenService.generateToken(pessoa);

        return ResponseEntity.ok(new AuthenticationResponseDTO(token));
    }

}
