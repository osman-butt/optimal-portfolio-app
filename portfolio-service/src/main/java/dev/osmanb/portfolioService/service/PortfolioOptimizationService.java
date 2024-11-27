package dev.osmanb.portfolioService.service;

import dev.osmanb.portfolioService.model.PortfolioRequest;
import dev.osmanb.portfolioService.model.PortfolioResponse;

public interface PortfolioOptimizationService {
    PortfolioResponse optimalPortfolio(PortfolioRequest request);
}
