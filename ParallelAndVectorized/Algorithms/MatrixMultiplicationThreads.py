import threading
import numpy as np

class MatrixMultiplicationThreads:
    def multiply(self, matrixA, matrixB, num_threads):
        size = len(matrixA)
        result = [[0.0 for _ in range(size)] for _ in range(size)]

        threads_per_row = max(1, size // num_threads)
        threads = []

        def multiply_rows(start_row, end_row):
            for row in range(start_row, end_row):
                self._multiply_row(row, matrixA, matrixB, result)

        for i in range(num_threads):
            start_row = i * threads_per_row
            end_row = min((i + 1) * threads_per_row, size)
            thread = threading.Thread(target=multiply_rows, args=(start_row, end_row))
            threads.append(thread)
            thread.start()

        for thread in threads:
            thread.join()

        return np.array(result)

    @staticmethod
    def _multiply_row(row, matrixA, matrixB, result):
        for j in range(len(matrixB[0])):
            for k in range(len(matrixA[0])):
                result[row][j] += matrixA[row][k] * matrixB[k][j]
