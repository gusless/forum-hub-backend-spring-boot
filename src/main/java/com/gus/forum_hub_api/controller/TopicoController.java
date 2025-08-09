package com.gus.forum_hub_api.controller;

import com.gus.forum_hub_api.domain.curso.CursoRepository;
import com.gus.forum_hub_api.domain.resposta.RespostaRepository;
import com.gus.forum_hub_api.domain.topico.*;
import com.gus.forum_hub_api.domain.topico.dto.DtoListenerTopicos;
import com.gus.forum_hub_api.domain.topico.dto.DtoOutTopico;
import com.gus.forum_hub_api.domain.topico.dto.DtoPostTopico;
import com.gus.forum_hub_api.domain.topico.dto.DtoUpdateTopico;
import com.gus.forum_hub_api.domain.usuario.Role;
import com.gus.forum_hub_api.domain.usuario.Usuario;
import com.gus.forum_hub_api.domain.usuario.UsuarioRepository;
import com.gus.forum_hub_api.infra.security.SecurityUtils;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/topicos")
public class TopicoController {

    @Autowired
    private TopicoRepository topicoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private CursoRepository cursoRepository;

    @Autowired
    private RespostaRepository respostaRepository;

    @PostMapping
    @Transactional
    public ResponseEntity postTopico(@RequestBody @Valid DtoPostTopico data){
        String emailUsuario = SecurityUtils.getEmailUsuarioLogado();
        var usuario = (Usuario) usuarioRepository.findByEmail(emailUsuario);
        var curso = cursoRepository.findById(data.idCurso())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Curso não encontrado"));

        var topico = new Topico(data, usuario, curso);

        topicoRepository.save(topico);
        return ResponseEntity.ok(new DtoOutTopico(topico));
    }

    @GetMapping
    public ResponseEntity<Page<DtoListenerTopicos>> getAllTopicos(
            @PageableDefault(size = 10, sort = {"dataCriacao"}) Pageable pageable){

        var page = topicoRepository.findAll(pageable).map(DtoListenerTopicos::new);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/{id}")
    public ResponseEntity getTopico(@PathVariable Long id){
        var topico = topicoRepository.getReferenceById(id);
        return ResponseEntity.ok(new DtoOutTopico(topico));
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity updateTopico(
            @PathVariable Long id,
            @RequestBody @Valid DtoUpdateTopico data){

        String emailUsuario = SecurityUtils.getEmailUsuarioLogado();
        var usuario = (Usuario) usuarioRepository.findByEmail(emailUsuario);
        var curso = cursoRepository.findById(data.idCurso())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Curso não encontrado"));
        var topico = topicoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Tópico não encontrado"));

        if (!usuario.getId().equals(topico.getAutor().getId())){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Você não está autorizado a atualizar este tópico");
        }
        topico.updateInfos(data.titulo(), data.mensagem(), curso, data.status());

        return  ResponseEntity.ok(new DtoOutTopico(topico));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity deleteTopico(@PathVariable Long id){
        Topico topico = topicoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Este tópico não existe"));
        Usuario usuario = (Usuario) usuarioRepository.findByEmail(SecurityUtils.getEmailUsuarioLogado());

        if (usuario.equals(topico.getAutor()) || usuario.getRole().equals(Role.ADMIN)) topicoRepository.deleteById(id);

        return ResponseEntity.noContent().build();
    }

}
