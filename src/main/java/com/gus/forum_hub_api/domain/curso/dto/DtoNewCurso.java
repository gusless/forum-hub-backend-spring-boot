package com.gus.forum_hub_api.domain.curso.dto;

import com.gus.forum_hub_api.domain.curso.Categoria;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DtoNewCurso(
        @NotBlank
        String nome,
        @NotNull
        Categoria categoria
) {
}
