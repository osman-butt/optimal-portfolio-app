package dev.osmanb.portfolioService.service;

import dev.osmanb.portfolioOptimization.model.PortfolioRequest;
import dev.osmanb.portfolioOptimization.model.PortfolioResponse;

public interface PortfolioOptimizationService {
    PortfolioResponse optimalPortfolio(PortfolioRequest request);
}
