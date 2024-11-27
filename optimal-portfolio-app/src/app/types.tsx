export interface OptimalPortfolio {
  weights: number[];
  expectedReturn: number;
  stdDeviation: number;
}

export interface EfficientFrontierPoint {
  expectedReturn: number;
  stdDeviation: number;
}

export interface CapitalMarketLine {
  intercept: number;
  slope: number;
}

export interface Tickers {
  ticker: string;
  expectedReturn: number;
  stdDeviation: number;
}

export interface PortfolioOptimizationResponse {
  optimalPortfolio: OptimalPortfolio;
  efficientFrontier: EfficientFrontierPoint[];
  capitalMarketLine: CapitalMarketLine;
  tickers: Tickers[];
}
