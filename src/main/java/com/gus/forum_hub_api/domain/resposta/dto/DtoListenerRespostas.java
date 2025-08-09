package com.gus.forum_hub_api.domain.resposta.dto;

import com.gus.forum_hub_api.domain.resposta.Resposta;

import java.time.LocalDateTime;

public record DtoListenerRespostas(
        String mensagem,
        LocalDateTime dataCriacao,
        Long topicoId,
        Long usuarioId
) {
    public DtoListenerRespostas(Resposta resposta){
        this(
                resposta.getMensagem(),
                resposta.getDataCriacao(),
                resposta.getTopico().getId(),
                resposta.getAutor().getId()
        );
    }
}
