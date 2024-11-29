import json
import numpy as np

from Algorithms.ExecutorMatrixMultiplication import ExecutorMatrixMultiplication
from Algorithms.FixedThreadsMatrixMultiplication import FixedThreadsMatrixMultiplication
from Algorithms.MatrixMultiplicationThreads import MatrixMultiplicationThreads
from Algorithms.ParallelMatrixMultiplicationOpenMP import ParallelMatrixMultiplicationOpenMP
from Algorithms.SynchronizedMatrixMultiplication import SynchronizedMatrixMultiplication
from Tests.benchmarks.utils import gather_system_info


class ParallelMatrixBenchmark:
    def __init__(self, matrix_sizes, thread_counts):
        self.matrix_sizes = matrix_sizes
        self.thread_counts = thread_counts
        self.algorithms = {
            "FixedThreadsMatrixMultiplication": FixedThreadsMatrixMultiplication(),
            "MatrixMultiplicationThreads": MatrixMultiplicationThreads(),
            "SychorizedMatrixMultiplication": SynchronizedMatrixMultiplication(),
            "ParallelMatrixMultiplicationOpenMP": ParallelMatrixMultiplicationOpenMP(),
            "ExecutorMatrixMultiplication": ExecutorMatrixMultiplication()
        }

    def run_benchmarks(self):
        results = []

        for size in self.matrix_sizes:
            print("Size: " + str(size))
            for threads in self.thread_counts:
                print("Threads: " + str(threads))
                for name, algorithm in self.algorithms.items():
                    print("Algorithm: " + name)
                    A = np.random.rand(size, size).astype(np.float32)
                    B = np.random.rand(size, size).astype(np.float32)

                    system_info = gather_system_info()

                    exec_time = self.benchmark_algorithm(algorithm, A, B, threads)

                    results.append({
                        "algorithm": name,
                        "matrix_size": size,
                        "threads": threads,
                        "execution_time_ms": exec_time,
                        "system_info": system_info
                    })

        self.save_results_to_json("parallel_benchmark_detailed_results.json", results)

    def benchmark_algorithm(self, algorithm, A, B, threads):
        import time
        start_time = time.perf_counter()
        algorithm.multiply(A, B, threads)
        end_time = time.perf_counter()
        return (end_time - start_time) * 1000

    @staticmethod
    def save_results_to_json(filepath, results):
        with open(filepath, "w") as file:
            json.dump(results, file, indent=4)
        print(f"Benchmark results saved to {filepath}")

if __name__ == "__main__":
    MATRIX_SIZES = [64, 128, 256, 512, 1024]
    THREAD_COUNTS = [2, 4, 8, 16]

    benchmark = ParallelMatrixBenchmark(MATRIX_SIZES, THREAD_COUNTS)
    benchmark.run_benchmarks()
