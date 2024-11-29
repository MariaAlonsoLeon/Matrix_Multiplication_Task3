from concurrent.futures import ThreadPoolExecutor
import numpy as np

class FixedThreadsMatrixMultiplication:
    def multiply(self, matrixA, matrixB, num_threads):
        size = len(matrixA)
        result = [[0.0 for _ in range(size)] for _ in range(size)]

        def compute_row(row):
            for j in range(size):
                for k in range(size):
                    result[row][j] += matrixA[row][k] * matrixB[k][j]

        with ThreadPoolExecutor(max_workers=num_threads) as executor:
            executor.map(compute_row, range(size))

        return np.array(result)
