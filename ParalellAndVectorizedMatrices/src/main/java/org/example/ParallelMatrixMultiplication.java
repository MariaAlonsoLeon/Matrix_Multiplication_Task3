package org.example;

public interface ParallelMatrixMultiplication {
    double[][] multiply(double[][] A, double[][] B, int threads) throws InterruptedException;
}
