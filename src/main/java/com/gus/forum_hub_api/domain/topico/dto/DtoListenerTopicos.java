package com.gus.forum_hub_api.domain.topico.dto;

import com.gus.forum_hub_api.domain.topico.Topico;

import java.time.LocalDateTime;

public record DtoListenerTopicos(
        String titulo,
        String mensagem,
        LocalDateTime dataCriacao,
        Long usuarioId,
        Long cursoId
) {
    public DtoListenerTopicos(Topico topico){
        this(
                topico.getTitulo(),
                topico.getMensagem(),
                topico.getDataCriacao(),
                topico.getAutor().getId(),
                topico.getCurso().getId()
        );
    }
}
