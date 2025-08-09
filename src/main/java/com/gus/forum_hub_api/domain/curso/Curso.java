package com.gus.forum_hub_api.domain.curso;

import com.gus.forum_hub_api.domain.curso.dto.DtoNewCurso;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "cursos")
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@AllArgsConstructor
public class Curso {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    @Enumerated(EnumType.STRING)
    private Categoria categoria;

    private Boolean ativo;

    public Curso(DtoNewCurso data) {
        this.nome = data.nome();
        this.categoria = data.categoria();
        this.ativo = true;
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void updateInfos(String nome, Categoria categoria) {
        this.nome = nome;
        this.categoria = categoria;
    }

    public void disableCurso() {
        this.ativo = false;
    }
}
