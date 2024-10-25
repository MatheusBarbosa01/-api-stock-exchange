package com.acoes.acoes.dto;

import com.acoes.acoes.entity.Acoes;

public record AcoesRequestDTO(Long id, String symbol, Double open, Double high, Double low, Double close, Long volume, String timestamp) {
    public AcoesRequestDTO(Acoes acoes){
        this(acoes.getId(),acoes.getSymbol(),acoes.getOpen(),acoes.getHigh(),acoes.getLow(),acoes.getClose(), acoes.getVolume(),acoes.getTimestamp());
    }
}
