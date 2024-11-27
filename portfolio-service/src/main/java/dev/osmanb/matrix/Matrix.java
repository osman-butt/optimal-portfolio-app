package dev.osmanb.matrix;

import java.util.Arrays;

public class Matrix {
    private final double[][] data;

    public Matrix(int rows, int cols) {
        data = new double[rows][cols];
    }

    public Matrix(double[][] data) {
        if (data == null || data.length == 0 || data[0].length == 0) {
            throw new IllegalArgumentException("Input data cannot be null or empty");
        }

        int cols = data[0].length;
        for (double[] row : data) {
            if (row.length != cols) {
                throw new IllegalArgumentException("All rows must have the same number of columns");
            }
        }

        this.data = new double[data.length][cols];
        for (int i = 0; i < data.length; i++) {
            System.arraycopy(data[i], 0, this.data[i], 0, data[i].length);
        }
    }

    public Matrix(double[] data) {
        this.data = new double[data.length][1];
        for (int i = 0; i < data.length; i++) {
            this.data[i][0] = data[i];
        }
    }

    public int getRows() {
        return data.length;
    }

    public int getCols() {
        return data[0].length;
    }

    public double getElement(int row, int col) {
        return data[row][col];
    }

    public void setElement(int row, int col, double value) {
        data[row][col] = value;
    }

    public double[][] getData() {
        double[][] copy = new double[data.length][data[0].length];
        for (int i = 0; i < data.length; i++) {
            System.arraycopy(data[i], 0, copy[i], 0, data[i].length);
        }
        return copy;
    }

    public Matrix transpose() {
        int rows = data.length;
        int cols = data[0].length;
        double[][] result = new double[cols][rows];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                result[j][i] = data[i][j];
            }
        }
        return new Matrix(result);
    }

    public Matrix inverse() {
        int n = getRows();
        if (getRows() != getCols()) {
            throw new IllegalArgumentException("Matrix must be square for inversion.");
        }

        // Augment the matrix with the identity matrix
        double[][] augmented = new double[n][2 * n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                augmented[i][j] = data[i][j]; // Original matrix
            }
            augmented[i][n + i] = 1.0; // Identity matrix
        }

        // Perform Gaussian elimination
        for (int i = 0; i < n; i++) {
            // Find pivot
            double pivot = augmented[i][i];
            if (Math.abs(pivot) < 1e-10) { // Tolerance for floating-point comparisons
                throw new IllegalArgumentException("Matrix is singular or nearly singular.");
            }

            // Normalize the pivot row
            for (int j = 0; j < 2 * n; j++) {
                augmented[i][j] /= pivot;
            }

            // Eliminate other rows
            for (int k = 0; k < n; k++) {
                if (k == i) continue; // Skip the pivot row
                double factor = augmented[k][i];
                for (int j = 0; j < 2 * n; j++) {
                    augmented[k][j] -= factor * augmented[i][j];
                }
            }
        }

        // Extract the inverse from the augmented matrix
        double[][] inverse = new double[n][n];
        for (int i = 0; i < n; i++) {
            System.arraycopy(augmented[i], n, inverse[i], 0, n);
        }

        return new Matrix(inverse);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (double[] row : data) {
            sb.append(Arrays.toString(row)).append("\n");
        }
        return sb.toString();
    }
}

