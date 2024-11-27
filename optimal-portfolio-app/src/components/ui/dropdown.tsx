"use client";

import { useId, useState, useTransition } from "react";
import Select from "react-select";
import { PortfolioOptimizationResponse } from "@/app/types";
import { calculatePortfolio } from "@/app/actions/portfolioActions";

type SelectedOption = {
  value: string;
  label: string;
};

const stocks: SelectedOption[] = [
  { value: "NOVO-B.CO", label: "Novo Nordisk B" },
  { value: "CARL-B.CO", label: "Carlsberg B" },
  { value: "MAERSK-B.CO", label: "A.P. Moeller-Maersk B" },
  { value: "COLO-B.CO", label: "Coloplast B" },
  { value: "DANSKE.CO", label: "Danske Bank A/S" },
  { value: "PNDORA.CO", label: "Pandora A/S" },
  { value: "GN.CO", label: "GN Store Nord A/S" },
  { value: "TRYG.CO", label: "Tryg A/S" },
  { value: "JYSK.CO", label: "Jyske Bank A/S" },
  { value: "FLS.CO", label: "FLSmidth & Co. A/S" },
  { value: "VWS.CO", label: "Vestas Wind Systems A/S" },
  { value: "ROCK-B.CO", label: "Rockwool International B" },
  { value: "NNIT.CO", label: "NNIT A/S" },
];

type DropDownMenuProps = {
  handleResponse: (data: PortfolioOptimizationResponse) => void;
};

export default function DropDownMenu({ handleResponse }: DropDownMenuProps) {
  const [selectedStocks, setSelectedStocks] = useState<SelectedOption[]>([]);
  const [error, setError] = useState<string | null>(null);
  const [isPending, startTransition] = useTransition();

  const handleChange = (options: readonly SelectedOption[]) => {
    setSelectedStocks(options as SelectedOption[]);
    setError(null);
  };

  const handleSubmit = async (event: React.FormEvent<HTMLFormElement>) => {
    event.preventDefault();
    if (selectedStocks.length < 2 || selectedStocks.length > 6) {
      setError(
        `Please select between 2 and 6 stocks. Currently selected: ${selectedStocks.length}`
      );
      return;
    }

    startTransition(async () => {
      try {
        const result = await calculatePortfolio(
          selectedStocks.map(stock => stock.value)
        );
        if (result.error) {
          setError(result.error);
        } else if (result.data) {
          handleResponse(result.data);
        }
      } catch (err) {
        setError("An unexpected error occurred. Please try again.");
        console.log(err);
      }
    });
  };

  const isSubmitDisabled =
    selectedStocks.length < 2 || selectedStocks.length > 6 || isPending;

  return (
    <form onSubmit={handleSubmit} className="space-y-4">
      <Select
        isMulti
        name="stocks"
        options={stocks}
        value={selectedStocks}
        onChange={handleChange}
        placeholder="Select up to 6 stocks..."
        className="basic-multi-select w-[300px] md:w-[400px] lg:w-[900px] text-left"
        classNamePrefix="select"
        instanceId={useId()}
      />

      {error && <div className="text-red-500 animate-fade-in">{error}</div>}

      <button
        type="submit"
        className={`w-full px-4 py-2 text-white bg-black rounded hover:bg-gray-800 transition-colors ${
          isSubmitDisabled ? "bg-gray-800/30 cursor-not-allowed" : ""
        }`}
        disabled={isSubmitDisabled}
      >
        {isPending ? "Calculating..." : "Calculate Portfolio"}
      </button>
    </form>
  );
}
