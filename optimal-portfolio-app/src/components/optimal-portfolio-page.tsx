"use client";

import { useState } from "react";
import EfficientFrontierChart from "@/components/ui/chart";
import DropDownMenu from "@/components/ui/dropdown";
import AllocationTable from "@/components/ui/table";
import { PortfolioOptimizationResponse } from "@/app/types";
import { GridItem, TickerSelector } from "@/components/ui/grid-item";
import { ChartSkeleton } from "@/components/ui/skeletons";

export type TangentType = {
  variance: number;
  expectedReturn: number;
  name: string;
};

export default function OptimalPortfolioPage() {
  const [data, setData] = useState<PortfolioOptimizationResponse | null>(null);

  const handleResponse = (data: PortfolioOptimizationResponse) => {
    setData(data);
  };

  return (
    <main className="p-4 grid md:grid-cols-2 gap-4">
      <div className="col-span-1 md:col-span-2 text-center">
        <div className="mt-4">
          <TickerSelector title="Select stocks">
            <DropDownMenu handleResponse={handleResponse} />
          </TickerSelector>
        </div>
      </div>
      <GridItem title="Return vs risk">
        {data?.optimalPortfolio.expectedReturn ? (
          <EfficientFrontierChart portfolioData={data} />
        ) : (
          <ChartSkeleton />
        )}
      </GridItem>
      <div className="order-3 md:order-2">
        <GridItem title="Optimal portfolio allocation">
          <AllocationTable data={data} />
        </GridItem>
      </div>
      <p className="text-left text-xs text-gray-500 order-2 md:order-3">
        * The chart above shows the efficient frontier of your portfolio based
        on the selected stocks.
      </p>
      <p className="text-left text-xs text-gray-500 order-3 md:order-4">
        * The table above shows the optimal allocation of your portfolio based
        on the selected stocks.
      </p>
    </main>
  );
}
