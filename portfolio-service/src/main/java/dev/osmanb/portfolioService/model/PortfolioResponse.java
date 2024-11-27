package dev.osmanb.portfolioService.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PortfolioResponse {
    private OptimalPortfolio optimalPortfolio;
    private List<EfficientFrontierPoint> efficientFrontier;
    private CapitalMarketLine capitalMarketLine;
    private List<Stock> tickers;
}
