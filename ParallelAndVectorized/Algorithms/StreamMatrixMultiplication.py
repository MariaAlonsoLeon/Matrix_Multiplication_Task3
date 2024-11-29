import numpy as np
import concurrent.futures


class StreamMatrixMultiplication:

    def multiply(self, A, B):
        rows_A, cols_A = A.shape
        rows_B, cols_B = B.shape

        if cols_A != rows_B:
            raise ValueError("The number of columns in matrix A must be equal to the number of rows in matrix B.")

        result = np.zeros((rows_A, cols_B), dtype=np.float32)

        with concurrent.futures.ThreadPoolExecutor() as executor:
            futures = [executor.submit(self._multiply_row, i, A, B, result) for i in range(rows_A)]

            for future in concurrent.futures.as_completed(futures):
                future.result()

        return result

    def _multiply_row(self, i, A, B, result):
        cols_A = A.shape[1]
        cols_B = B.shape[1]

        for j in range(cols_B):
            sum_ = 0
            for k in range(cols_A):
                sum_ += A[i, k] * B[k, j]
            result[i, j] = sum_
