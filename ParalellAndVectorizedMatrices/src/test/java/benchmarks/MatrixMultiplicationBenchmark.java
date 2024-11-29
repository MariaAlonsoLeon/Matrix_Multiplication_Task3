package benchmarks;

import org.example.*;
import org.openjdk.jmh.annotations.*;
import java.util.concurrent.TimeUnit;

@BenchmarkMode({Mode.AverageTime})
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Thread)
public class MatrixMultiplicationBenchmark {

    @Param({"64", "128", "256", "512", "1024"})
    public int size;

    private double[][] matrixA;
    private double[][] matrixB;

    private MatrixMultiplication basicMatrixMultiplication;
    private MatrixMultiplication atomicMatrixMultiplication;
    private MatrixMultiplication vectorizedMatrixMultiplication;
    private MatrixMultiplication optimizedVectorizedMatrixMultiplication;
    private MatrixMultiplication streamMatrixMultiplication;
    private MatrixMultiplication vectorizedSIMDMatrixMultiplication;

    @Setup(Level.Trial)
    public void setup() {
        matrixA = new double[size][size];
        matrixB = new double[size][size];

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                matrixA[i][j] = Math.random();
                matrixB[i][j] = Math.random();
            }
        }

        basicMatrixMultiplication = new BasicMatrixMultiplication();
        atomicMatrixMultiplication = new AtomicMatrixMultiplication();
        vectorizedMatrixMultiplication = new VectorizedMatrixMultiplication();
        optimizedVectorizedMatrixMultiplication = new OptimizedVectorizedMatrixMultiplication();
        streamMatrixMultiplication = new StreamMatrixMultiplication();
        vectorizedSIMDMatrixMultiplication = new VectorizedMatrixMultiplicationSIMD();
    }

    @Benchmark
    public double[][] basicMatrixMultiplication() {
        return basicMatrixMultiplication.multiply(matrixA, matrixB);
    }

    @Benchmark
    public double[][] atomicMatrixMultiplication() {
        return atomicMatrixMultiplication.multiply(matrixA, matrixB);
    }

    @Benchmark
    public double[][] vectorizedMatrixMultiplication() {
        return vectorizedMatrixMultiplication.multiply(matrixA, matrixB);
    }

    @Benchmark
    public double[][] optimizedVectorizedMatrixMultiplication() {
        return optimizedVectorizedMatrixMultiplication.multiply(matrixA, matrixB);
    }

    @Benchmark
    public double[][] streamMatrixMultiplication() {
        return streamMatrixMultiplication.multiply(matrixA, matrixB);
    }


    @Benchmark
    public double[][] vectorizedSIMDMatrixMultiplication() {
        return vectorizedSIMDMatrixMultiplication.multiply(matrixA, matrixB);
    }
}
