from flask import Flask, request, jsonify
import pandas as pd
import yfinance as yf

# Initialize Flask app
app = Flask(__name__)

@app.route('/portfolio/metrics', methods=['POST'])
def calculate_portfolio_metrics():
    # Parse the request body for tickers
    data = request.json
    tickers = data.get("tickers", [])

    if not tickers:
        return jsonify({"error": "No tickers provided."}), 400

    try:
        tickers = sorted(tickers)

        prices = yf.download(tickers, start="2010-01-01", end="2024-01-01")["Adj Close"]

        yearly_prices = prices.resample('Y').last()

        yearly_returns = yearly_prices.pct_change().dropna()

        expected_yearly_returns = yearly_returns.mean().tolist()

        covariance_matrix = yearly_returns.cov().values.tolist()

        # TODO: CHOOSE AN APPROPRIATE RISK-FREE RATE
        risk_free_data = yf.download("^IRX", start="2010-01-01", end="2024-01-01")["Adj Close"]
        risk_free_rate = (risk_free_data.resample('Y').last().mean() / 100).item()  # Convert to decimal

        response = {
            "tickers": tickers,
            "expectedReturns": expected_yearly_returns,
            "covarianceMatrix": covariance_matrix,
            "riskFreeRate": risk_free_rate
        }

        return jsonify(response)

    except Exception as e:
        return jsonify({"error": str(e)}), 500

# Run the Flask app
if __name__ == "__main__":
    app.run(debug=True)
