import { PortfolioOptimizationResponse } from "@/app/types";

type AllocationTableProps = {
  data: PortfolioOptimizationResponse | null;
};

export default function AllocationTable({ data }: AllocationTableProps) {
  if (!data)
    return <p className="text-black/50">No data available for display</p>;

  const stocks = (data.tickers || []).map((ticker, index) => ({
    name: ticker.ticker,
    value: data.optimalPortfolio.weights?.[index] || 0,
  }));

  const totalAllocation = stocks.reduce((acc, stock) => acc + stock.value, 0);
  const expectedReturn = data.optimalPortfolio.expectedReturn * 100;
  const expectedRisk = data.optimalPortfolio.stdDeviation * 100;

  return (
    <div className="flex flex-col items-center space-y-6">
      {/* Allocation Table */}
      <div>
        <table className="min-w-[320px] border-collapse border-spacing-2">
          <thead className="border-b-2 border-gray-200">
            <tr>
              <th className="text-left py-2 px-4">Ticker</th>
              <th className="text-right py-2 px-4">Allocation (%)</th>
            </tr>
          </thead>
          <tbody>
            {stocks.map((stock, index) => (
              <tr key={index} className="hover:bg-gray-100">
                <td className="py-1 px-4">{stock.name}</td>
                <td className="py-1 px-4 text-right">
                  {formatPercentage(stock.value * 100)}
                </td>
              </tr>
            ))}
            <tr className="border-t font-bold">
              <td className="py-2 px-4">Total</td>
              <td className="py-2 px-4 text-right">
                {formatPercentage(totalAllocation * 100)}
              </td>
            </tr>
          </tbody>
        </table>
      </div>

      {/* Portfolio Metrics */}
      <div>
        <table className="min-w-[320px]">
          <tbody>
            <tr className="hover:bg-gray-100">
              <td className="py-1 px-4 text-left">Expected Return</td>
              <td className="py-1 px-4 text-right font-semibold">
                {formatPercentage(expectedReturn)}
              </td>
            </tr>
            <tr className="hover:bg-gray-100">
              <td className="py-1 px-4 text-left">Expected Risk</td>
              <td className="py-1 px-4 text-right font-semibold">
                {formatPercentage(expectedRisk)}
              </td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>
  );
}

// Utility function to format numbers as percentages
function formatPercentage(num: number): string {
  return `${num.toFixed(2)}%`;
}
