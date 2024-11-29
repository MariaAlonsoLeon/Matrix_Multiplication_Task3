import json
import numpy as np

from Algorithms.AtomicMatrixMultiplication import AtomicMatrixMultiplication
from Algorithms.BasicMatrixMuliplication import BasicMatrixMultiplication
from Algorithms.CudaMatrixMultiplication import CudaMatrixMultiplication
from Algorithms.OptimizedVectorizedMatrixMultiplication import OptimizedVectorizedMatrixMultiplication
from Algorithms.StreamMatrixMultiplication import StreamMatrixMultiplication
from Algorithms.VectorizedMatrixMultiplicationSIMD import VectorizedMatrixMultiplicationSIMD
from Tests.benchmarks.utils import gather_system_info


class SequentialMatrixBenchmark:
    def __init__(self, matrix_sizes):
        self.matrix_sizes = matrix_sizes
        self.algorithms = {
            "BasicMatrixMultiplication": BasicMatrixMultiplication(),
            "VectorizedMatrixMultiplicationSIMD": VectorizedMatrixMultiplicationSIMD(),
            "OptimizedVectorizedMatrixMultiplication": OptimizedVectorizedMatrixMultiplication(),
            "CudaMatrixMultiplication": CudaMatrixMultiplication(),
            "StreamMatrixMultiplication": StreamMatrixMultiplication(),
            "AtomicMatrixMultiplication": AtomicMatrixMultiplication()
        }

    def run_benchmarks(self):
        results = []

        for size in self.matrix_sizes:
            print(f"Matrix Size: {size}")
            for name, algorithm in self.algorithms.items():
                print(f"Algorithm: {name}")

                A = np.random.rand(size, size).astype(np.float32)
                B = np.random.rand(size, size).astype(np.float32)

                system_info = gather_system_info()

                exec_time = self.benchmark_algorithm(algorithm, A, B)

                results.append({
                    "algorithm": name,
                    "matrix_size": size,
                    "execution_time_ms": exec_time,
                    "system_info": system_info
                })

        self.save_results_to_json("sequential_benchmark_detailed_results.json", results)

    def benchmark_algorithm(self, algorithm, A, B):
        import time
        start_time = time.perf_counter()
        algorithm.multiply(A, B)
        end_time = time.perf_counter()
        return (end_time - start_time) * 1000

    @staticmethod
    def save_results_to_json(filepath, results):
        with open(filepath, "w") as file:
            json.dump(results, file, indent=4)
        print(f"Benchmark results saved to {filepath}")

# Configuración principal
if __name__ == "__main__":
    MATRIX_SIZES = [64, 128, 256, 512, 1024]

    benchmark = SequentialMatrixBenchmark(MATRIX_SIZES)
    benchmark.run_benchmarks()
