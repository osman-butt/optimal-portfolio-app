package dev.osmanb.matrix;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class MatrixUtilsTest {

    @Test
    public void testMatrixMultiplication() {
        // Matrix 1 (2x3)
        double[][] data1 = {
                {1.0, 2.0, 3.0},
                {4.0, 5.0, 6.0}
        };
        Matrix matrix1 = new Matrix(data1);

        // Matrix 2 (3x2)
        double[][] data2 = {
                {7.0, 8.0},
                {9.0, 10.0},
                {11.0, 12.0}
        };
        Matrix matrix2 = new Matrix(data2);

        // Expected result after multiplication
        double[][] expectedData = {
                {58.0, 64.0},
                {139.0, 154.0}
        };
        Matrix expectedMatrix = new Matrix(expectedData);

        // Perform multiplication using MatrixUtils
        Matrix result = MatrixUtils.multiply(matrix1, matrix2);

        // Compare the result matrix with the expected matrix
        assertEquals(expectedMatrix.getRows(), result.getRows(), "Number of rows should match");
        assertEquals(expectedMatrix.getCols(), result.getCols(), "Number of columns should match");

        for (int i = 0; i < result.getRows(); i++) {
            for (int j = 0; j < result.getCols(); j++) {
                assertEquals(expectedMatrix.getElement(i, j), result.getElement(i, j), 1e-10,
                        "Element at (" + i + "," + j + ") should match the expected value");
            }
        }
    }

    @Test
    public void testMatrixMultiplicationIncompatibleDimensions() {
        // Matrix 1 (2x3)
        double[][] data1 = {
                {1.0, 2.0, 3.0},
                {4.0, 5.0, 6.0}
        };
        Matrix matrix1 = new Matrix(data1);

        // Matrix 2 (2x2), incompatible for multiplication with Matrix 1
        double[][] data2 = {
                {7.0, 8.0},
                {9.0, 10.0}
        };
        Matrix matrix2 = new Matrix(data2);

        // Matrix multiplication should throw an IllegalArgumentException
        assertThrows(IllegalArgumentException.class, () -> MatrixUtils.multiply(matrix1, matrix2),
                "Incompatible dimensions for matrix multiplication.");
    }

    @Test
    public void testMatrixMultiplicationIdentityMatrix() {
        // Matrix 1 (2x2)
        double[][] data1 = {
                {1.0, 2.0},
                {3.0, 4.0}
        };
        Matrix matrix1 = new Matrix(data1);

        // Identity Matrix (2x2)
        double[][] data2 = {
                {1.0, 0.0},
                {0.0, 1.0}
        };
        Matrix matrix2 = new Matrix(data2);

        // Expected result is the same as matrix1
        Matrix expectedMatrix = matrix1;

        // Perform multiplication
        Matrix result = MatrixUtils.multiply(matrix1, matrix2);

        // Check if result equals the original matrix
        assertEquals(expectedMatrix.getRows(), result.getRows(), "Number of rows should match");
        assertEquals(expectedMatrix.getCols(), result.getCols(), "Number of columns should match");

        for (int i = 0; i < result.getRows(); i++) {
            for (int j = 0; j < result.getCols(); j++) {
                assertEquals(expectedMatrix.getElement(i, j), result.getElement(i, j), 1e-10,
                        "Element at (" + i + "," + j + ") should match the expected value");
            }
        }
    }

    @Test
    public void testMatrixMultiplicationWithZeroMatrix() {
        // Matrix 1 (2x3)
        double[][] data1 = {
                {1.0, 2.0, 3.0},
                {4.0, 5.0, 6.0}
        };
        Matrix matrix1 = new Matrix(data1);

        // Zero Matrix (3x2)
        double[][] data2 = {
                {0.0, 0.0},
                {0.0, 0.0},
                {0.0, 0.0}
        };
        Matrix matrix2 = new Matrix(data2);

        // Expected result is a zero matrix (2x2)
        double[][] expectedData = {
                {0.0, 0.0},
                {0.0, 0.0}
        };
        Matrix expectedMatrix = new Matrix(expectedData);

        // Perform multiplication
        Matrix result = MatrixUtils.multiply(matrix1, matrix2);

        // Check if the result matches the expected zero matrix
        assertEquals(expectedMatrix.getRows(), result.getRows(), "Number of rows should match");
        assertEquals(expectedMatrix.getCols(), result.getCols(), "Number of columns should match");

        for (int i = 0; i < result.getRows(); i++) {
            for (int j = 0; j < result.getCols(); j++) {
                assertEquals(expectedMatrix.getElement(i, j), result.getElement(i, j), 1e-10,
                        "Element at (" + i + "," + j + ") should match the expected value");
            }
        }
    }

    @Test
    public void testMatrixMultiplicationIdentityMatrixRightSide() {
        // Matrix 1 (2x2)
        double[][] data1 = {
                {1.0, 2.0},
                {3.0, 4.0}
        };
        Matrix matrix1 = new Matrix(data1);

        // Identity Matrix (2x2)
        double[][] data2 = {
                {1.0, 0.0},
                {0.0, 1.0}
        };
        Matrix matrix2 = new Matrix(data2);

        // Expected result is the same as matrix1
        Matrix expectedMatrix = matrix1;

        // Perform multiplication (right side)
        Matrix result = MatrixUtils.multiply(matrix2, matrix1);

        // Check if result equals the original matrix
        assertEquals(expectedMatrix.getRows(), result.getRows(), "Number of rows should match");
        assertEquals(expectedMatrix.getCols(), result.getCols(), "Number of columns should match");

        for (int i = 0; i < result.getRows(); i++) {
            for (int j = 0; j < result.getCols(); j++) {
                assertEquals(expectedMatrix.getElement(i, j), result.getElement(i, j), 1e-10,
                        "Element at (" + i + "," + j + ") should match the expected value");
            }
        }
    }
}