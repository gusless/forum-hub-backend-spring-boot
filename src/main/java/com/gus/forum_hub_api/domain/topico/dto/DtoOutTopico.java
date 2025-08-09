package com.gus.forum_hub_api.domain.topico.dto;

import com.gus.forum_hub_api.domain.topico.Status;
import com.gus.forum_hub_api.domain.topico.Topico;

import java.time.LocalDateTime;

public record DtoOutTopico (
        Long id,
        String titulo,
        String mensagem,
        Status status,
        LocalDateTime dataCriacao,
        Long idUsuario,
        Long idCurso
) {
    public DtoOutTopico(Topico topico) {
        this(
                topico.getId(),
                topico.getTitulo(),
                topico.getMensagem(),
                topico.getStatus(),
                topico.getDataCriacao(),
                topico.getAutor().getId(),
                topico.getCurso().getId()
        );
    }
}
