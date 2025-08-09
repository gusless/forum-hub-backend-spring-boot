package com.gus.forum_hub_api.controller;

import com.gus.forum_hub_api.domain.resposta.*;
import com.gus.forum_hub_api.domain.resposta.dto.DtoListenerRespostas;
import com.gus.forum_hub_api.domain.resposta.dto.DtoOutResposta;
import com.gus.forum_hub_api.domain.resposta.dto.DtoPostResposta;
import com.gus.forum_hub_api.domain.resposta.dto.DtoUpdateResposta;
import com.gus.forum_hub_api.domain.topico.TopicoRepository;
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
@RequestMapping("/topicos/{id}/respostas")
public class RespostaController {

    @Autowired
    TopicoRepository topicoRepository;

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    RespostaRepository respostaRepository;

    @PostMapping
    @Transactional
    public ResponseEntity postResposta(@PathVariable Long id, @RequestBody @Valid DtoPostResposta data){
        var topico = topicoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Tópico não encontrado"));
        String emailUsuario = SecurityUtils.getEmailUsuarioLogado();
        var usuario = (Usuario) usuarioRepository.findByEmail(emailUsuario);
        var resposta = new Resposta(data.mensagem(), topico, usuario);

        respostaRepository.save(resposta);
        return ResponseEntity.ok(new DtoOutResposta(resposta));
    }

    @GetMapping
    public ResponseEntity<Page<DtoListenerRespostas>> getAllRespostasByTopico(@PathVariable Long id, @PageableDefault(size = 15, sort = {"dataCriacao"}) Pageable pageable){
        var page = respostaRepository.findByTopicoId(pageable, id).map(DtoListenerRespostas::new);
        return ResponseEntity.ok(page);
    }

    @PutMapping("/{idResposta}")
    @Transactional
    public ResponseEntity updateResposta(
            @PathVariable Long idResposta,
            @Valid @RequestBody DtoUpdateResposta data){
        Usuario usuario = (Usuario) usuarioRepository.findByEmail(SecurityUtils.getEmailUsuarioLogado());
        var resposta = respostaRepository.findById(idResposta)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Resposta não encontrada"));

        if (!usuario.equals(resposta.getAutor())){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Você não está permitido de editar essa resposta");
        }

        resposta.updateInfos(data.mensagem());
        return ResponseEntity.ok(new DtoOutResposta(resposta));
    }

    @DeleteMapping("/idResposta")
    @Transactional
    public ResponseEntity deleteResposta(@PathVariable Long id){
        Resposta resposta = respostaRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Resposta não encontrada"));
        Usuario usuario = (Usuario) usuarioRepository.findByEmail(SecurityUtils.getEmailUsuarioLogado());

        if (usuario.equals(resposta.getAutor()) || usuario.getRole().equals(Role.ADMIN)) respostaRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
