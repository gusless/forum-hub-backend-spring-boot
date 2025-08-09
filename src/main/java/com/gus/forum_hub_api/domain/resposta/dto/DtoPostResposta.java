package com.gus.forum_hub_api.domain.resposta.dto;

import jakarta.validation.constraints.NotBlank;

public record DtoPostResposta(
        @NotBlank
        String mensagem
) {
}
