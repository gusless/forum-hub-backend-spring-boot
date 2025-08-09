package com.gus.forum_hub_api.controller;

import com.gus.forum_hub_api.domain.curso.*;
import com.gus.forum_hub_api.domain.curso.dto.DtoCursoListener;
import com.gus.forum_hub_api.domain.curso.dto.DtoNewCurso;
import com.gus.forum_hub_api.domain.curso.dto.DtoOutCurso;
import com.gus.forum_hub_api.domain.curso.dto.DtoUpdateCurso;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/cursos")
public class CursoController {

    @Autowired
    CursoRepository cursoRepository;

    @PostMapping
    @Transactional
    ResponseEntity novoCurso(@Valid @RequestBody DtoNewCurso data){
        var curso = new Curso(data);
        cursoRepository.save(curso);
        return ResponseEntity.ok(new DtoOutCurso(curso));
    }

    @GetMapping
    public ResponseEntity<Page<DtoCursoListener>> getAllCursos(@PageableDefault(size = 20, sort = {"categoria"}) Pageable pageable){
        var page = cursoRepository.findAll(pageable).map(DtoCursoListener::new);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/{id}")
    public ResponseEntity getCurso(@PathVariable Long id){
        var curso = cursoRepository.getReferenceById(id);
        return ResponseEntity.ok(new DtoOutCurso(curso));
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity updateCurso(
            @PathVariable Long id,
            @Valid @RequestBody DtoUpdateCurso data){

        var curso = cursoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Curso não encontrado"));

        curso.updateInfos(data.nome(), data.categoria());
        return ResponseEntity.ok(new DtoOutCurso(curso));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity disableCurso(@PathVariable Long id){
        Curso curso = cursoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        curso.disableCurso();

        return ResponseEntity.noContent().build();
    }

}
