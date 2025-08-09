package com.gus.forum_hub_api.domain.topico.dto;

import com.gus.forum_hub_api.domain.topico.Status;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DtoUpdateTopico(
        @NotBlank
        String titulo,
        @NotBlank
        String mensagem,
        @NotNull
        Long idCurso,
        @NotNull
        Status status
) {
}
