"use server";

import { PortfolioOptimizationResponse } from "@/app/types";

export async function calculatePortfolio(tickers: string[]): Promise<{
  data: PortfolioOptimizationResponse | null;
  error: string | null;
}> {
  if (tickers.length < 2 || tickers.length > 6) {
    return {
      data: null,
      error: `Please select between 2 and 6 stocks. Currently selected: ${tickers.length}`,
    };
  }

  try {
    const response = await fetch(
      "http://localhost:8080/api/portfolio/optimization",
      {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ tickers }),
      }
    );

    if (!response.ok) {
      const errorText = await response.text();
      throw new Error(
        `Failed to fetch portfolio data: ${response.status} ${errorText}`
      );
    }

    const data = await response.json();
    return { data: data as PortfolioOptimizationResponse, error: null };
  } catch (err) {
    console.error("Error in calculatePortfolio:", err);
    return {
      data: null,
      error: err instanceof Error ? err.message : "An unknown error occurred",
    };
  }
}
