from flask import Flask, request, jsonify
import pandas as pd
import yfinance as yf
import datetime as dt

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

        years = 10

        end = dt.date.today()
        start = end - dt.timedelta(days=365 * years)

        prices = yf.download(tickers, start, end)["Close"]

        yearly_prices = prices.resample('YE').last()

        yearly_returns = yearly_prices.pct_change().dropna()

        expected_yearly_returns = yearly_returns.mean().tolist()

        covariance_matrix = yearly_returns.cov().values.tolist()

        # TODO: CHOOSE AN APPROPRIATE RISK-FREE RATE
        risk_free_data = yf.download("^IRX", start, end)["Close"]
        risk_free_rate = (risk_free_data.resample('YE').last().mean() / 100).item()  # Convert to decimal

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
    port = 5000
    app.run(debug=False, host="0.0.0.0", port=port)
    # app.run(debug=True, host="0.0.0.0", port=port)
