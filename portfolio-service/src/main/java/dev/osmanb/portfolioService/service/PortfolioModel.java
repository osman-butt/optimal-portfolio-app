package dev.osmanb.portfolioService.service;

public interface PortfolioModel<I,O> {
    O getOptimalPortfolio(I input);
}
