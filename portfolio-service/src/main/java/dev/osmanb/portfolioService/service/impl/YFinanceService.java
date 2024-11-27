package dev.osmanb.portfolioService.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import dev.osmanb.portfolioService.exception.*;
import dev.osmanb.portfolioService.model.PortfolioRequest;
import dev.osmanb.portfolioService.model.YFinanceResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class YFinanceService {
    private final String baseUrl;
    private final WebClient webClient;

    public YFinanceService(@Value("${stockMetrics.baseurl}") String baseUrl, WebClient webClient) {
        this.baseUrl = baseUrl;
        this.webClient = webClient;
    }

    public Mono<YFinanceResponse> getStockData(PortfolioRequest portfolioRequest) throws JsonProcessingException {
        return webClient.post()
                .uri(baseUrl)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(portfolioRequest.toJson())
                .retrieve()
                .onStatus(status -> status.value() == 422, (response) -> {
                    throw new UnprocessableContent("Invalid or missing name parameter");
                })
                .onStatus(status -> status.value() == 429, (response) -> {
                    throw new TooManyRequests("Request limit reached");
                })
                .bodyToMono(YFinanceResponse.class);
    }
}
