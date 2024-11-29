package benchmarks;

import org.example.*;
import org.openjdk.jmh.annotations.*;
import java.util.concurrent.TimeUnit;

@BenchmarkMode({Mode.AverageTime})
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Thread)
public class ParallelMatrixMultiplicationBenchmark {

    @Param({"64", "128", "256", "512", "1024"})
    public int size;

    @Param({"2", "4", "8", "16"})
    public int numThreads;

    private double[][] matrixA;
    private double[][] matrixB;

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
    }

    @Benchmark
    public double[][] fixedThreadsMatrixMultiplication() throws InterruptedException {
        ParallelMatrixMultiplication fixedThreads = new FixedThreadsMatrixMultiplication();
        return fixedThreads.multiply(matrixA, matrixB, numThreads);
    }

    @Benchmark
    public double[][] matrixMultiplicationThreads() throws InterruptedException {
        ParallelMatrixMultiplication threads = new MatrixMultiplicationThreads();
        return threads.multiply(matrixA, matrixB, numThreads);
    }

    @Benchmark
    public double[][] synchronizedMatrixMultiplication() throws InterruptedException {
        ParallelMatrixMultiplication synchronizedMultiplication = new SynchronizedMatrixMultiplication();
        return synchronizedMultiplication.multiply(matrixA, matrixB, numThreads);
    }

    @Benchmark
    public double[][] executorMatrixMultiplication() throws InterruptedException {
        ParallelMatrixMultiplication executorMultiplication = new ExecutorMatrixMultiplication();
        return executorMultiplication.multiply(matrixA, matrixB, numThreads);
    }

    @Benchmark
    public double[][] parallelOpenMPMatrixMultiplication() throws InterruptedException {
        ParallelMatrixMultiplication openMP = new ParallelMatrixMultiplicationOpenMP();
        return openMP.multiply(matrixA, matrixB, numThreads);
    }
}
