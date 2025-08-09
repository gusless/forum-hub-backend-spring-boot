package com.gus.forum_hub_api.domain.usuario.dto;

import com.gus.forum_hub_api.domain.usuario.Usuario;

public record DtoOutUsuario (
        Long id,
        String nome,
        String email
) {
    public DtoOutUsuario(Usuario usuario) {
        this(
                usuario.getId(),
                usuario.getNome(),
                usuario.getEmail()
        );
    }
}
