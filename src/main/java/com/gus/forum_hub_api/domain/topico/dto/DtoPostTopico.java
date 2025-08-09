package com.gus.forum_hub_api.domain.topico.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DtoPostTopico (
    @NotBlank
    String titulo,
    @NotBlank
    String mensagem,
    @NotNull
    Long idCurso

) {
}
