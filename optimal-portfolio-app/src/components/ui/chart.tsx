"use client";

import { PortfolioOptimizationResponse } from "@/app/types";
import {
  ResponsiveContainer,
  XAxis,
  YAxis,
  CartesianGrid,
  ScatterChart,
  Scatter,
  ZAxis,
  Legend,
  Label,
} from "recharts";

type EfficientFrontierProps = {
  portfolioData: PortfolioOptimizationResponse | null;
};

export const EfficientFrontierChart = ({
  portfolioData,
}: EfficientFrontierProps) => {
  if (!portfolioData || !portfolioData.efficientFrontier.length)
    return <div>No data available for display</div>;

  const tangentPF = [
    {
      stdDeviation: portfolioData.optimalPortfolio.stdDeviation,
      expectedReturn: portfolioData.optimalPortfolio.expectedReturn,
      name: "Tangent Portfolio",
    },
  ];

  const efficientFrontier = [
    {
      stdDeviation: 0,
      expectedReturn: portfolioData.capitalMarketLine.intercept,
      name: "Risk-free rate",
    },
    {
      stdDeviation:
        (Number(
          portfolioData.efficientFrontier[
            portfolioData.efficientFrontier.length - 1
          ].expectedReturn
        ) -
          portfolioData.capitalMarketLine.intercept) /
        portfolioData.capitalMarketLine.slope,
      expectedReturn: Number(
        portfolioData.efficientFrontier[
          portfolioData.efficientFrontier.length - 1
        ].expectedReturn
      ),
      name: "CML",
    },
  ];

  const stockEfficientFrontier = portfolioData.efficientFrontier;

  return (
    <ResponsiveContainer width="100%" height="100%">
      <ScatterChart
        margin={{
          // top: 20,
          right: 45,
          bottom: 20,
          left: 15,
        }}
      >
        <CartesianGrid />
        <XAxis type="number" dataKey="stdDeviation" name="stdDeviation">
          <Label value={"Risk"} position="right" offset={15} />
        </XAxis>
        <YAxis type="number" dataKey="expectedReturn" name="return">
          <Label value={"Exp. return"} position="insideLeft" angle={-90} />
        </YAxis>
        {/* <Tooltip cursor={{ strokeDasharray: "3 3" }} /> */}
        <Scatter
          name="Efficient frontier"
          data={stockEfficientFrontier}
          fill="black"
          line
        />
        {/* <Scatter name="APPL" data={AAPL} fill="blue" />
        <Scatter name="GOOGL" data={GOOGL} fill="red" />
        <Scatter name="MSFT" data={MSFT} fill="yellow" /> */}
        <Scatter name="Optimal PF" data={tangentPF} fill="green" />
        <Scatter
          name="Capital Market Line"
          data={efficientFrontier}
          fill="#82ca9d"
          line
        />
        <ZAxis dataKey="name" range={[3]} />
        <Legend verticalAlign="bottom" height={36} />
      </ScatterChart>
    </ResponsiveContainer>
  );
};

export default EfficientFrontierChart;
