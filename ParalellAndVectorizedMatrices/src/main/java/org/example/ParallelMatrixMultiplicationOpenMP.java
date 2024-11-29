package org.example;

import java.util.concurrent.RecursiveAction;
import java.util.concurrent.ForkJoinPool;

public class ParallelMatrixMultiplicationOpenMP implements ParallelMatrixMultiplication {

    @Override
    public double[][] multiply(double[][] A, double[][] B, int threads) throws InterruptedException {
        int size = A.length;
        int threshold = calculateThreshold(size);

        double[][] C = new double[size][size];

        ForkJoinPool pool = new ForkJoinPool(threads);
        pool.invoke(new MatrixMultiplyTask(A, B, C, 0, size, threshold));

        return C;
    }

    private static int calculateThreshold(int size) {
        return size / 10;
    }

    private static class MatrixMultiplyTask extends RecursiveAction {
        private final double[][] A, B, C;
        private final int startRow, endRow, threshold;

        public MatrixMultiplyTask(double[][] A, double[][] B, double[][] C, int startRow, int endRow, int threshold) {
            this.A = A;
            this.B = B;
            this.C = C;
            this.startRow = startRow;
            this.endRow = endRow;
            this.threshold = threshold;
        }

        @Override
        protected void compute() {
            int rows = endRow - startRow;
            if (rows <= threshold) {
                for (int i = startRow; i < endRow; i++) {
                    for (int j = 0; j < C[0].length; j++) {
                        double sum = 0;
                        for (int k = 0; k < B.length; k++) {
                            sum += A[i][k] * B[k][j];
                        }
                        C[i][j] = sum;
                    }
                }
            } else {
                int midRow = startRow + rows / 2;
                invokeAll(
                        new MatrixMultiplyTask(A, B, C, startRow, midRow, threshold),
                        new MatrixMultiplyTask(A, B, C, midRow, endRow, threshold)
                );
            }
        }
    }
}
