package resources_tests;

import org.example.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SequentialMatrixResourceTest {

    public static void main(String[] args) {
        int[] sizes = {64, 128, 256, 512, 1024};

        String[] algorithms = {
                "BasicMatrixMultiplication",
                "AtomicMatrixMultiplication",
                "VectorizedMatrixMultiplication",
                "OptimizedVectorizedMatrixMultiplication",
                "StreamMatrixMultiplication",
                "VectorizedSIMDMatrixMultiplication"
        };

        List<Map<String, Object>> results = new ArrayList<>();

        for (String algorithm : algorithms) {
            System.out.printf("Algorithm: %s\n", algorithm);
            for (int size : sizes) {
                System.out.printf("\t- Size: %d\n", size);
                double[][] matrixA = generateRandomMatrix(size, size);
                double[][] matrixB = generateRandomMatrix(size, size);

                Map<String, Object> metricsBefore = ResourceMonitor.monitorResources();

                double[][] result = executeSequentialAlgorithm(algorithm, matrixA, matrixB);

                Map<String, Object> metricsAfter = ResourceMonitor.monitorResources();

                Map<String, Object> combinedMetrics = new HashMap<>();
                combinedMetrics.put("Before", metricsBefore);
                combinedMetrics.put("After", metricsAfter);
                combinedMetrics.put("Algorithm", algorithm);
                combinedMetrics.put("MatrixSize", size);

                results.add(combinedMetrics);
            }
        }

        ResourceMonitor.saveMetricsToJson(results, "sequential_results.json");
    }

    private static double[][] executeSequentialAlgorithm(String algorithm, double[][] matrixA, double[][] matrixB) {
        MatrixMultiplication multiplicationAlgorithm = getAlgorithmInstance(algorithm);
        
        return multiplicationAlgorithm.multiply(matrixA, matrixB);
    }

    private static MatrixMultiplication getAlgorithmInstance(String algorithm) {
        switch (algorithm) {
            case "BasicMatrixMultiplication":
                return new BasicMatrixMultiplication();
            case "AtomicMatrixMultiplication":
                return new AtomicMatrixMultiplication();
            case "VectorizedMatrixMultiplication":
                return new VectorizedMatrixMultiplication();
            case "OptimizedVectorizedMatrixMultiplication":
                return new OptimizedVectorizedMatrixMultiplication();
            case "StreamMatrixMultiplication":
                return new StreamMatrixMultiplication();
            case "VectorizedSIMDMatrixMultiplication":
                return new VectorizedMatrixMultiplicationSIMD();
            default:
                throw new IllegalArgumentException("Unsupported algorithm: " + algorithm);
        }
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
