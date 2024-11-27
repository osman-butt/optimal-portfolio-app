package dev.osmanb.portfolioService.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import dev.osmanb.portfolioService.model.PortfolioRequest;
import dev.osmanb.portfolioService.model.PortfolioResponse;
import dev.osmanb.portfolioService.model.YFinanceResponse;
import dev.osmanb.portfolioService.service.PortfolioOptimizationService;
import org.springframework.stereotype.Service;

@Service
public class PortfolioOptimizationServiceImpl implements PortfolioOptimizationService {

    private final YFinanceService yFinanceService;
    private final MarkowitzModel markowitzModel;


    public PortfolioOptimizationServiceImpl(YFinanceService yFinanceService, MarkowitzModel markowitzModel) {
        this.yFinanceService = yFinanceService;
        this.markowitzModel = markowitzModel;
    }
    @Override
    public PortfolioResponse optimalPortfolio(PortfolioRequest request) {
        // Validate request
        if (request == null) {
            throw new IllegalArgumentException("Request cannot be null");
        }

        // Get stock data
        YFinanceResponse yFinanceResponse;
        try {
            yFinanceResponse = yFinanceService.getStockData(request).block();
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        // Perform Optimization
        if (yFinanceResponse == null) {
            throw new IllegalArgumentException("No data found for the given tickers");
        }
        return markowitzModel.getOptimalPortfolio(yFinanceResponse);

    }
}
