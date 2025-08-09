package com.gus.forum_hub_api.controller;

import com.gus.forum_hub_api.domain.usuario.*;
import com.gus.forum_hub_api.domain.usuario.dto.DtoLoginResponse;
import com.gus.forum_hub_api.domain.usuario.dto.DtoLoginUsuario;
import com.gus.forum_hub_api.domain.usuario.dto.DtoOutUsuario;
import com.gus.forum_hub_api.domain.usuario.dto.DtoRegisterUsuario;
import com.gus.forum_hub_api.infra.security.TokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UsuarioRepository repository;

    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity login(@Valid @RequestBody DtoLoginUsuario dtoLogin){
        var usernamePassword = new UsernamePasswordAuthenticationToken(dtoLogin.login(), dtoLogin.password());
        var auth = authenticationManager.authenticate(usernamePassword);

        var token = tokenService.generateToken((Usuario) auth.getPrincipal());

        return ResponseEntity.ok(new DtoLoginResponse(token));
    }

    @PostMapping("/register")
    public ResponseEntity logon(
            @Valid @RequestBody DtoRegisterUsuario dtoRegister,
            UriComponentsBuilder uriBuilder){

        if (repository.findByEmail(dtoRegister.email()) != null) return ResponseEntity.badRequest().build();

        String encryptPassword = new BCryptPasswordEncoder().encode(dtoRegister.senha());
        Usuario usuario = new Usuario(
                dtoRegister.nome(),
                dtoRegister.email(),
                encryptPassword
        );
        this.repository.save(usuario);

        var uri = uriBuilder
                .path("/auth/register/{id}")
                .buildAndExpand(usuario.getId())
                .toUri();

        return ResponseEntity
                .created(uri)
                .body(new DtoOutUsuario(usuario));
    }

}
