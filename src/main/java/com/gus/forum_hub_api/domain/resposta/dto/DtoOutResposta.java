package com.gus.forum_hub_api.domain.resposta.dto;

import com.gus.forum_hub_api.domain.resposta.Resposta;

import java.time.LocalDateTime;

public record DtoOutResposta(
    Long id,
    String mensagem,
    LocalDateTime dataCriacao,
    Long topicoId,
    Long usuarioId
) {
    public DtoOutResposta(Resposta resposta) {
        this(
                resposta.getId(),
                resposta.getMensagem(),
                resposta.getDataCriacao(),
                resposta.getTopico().getId(),
                resposta.getAutor().getId()
        );
    }
}
