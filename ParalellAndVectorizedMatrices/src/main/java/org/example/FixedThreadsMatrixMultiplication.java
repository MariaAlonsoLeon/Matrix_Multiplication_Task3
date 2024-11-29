package org.example;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class FixedThreadsMatrixMultiplication implements ParallelMatrixMultiplication{

    @Override
    public double[][] multiply(double[][] matrixA, double[][] matrixB, int numThreads) throws InterruptedException {
        int size = matrixA.length;
        double[][] result = new double[size][size];

        ExecutorService executorService = Executors.newFixedThreadPool(numThreads);

        for (int i = 0; i < size; i++) {
            final int row = i;
            executorService.submit(() -> {
                for (int j = 0; j < size; j++) {
                    for (int k = 0; k < size; k++) {
                        result[row][j] += matrixA[row][k] * matrixB[k][j];
                    }
                }
            });
        }

        executorService.shutdown();
        executorService.awaitTermination(1, TimeUnit.HOURS);

        return result;
    }
}
