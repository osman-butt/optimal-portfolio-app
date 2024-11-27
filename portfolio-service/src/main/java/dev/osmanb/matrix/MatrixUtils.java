package dev.osmanb.matrix;

public class MatrixUtils {

    public static Matrix multiply(Matrix matrix1, Matrix matrix2) {
        if (matrix1.getCols() != matrix2.getRows()) {
            throw new IllegalArgumentException("Incompatible dimensions for matrix multiplication.");
        }

        int rows = matrix1.getRows();
        int cols = matrix2.getCols();
        int common = matrix1.getCols();
        double[][] result = new double[rows][cols];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                double sum = 0.0;
                for (int k = 0; k < common; k++) {
                    sum += matrix1.getElement(i, k) * matrix2.getElement(k, j);
                }
                result[i][j] = sum;
            }
        }

        return new Matrix(result);
    }
}
