package org.example;

public class OptimizedVectorizedMatrixMultiplication implements MatrixMultiplication{

    @Override
    public double[][] multiply(double[][] matrixA, double[][] matrixB) {
        int rowsA = matrixA.length;
        int colsA = matrixA[0].length;
        int rowsB = matrixB.length;
        int colsB = matrixB[0].length;

        if (colsA != rowsB) {
            throw new IllegalArgumentException("The number of columns in matrix A must be equal to the number of rows in matrix B.");
        }

        double[][] result = new double[rowsA][colsB];

        for (int i = 0; i < rowsA; i++) {
            for (int j = 0; j < colsB; j++) {
                result[i][j] = vectorizedDotProduct(matrixA[i], getColumn(matrixB, j));
            }
        }

        return result;
    }

    private static double vectorizedDotProduct(double[] row, double[] column) {
        double sum = 0;
        for (int k = 0; k < row.length; k++) {
            sum += row[k] * column[k];
        }
        return sum;
    }

    private static double[] getColumn(double[][] matrix, int colIndex) {
        double[] column = new double[matrix.length];
        for (int i = 0; i < matrix.length; i++) {
            column[i] = matrix[i][colIndex];
        }
        return column;
    }
}
