package dev.osmanb.portfolioService.service.impl;

import dev.osmanb.matrix.Matrix;
import dev.osmanb.matrix.MatrixUtils;
import dev.osmanb.portfolioService.model.*;
import dev.osmanb.portfolioService.service.PortfolioModel;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MarkowitzModel implements PortfolioModel<YFinanceResponse, PortfolioResponse> {
    @Override
    public PortfolioResponse getOptimalPortfolio(YFinanceResponse yFinanceResponse) {
        // Risk-free rate
        double riskFreeRate = yFinanceResponse.getRiskFreeRate();

        double[] expectedReturns = yFinanceResponse.getExpectedReturns();

        List<String> tickers = yFinanceResponse.getTickers();

        // Create necessary matrices
        Matrix excessReturns = createExcessReturnsMatrix(expectedReturns, riskFreeRate);
        Matrix excessReturnsTranspose = excessReturns.transpose();

        Matrix covMatrix = new Matrix(yFinanceResponse.getCovarianceMatrix());
        Matrix covMatrixInverse = covMatrix.inverse();

        Matrix onesVector = createOnesVector(excessReturns.getRows());
        Matrix onesVectorTranspose = onesVector.transpose();

        Matrix AMatrix = getAMatrix(excessReturns, covMatrixInverse);


        // Calculate the tangent portfolio
        double tangentPfExcessReturn = getTangentPfExcessReturn(excessReturnsTranspose, covMatrixInverse, excessReturns, onesVectorTranspose);
        double[] tangentPortfolioWeights = getTangentPortfolioWeights(covMatrixInverse, excessReturns, excessReturnsTranspose, tangentPfExcessReturn);
        double tangentPfVariance = getTangentPfVariance(tangentPfExcessReturn, excessReturnsTranspose, covMatrixInverse, excessReturns);
        double tangentPfStdDeviation = Math.sqrt(tangentPfVariance);

        List<EfficientFrontierPoint> efficientFrontierList = getEfficientFrontier(riskFreeRate, tangentPfExcessReturn, AMatrix);

        CapitalMarketLine cml = getCapitalMarketLine(excessReturnsTranspose, covMatrixInverse, excessReturns,riskFreeRate);

        OptimalPortfolio optimalPortfolio = buildOptimalPortfolioObject(tangentPfExcessReturn, tangentPortfolioWeights,tangentPfStdDeviation,riskFreeRate);
        List<Stock> stockList = buildStockListObject(tickers,expectedReturns, covMatrix);

        return buildResponse(optimalPortfolio, efficientFrontierList, cml, stockList);
    }

    private OptimalPortfolio buildOptimalPortfolioObject(double tangentPfExcessReturn,
                                                         double[] tangentPortfolioWeights,
                                                         double tangentPfStdDeviation,
                                                         double riskFreeRate) {
        OptimalPortfolio optimalPortfolio = new OptimalPortfolio();
        optimalPortfolio.setExpectedReturn(tangentPfExcessReturn+riskFreeRate);
        optimalPortfolio.setStdDeviation(tangentPfStdDeviation);
        optimalPortfolio.setWeights(tangentPortfolioWeights);
        return optimalPortfolio;
    }

    private List<Stock> buildStockListObject(List<String> tickers,double[] expectedReturns,Matrix covMatrix) {
        List<Stock> stocks = new ArrayList<>();
        for (int i = 0; i < tickers.size(); i++) {
            Stock stock = new Stock();
            stock.setTicker(tickers.get(i));
            stock.setExpectedReturn(expectedReturns[i]);
            stock.setStdDeviation(Math.sqrt(covMatrix.getElement(i,i)));
            stocks.add(stock);
        }
        return stocks;
    }

    private PortfolioResponse buildResponse(
            OptimalPortfolio optimalPortfolio,
            List<EfficientFrontierPoint> efficientFrontierList,
            CapitalMarketLine cml,
            List<Stock> stockList){

        PortfolioResponse portfolioResponse = new PortfolioResponse();
        portfolioResponse.setOptimalPortfolio(optimalPortfolio);
        portfolioResponse.setEfficientFrontier(efficientFrontierList);
        portfolioResponse.setCapitalMarketLine(cml);
        portfolioResponse.setTickers(stockList);
        return portfolioResponse;
    }

    private Matrix createExcessReturnsMatrix(double[] expectedReturns, double riskFreeRate) {
        Matrix excessReturns = new Matrix(expectedReturns);
        for (int i = 0; i < excessReturns.getRows(); i++) {
            excessReturns.setElement(i, 0, excessReturns.getElement(i, 0) - riskFreeRate);
        }
        return excessReturns;
    }

    private Matrix createOnesVector(int rows) {
        Matrix onesVector = new Matrix(rows, 1);
        for (int i = 0; i < onesVector.getRows(); i++) {
            onesVector.setElement(i, 0, 1.0);
        }
        return onesVector;
    }

    private double getTangentPfExcessReturn(Matrix excessReturnsTranspose, Matrix covMatrixInverse, Matrix excessReturns, Matrix onesVectorTranspose) {
        double tangentPfReturnNumerator = MatrixUtils.multiply(excessReturnsTranspose, MatrixUtils.multiply(covMatrixInverse, excessReturns)).getElement(0, 0);
        double tangentPfReturnDenominator = MatrixUtils.multiply(onesVectorTranspose, MatrixUtils.multiply(covMatrixInverse, excessReturns)).getElement(0, 0);
        return tangentPfReturnNumerator / tangentPfReturnDenominator;
    }

    private double[] getTangentPortfolioWeights(Matrix covMatrixInverse, Matrix excessReturns, Matrix excessReturnsTranspose, double tangentPfExcessReturn) {
        Matrix covInverseExcessReturns = MatrixUtils.multiply(covMatrixInverse, excessReturns);
        double fraction = tangentPfExcessReturn / MatrixUtils.multiply(excessReturnsTranspose,MatrixUtils.multiply(covMatrixInverse, excessReturns)).getElement(0, 0);
        Matrix tangentPortfolioWeights = new Matrix(excessReturns.getRows(), 1);
        for (int i = 0; i < tangentPortfolioWeights.getRows(); i++) {
            tangentPortfolioWeights.setElement(i, 0, fraction * covInverseExcessReturns.getElement(i, 0));
        }

        double[] weights = new double[tangentPortfolioWeights.getRows()];
        for (int i = 0; i < tangentPortfolioWeights.getRows(); i++) {
            weights[i] = tangentPortfolioWeights.getElement(i, 0);
        }

        return weights;
    }

    private double getTangentPfVariance(double tangentPfExcessReturn, Matrix excessReturnsTranspose, Matrix covMatrixInverse, Matrix excessReturns) {
        double tangentPfReturnNumerator = MatrixUtils.multiply(excessReturnsTranspose, MatrixUtils.multiply(covMatrixInverse, excessReturns)).getElement(0, 0);
        return tangentPfExcessReturn * tangentPfExcessReturn / tangentPfReturnNumerator;
    }

    private List<EfficientFrontierPoint> getEfficientFrontier(double riskFreeRate, double tangentPfExcessReturn, Matrix A) {

        List<EfficientFrontierPoint> efficientFrontierList = new ArrayList<>();


        double upperLimit = 2* (tangentPfExcessReturn + riskFreeRate);
        int intervals = 100; // Number of intervals for the efficient frontier

         double a = A.getElement(0, 0);
         double b = A.getElement(0, 1);
         double c = A.getElement(1, 1);


        for (int i = 0; i <= intervals; i++) {
            // Calculate the target return for the current interval
            double targetReturn = riskFreeRate + i * (upperLimit - riskFreeRate) / intervals;

            // Compute the variance associated with the target return
            double targetVariance = getVariance(a, b, c, targetReturn);
            double targetStdDeviation = Math.sqrt(targetVariance);

            // Create and populate an EfficientFrontier object
            EfficientFrontierPoint efficientFrontierPoint = new EfficientFrontierPoint();
            efficientFrontierPoint.setExpectedReturn(targetReturn);
            efficientFrontierPoint.setStdDeviation(targetStdDeviation);

            // Add the efficient frontier point to the list
            efficientFrontierList.add(efficientFrontierPoint);
        }

        return efficientFrontierList;
    }

    public double getVariance(double a, double b, double c, double rateOfReturn) {
        return (a - 2*b*rateOfReturn + c*rateOfReturn*rateOfReturn) / (a*c - b*b);
    }

    public Matrix getAMatrix(Matrix expectedReturns, Matrix covMatrixInverse) {
        Matrix returns_Ones = new Matrix(expectedReturns.getRows(), 2);
        for (int i = 0; i < returns_Ones.getRows(); i++) {
            returns_Ones.setElement(i, 0, expectedReturns.getElement(i, 0));
            returns_Ones.setElement(i, 1, 1.0);
        }

        Matrix returnsOnesTranspose = returns_Ones.transpose();

        // A is a 2x2 matrix
        // a b
        // b c

        // double a = A.getElement(0, 0);
        // double b = A.getElement(0, 1);
        // double c = A.getElement(1, 1);
        return MatrixUtils.multiply(returnsOnesTranspose, MatrixUtils.multiply(covMatrixInverse, returns_Ones));
    }

    private CapitalMarketLine getCapitalMarketLine(Matrix excessReturnsTranspose, Matrix covarianceMatrixInverse, Matrix excessReturns, double riskFreeRate){
        double cmlSlope = Math.sqrt(MatrixUtils.multiply(excessReturnsTranspose, MatrixUtils.multiply(covarianceMatrixInverse, excessReturns)).getElement(0, 0));
        CapitalMarketLine capitalMarketLine = new CapitalMarketLine();
        capitalMarketLine.setIntercept(riskFreeRate);
        capitalMarketLine.setSlope(cmlSlope);
        return capitalMarketLine;
    }
}
