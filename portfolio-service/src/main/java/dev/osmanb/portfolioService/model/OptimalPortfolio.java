package dev.osmanb.portfolioService.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OptimalPortfolio {
    private double[] weights;
    private double expectedReturn;
    private double stdDeviation;
}
