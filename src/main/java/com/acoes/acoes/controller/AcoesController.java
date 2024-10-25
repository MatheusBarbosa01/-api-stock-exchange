package com.acoes.acoes.controller;

import com.acoes.acoes.dto.AcoesRequestDTO;
import com.acoes.acoes.services.AcoesDataService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/acoes")
public class AcoesController {

    private final AcoesDataService acoesDataService;

    public AcoesController(AcoesDataService acoesDataService) {
        this.acoesDataService = acoesDataService;
    }
    @PostMapping("/fetch/{symbol}")
    public String fetchAndSaveAcoesData(@PathVariable String symbol) {
        return acoesDataService.fetchAcoesData(symbol);
    }

    @GetMapping("/{symbol}")
    public List<AcoesRequestDTO> getAcoesData(@PathVariable String symbol) {
        return acoesDataService.getAcoesFromDatabase(symbol);
    }
}
