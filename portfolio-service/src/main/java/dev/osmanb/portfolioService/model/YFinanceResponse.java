package dev.osmanb.portfolioService.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class YFinanceResponse {
    private double[] expectedReturns;
    private double[][] covarianceMatrix;
    private double riskFreeRate;
    private List<String> tickers;
    public YFinanceResponse(double[] expectedReturns, double[][] covarianceMatrix, double riskFreeRate, List<String> tickers) {
        this.expectedReturns = expectedReturns;
        this.covarianceMatrix = covarianceMatrix;
        this.riskFreeRate = riskFreeRate;
        this.tickers = tickers;
    }
}
