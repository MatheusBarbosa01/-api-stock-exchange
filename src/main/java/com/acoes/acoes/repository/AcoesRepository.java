package com.acoes.acoes.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.acoes.acoes.dto.AcoesRequestDTO;
import com.acoes.acoes.entity.Acoes;
import java.util.List;

public interface AcoesRepository extends JpaRepository<Acoes, Long> {
    List<AcoesRequestDTO> findBySymbolOrderByTimestampDesc(String symbol);
}
