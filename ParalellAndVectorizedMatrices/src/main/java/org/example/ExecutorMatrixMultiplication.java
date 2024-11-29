package org.example;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExecutorMatrixMultiplication implements ParallelMatrixMultiplication{

    @Override
    public double[][] multiply(double[][] A, double[][] B, int threads) throws InterruptedException {
        int rowsA = A.length;
        int colsA = A[0].length;
        int colsB = B[0].length;
        double[][] result = new double[rowsA][colsB];

        ExecutorService executor = Executors.newFixedThreadPool(threads);

        for (int i = 0; i < rowsA; i++) {
            int row = i;
            executor.execute(() -> {
                for (int j = 0; j < colsB; j++) {
                    double sum = 0;
                    for (int k = 0; k < colsA; k++) {
                        sum += A[row][k] * B[k][j];
                    }
                    result[row][j] = sum;
                }
            });
        }

        executor.shutdown();
        while (!executor.isTerminated()) {
            Thread.sleep(10);
        }

        return result;
    }
}
