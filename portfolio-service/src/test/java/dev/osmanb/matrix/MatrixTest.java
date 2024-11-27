package dev.osmanb.matrix;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class MatrixTest {

    @Test
    public void testMatrixInitializationFrom2DArray() {
        // Arrange
        double[][] data = {
                {1.0, 2.0},
                {3.0, 4.0}
        };

        // Act
        Matrix matrix = new Matrix(data);

        // Assert
        assertEquals(2, matrix.getRows(), "Rows should be 2");
        assertEquals(2, matrix.getCols(), "Cols should be 2");
        assertEquals(1.0, matrix.getElement(0, 0), "Element at (0,0) should be 1.0");
        assertEquals(4.0, matrix.getElement(1, 1), "Element at (1,1) should be 4.0");
    }

    @Test
    public void testMatrixInitializationFrom1DArray() {
        // Arrange
        double[] data = {1.0, 2.0, 3.0};

        // Act
        Matrix matrix = new Matrix(data);

        // Assert
        assertEquals(3, matrix.getRows(), "Rows should be 3");
        assertEquals(1, matrix.getCols(), "Cols should be 1");
        assertEquals(1.0, matrix.getElement(0, 0), "Element at (0,0) should be 1.0");
        assertEquals(3.0, matrix.getElement(2, 0), "Element at (2,0) should be 3.0");
    }

    @Test
    public void testMatrixTranspose() {
        // Arrange
        double[][] data = {
                {1.0, 2.0},
                {3.0, 4.0}
        };

        // Act
        Matrix matrix = new Matrix(data);
        Matrix transposed = matrix.transpose();

        // Assert
        assertEquals(2, transposed.getRows(), "Transposed matrix should have 2 rows");
        assertEquals(2, transposed.getCols(), "Transposed matrix should have 2 cols");
        assertEquals(1.0, transposed.getElement(0, 0), "Element at (0,0) should be 1.0 after transpose");
        assertEquals(2.0, transposed.getElement(1, 0), "Element at (1,0) should be 2.0 after transpose");
        assertEquals(3.0, transposed.getElement(0, 1), "Element at (0,1) should be 3.0 after transpose");
        assertEquals(4.0, transposed.getElement(1, 1), "Element at (1,1) should be 4.0 after transpose");
    }

    @Test
    public void testMatrixInverse() {
        // Arrange
        double[][] data = {
                {4.0, 7.0},
                {2.0, 6.0}
        };

        // Act
        Matrix matrix = new Matrix(data);
        Matrix inverse = matrix.inverse();

        // Assert
        assertEquals(0.6, inverse.getElement(0, 0), 1e-10, "Element at (0,0) of inverse should be 0.6");
        assertEquals(-0.7, inverse.getElement(0, 1), 1e-10, "Element at (0,1) of inverse should be -0.7");
        assertEquals(-0.2, inverse.getElement(1, 0), 1e-10, "Element at (1,0) of inverse should be -0.2");
        assertEquals(0.4, inverse.getElement(1, 1), 1e-10, "Element at (1,1) of inverse should be 0.4");
    }

    @Test
    public void testMatrixInverseThrowsOnNonSquareMatrix() {
        // Arrange
        double[][] data = {
                {1.0, 2.0},
                {3.0, 4.0},
                {5.0, 6.0}
        };

        // Act & Assert
        Matrix matrix = new Matrix(data);
        assertThrows(IllegalArgumentException.class, matrix::inverse, "Matrix must be square for inversion.");
    }

    @Test
    public void testMatrixInverseThrowsOnSingularMatrix() {
        // Arrange
        double[][] data = {
                {1.0, 2.0},
                {2.0, 4.0}
        };

        // Act & Assert
        Matrix matrix = new Matrix(data);
        assertThrows(IllegalArgumentException.class, matrix::inverse, "Matrix is singular or nearly singular.");
    }

    @Test
    public void testMatrixInitializationThrowsOnInvalidData() {
        assertThrows(IllegalArgumentException.class, () -> new Matrix((double[][]) null), "Matrix data cannot be null");
        assertThrows(IllegalArgumentException.class, () -> new Matrix(new double[0][0]), "Matrix data cannot be empty");
    }

    @Test
    public void testMatrixInitializationThrowsOnJaggedArray() {
        // Arrange
        double[][] data = {
                {1.0, 2.0},
                {3.0}
        };

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> new Matrix(data), "All rows must have the same number of columns");
    }

    @Test
    public void testMatrixToString() {
        // Arrange
        double[][] data = {
                {1.0, 2.0},
                {3.0, 4.0}
        };

        // Act & Assert
        Matrix matrix = new Matrix(data);
        String expected = "[1.0, 2.0]\n[3.0, 4.0]\n";
        assertEquals(expected, matrix.toString(), "Matrix toString() output should match expected string");
    }
}
