package com.acoes.acoes.services;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.acoes.acoes.dto.AcoesRequestDTO;
import com.acoes.acoes.entity.Acoes;
import com.acoes.acoes.repository.AcoesRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;

import java.util.Iterator;
@Service
public class AcoesDataService {

    private final AcoesRepository repository;
    private final RestTemplate restTemplate;

    public AcoesDataService(AcoesRepository repository, RestTemplate restTemplate) {
        this.repository = repository;
        this.restTemplate = restTemplate;
    }

    public String fetchAcoesData(String symbol) {
        String apiKey = "TCB4IQMEIQUW3L3P";
        String url = "https://www.alphavantage.co/query?function=TIME_SERIES_INTRADAY&symbol=" + symbol + "&interval=5min&apikey=" + apiKey;
        String response = restTemplate.getForObject(url, String.class);

        saveAcoesData(response, symbol);
        return response;
    }

    private void saveAcoesData(String response, String symbol) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(response);
            JsonNode timeSeriesNode = rootNode.path("Time Series (5min)");

            if (timeSeriesNode.isObject()) {
                Iterator<String> iterator = timeSeriesNode.fieldNames();
                
                while (iterator.hasNext()) {
                    String timestamp = iterator.next();
                    JsonNode priceData = timeSeriesNode.path(timestamp);
                    
                    Acoes acoesData = new Acoes();
                    acoesData.setSymbol(symbol);
                    acoesData.setOpen(priceData.path("1. open").asDouble());
                    acoesData.setHigh(priceData.path("2. high").asDouble());
                    acoesData.setLow(priceData.path("3. low").asDouble());
                    acoesData.setClose(priceData.path("4. close").asDouble());
                    acoesData.setVolume(priceData.path("5. volume").asLong());
                    acoesData.setTimestamp(timestamp);
                    
                    repository.save(acoesData);
                }
            }
        } catch (Exception e) {
            e.printStackTrace(); 
        }
    }

    public List<AcoesRequestDTO> getAcoesFromDatabase(String symbol) {
        return repository.findBySymbolOrderByTimestampDesc(symbol);
    }
}
