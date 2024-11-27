"use client";

import { useId, useState } from "react";
import Select from "react-select";
import { PortfolioOptimizationResponse } from "@/app/types";

type SelectedOption = { value: string; label: string };

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

const BACKEND_BASEURL = process.env.NEXT_PUBLIC_BACKEND_BASEURL;

export default function DropDownMenu({ handleResponse }: DropDownMenuProps) {
  console.log();
  const [selectedStocks, setSelectedStocks] = useState<string[]>([]);
  const [error, setError] = useState<string | null>(null);
  const [buttonDisabled, setButtonDisabled] = useState<boolean>(true);

  // Update selected stocks when the dropdown value changes
  const handleChange = (option: readonly SelectedOption[]) => {
    const selectedOptions = option ? option.map(stock => stock.value) : [];
    setSelectedStocks(selectedOptions);
    if (selectedOptions.length > 0) {
      setButtonDisabled(false);
    }
    setError(null); // Clear error on valid input
  };

  // Form submission handler
  const handleSubmit = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    setButtonDisabled(true);
    // Validate stock selection
    if (selectedStocks.length < 2 || selectedStocks.length > 6) {
      setError(
        `Please select between 2 and 6 stocks. Currently selected: ${selectedStocks.length}`
      );
      return;
    }

    setError(null); // Clear any existing errors

    try {
      const response = await fetch(
        BACKEND_BASEURL + "/api/portfolio/optimization",
        {
          method: "POST",
          headers: { "Content-Type": "application/json" },
          body: JSON.stringify({ tickers: selectedStocks }),
        }
      );

      if (!response.ok) throw new Error("Failed to fetch portfolio data");

      const data = await response.json();
      handleResponse(data);
    } catch (err) {
      console.error("Error:", err);
      setError("An error occurred while calculating the portfolio. Try again.");
    }
  };

  return (
    <form onSubmit={handleSubmit} className="space-y-4">
      {/* Multi-select dropdown */}
      <Select
        isMulti
        name="stocks"
        options={stocks}
        placeholder="Select up to 6 stocks..."
        className="basic-multi-select w-[300px] md:w-[400px] lg:w-[900px] text-left"
        classNamePrefix="select"
        onChange={handleChange}
        instanceId={useId()}
      />

      {/* Error message */}
      {error && <div className="text-red-500 animate-fade-in">{error}</div>}

      {/* Submit button */}
      <button
        type="submit"
        className={`w-full px-4 py-2 text-white bg-black rounded hover:bg-gray-800 transition-colors ${
          buttonDisabled
            ? "disabled:bg-gray-800/30 disabled:cursor-not-allowed"
            : ""
        }`}
        disabled={buttonDisabled}
      >
        Calculate Portfolio
      </button>
    </form>
  );
}
