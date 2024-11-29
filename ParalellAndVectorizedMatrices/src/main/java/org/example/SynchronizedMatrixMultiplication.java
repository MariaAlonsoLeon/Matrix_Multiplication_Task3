package org.example;

import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

public class SynchronizedMatrixMultiplication implements ParallelMatrixMultiplication {

    @Override
    public double[][] multiply(double[][] A, double[][] B, int threads) throws InterruptedException {
        int rowsA = A.length;
        int colsA = A[0].length;
        int colsB = B[0].length;
        double[][] result = new double[rowsA][colsB];

        Semaphore semaphore = new Semaphore(threads);
        AtomicInteger rowCounter = new AtomicInteger(0);

        Runnable task = () -> {
            try {
                while (true) {
                    int row = rowCounter.getAndIncrement();
                    if (row >= rowsA) break;

                    for (int j = 0; j < colsB; j++) {
                        double sum = 0;
                        for (int k = 0; k < colsA; k++) {
                            sum += A[row][k] * B[k][j];
                        }
                        result[row][j] = sum;
                    }
                }
            } finally {
                semaphore.release();
            }
        };

        Thread[] threadPool = new Thread[threads];
        for (int i = 0; i < threads; i++) {
            semaphore.acquire();
            threadPool[i] = new Thread(task);
            threadPool[i].start();
        }

        for (Thread t : threadPool) {
            t.join();
        }

        return result;
    }
}
