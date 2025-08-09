package com.gus.forum_hub_api.controller;

import com.gus.forum_hub_api.domain.resposta.dto.DtoOutResposta;
import com.gus.forum_hub_api.domain.resposta.RespostaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/respostas")
public class RespostaLivreController {

    @Autowired
    RespostaRepository respostaRepository;

    @GetMapping
    public ResponseEntity getAllRespostas(@PageableDefault(size = 20, sort = "topicoId") Pageable pageable){
        var page = respostaRepository.findAll(pageable).map(DtoOutResposta::new);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/{id}")
    public ResponseEntity getResposta(@PathVariable Long id){
        var resposta = respostaRepository.getReferenceById(id);
        return ResponseEntity.ok(new DtoOutResposta(resposta));
    }
}
