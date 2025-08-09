package com.gus.forum_hub_api.domain.curso.dto;

import com.gus.forum_hub_api.domain.curso.Categoria;
import com.gus.forum_hub_api.domain.curso.Curso;

public record DtoOutCurso(
        Long id,
        String nome,
        Categoria categoria
) {
    public DtoOutCurso(Curso curso) {
        this(
                curso.getId(),
                curso.getNome(),
                curso.getCategoria()
        );
    }
}
