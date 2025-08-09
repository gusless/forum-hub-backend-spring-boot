package com.gus.forum_hub_api.domain.resposta;

import com.gus.forum_hub_api.domain.topico.Topico;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RespostaRepository extends JpaRepository<Resposta, Long> {
    Page<Resposta> findByTopicoId(Pageable pageable, Long id);
}
