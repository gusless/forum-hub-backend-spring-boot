package com.gus.forum_hub_api.domain.topico;


import com.gus.forum_hub_api.domain.curso.Curso;
import com.gus.forum_hub_api.domain.resposta.Resposta;
import com.gus.forum_hub_api.domain.topico.dto.DtoPostTopico;
import com.gus.forum_hub_api.domain.usuario.Usuario;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "topicos")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Topico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;

    private String mensagem;

    private LocalDateTime dataCriacao;

    @Enumerated(EnumType.STRING)
    private Status status;

    @ManyToOne
    @JoinColumn(name = "autor_id")
    private Usuario autor;

    @ManyToOne
    @JoinColumn(name = "curso_id")
    private Curso curso;

    @OneToMany(mappedBy = "topico", cascade = CascadeType.ALL)
    private List<Resposta> respostas;

    public Topico(DtoPostTopico dto, Usuario usuario, Curso curso) {
        this.titulo = dto.titulo();
        this.mensagem = dto.mensagem();
        this.dataCriacao = LocalDateTime.now();
        this.status = Status.ABERT0;
        this.autor = usuario;
        this.curso = curso;
    }

    public void updateInfos(String titulo, String mensagem, Curso curso, Status status) {
        this.titulo = titulo;
        this.mensagem = mensagem;
        this.curso = curso;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getMensagem() {
        return mensagem;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public Status getStatus() {
        return status;
    }

    public Usuario getAutor() {
        return autor;
    }

    public Curso getCurso() {
        return curso;
    }

    public List<Resposta> getRespostas() {
        return respostas;
    }
}
