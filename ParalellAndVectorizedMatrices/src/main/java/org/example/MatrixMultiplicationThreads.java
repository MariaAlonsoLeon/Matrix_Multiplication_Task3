package org.example;

public class MatrixMultiplicationThreads implements ParallelMatrixMultiplication {

    @Override
    public double[][] multiply(double[][] matrixA, double[][] matrixB, int numThreads) throws InterruptedException {
        int size = matrixA.length;
        double[][] result = new double[size][size];

        int threadsPerRow = Math.max(1, size / numThreads);
        Thread[] threads = new Thread[numThreads];

        for (int i = 0; i < numThreads; i++) {
            final int startRow = i * threadsPerRow;
            final int endRow = Math.min((i + 1) * threadsPerRow, size);

            threads[i] = new Thread(() -> {
                for (int row = startRow; row < endRow; row++) {
                    multiplyRow(row, matrixA, matrixB, result);
                }
            });
            threads[i].start();
        }

        for (int i = 0; i < numThreads; i++) {
            threads[i].join();
        }

        return result;
    }

    private static void multiplyRow(int row, double[][] matrixA, double[][] matrixB, double[][] result) {
        for (int j = 0; j < matrixB[0].length; j++) {
            for (int k = 0; k < matrixA[0].length; k++) {
                result[row][j] += matrixA[row][k] * matrixB[k][j];
            }
        }
    }

}
