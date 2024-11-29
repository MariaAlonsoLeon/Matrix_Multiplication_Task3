package resources_tests;

import org.example.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ParallelMatrixResourceTest {

    public static void main(String[] args) throws InterruptedException {
        int[] sizes = {64, 128, 256, 512, 1024};
        int[] threads = {2, 4, 8, 16};

        String[] algorithms = {
                "FixedThreadsMatrixMultiplication",
                "MatrixMultiplicationThreads",
                "SynchronizedMatrixMultiplication",
                "ExecutorMatrixMultiplication",
                "ParallelOpenMPMatrixMultiplication"
        };

        List<Map<String, Object>> results = new ArrayList<>();

        for (String algorithm : algorithms) {
            System.out.println("Algorithm: " + algorithm);
            for (int size : sizes) {
                System.out.println("\t-Size: " + size);
                for (int numThreads : threads) {
                    System.out.println("\t\t-NumThreads: " + numThreads);
                    double[][] matrixA = generateRandomMatrix(size, size);
                    double[][] matrixB = generateRandomMatrix(size, size);

                    Map<String, Object> metricsBefore = ResourceMonitor.monitorResources();

                    double[][] result = executeParallelAlgorithm(algorithm, matrixA, matrixB, numThreads);

                    Map<String, Object> metricsAfter = ResourceMonitor.monitorResources();

                    Map<String, Object> combinedMetrics = new HashMap<>();
                    combinedMetrics.put("Before", metricsBefore);
                    combinedMetrics.put("After", metricsAfter);
                    combinedMetrics.put("Algorithm", algorithm);
                    combinedMetrics.put("MatrixSize", size);
                    combinedMetrics.put("Threads", numThreads);

                    results.add(combinedMetrics);
                }
            }
        }

        ResourceMonitor.saveMetricsToJson(results, "parallel_results.json");
    }

    private static double[][] executeParallelAlgorithm(String algorithm, double[][] matrixA, double[][] matrixB, int numThreads) throws InterruptedException {
        ParallelMatrixMultiplication matrixMultiplication;

        switch (algorithm) {
            case "FixedThreadsMatrixMultiplication":
                matrixMultiplication = new FixedThreadsMatrixMultiplication();
                break;
            case "MatrixMultiplicationThreads":
                matrixMultiplication = new MatrixMultiplicationThreads();
                break;
            case "SynchronizedMatrixMultiplication":
                matrixMultiplication = new SynchronizedMatrixMultiplication();
                break;
            case "ExecutorMatrixMultiplication":
                matrixMultiplication = new ExecutorMatrixMultiplication();
                break;
            case "ParallelOpenMPMatrixMultiplication":
                matrixMultiplication = new ParallelMatrixMultiplicationOpenMP();
                break;
            default:
                throw new IllegalArgumentException("Unsupported algorithm: " + algorithm);
        }

        return matrixMultiplication.multiply(matrixA, matrixB, numThreads);
    }

    private static double[][] generateRandomMatrix(int rows, int cols) {
        double[][] matrix = new double[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                matrix[i][j] = Math.random();
            }
        }
        return matrix;
    }
}
