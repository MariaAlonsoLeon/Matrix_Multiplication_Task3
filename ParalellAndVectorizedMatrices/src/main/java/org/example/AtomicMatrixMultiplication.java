package org.example;

import java.util.concurrent.atomic.AtomicInteger;

public class AtomicMatrixMultiplication implements MatrixMultiplication {

    @Override
    public double[][] multiply(double[][] matrixA, double[][] matrixB) {
        int size = matrixA.length;
        double[][] result = new double[size][size];

        AtomicInteger row = new AtomicInteger(0);

        while (row.get() < size) {
            int r = row.getAndIncrement();
            if (r < size) {
                for (int c = 0; c < size; c++) {
                    for (int k = 0; k < size; k++) {
                        result[r][c] += matrixA[r][k] * matrixB[k][c];
                    }
                }
            }
        }

        return result;
    }
}
