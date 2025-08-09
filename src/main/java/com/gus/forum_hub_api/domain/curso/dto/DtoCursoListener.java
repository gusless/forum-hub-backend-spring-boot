package com.gus.forum_hub_api.domain.curso.dto;

import com.gus.forum_hub_api.domain.curso.Categoria;
import com.gus.forum_hub_api.domain.curso.Curso;

public record DtoCursoListener(
        String nome,
        Categoria categoria
) {
    public DtoCursoListener(Curso curso){
        this(
                curso.getNome(),
                curso.getCategoria()
        );
    }
}
