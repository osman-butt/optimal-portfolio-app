"use server";

import { PortfolioOptimizationResponse } from "@/app/types";

const BACKEND_BASEURL = process.env.BACKEND_BASEURL;

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
      BACKEND_BASEURL + "/api/portfolio/optimization",
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
