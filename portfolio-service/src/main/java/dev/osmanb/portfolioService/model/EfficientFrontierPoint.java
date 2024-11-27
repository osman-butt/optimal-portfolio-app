package dev.osmanb.portfolioService.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EfficientFrontierPoint {
    private double expectedReturn;
    private double stdDeviation;
}
