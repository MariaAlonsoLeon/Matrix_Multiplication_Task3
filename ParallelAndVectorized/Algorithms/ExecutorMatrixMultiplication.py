import numpy as np
from concurrent.futures import ThreadPoolExecutor

class ExecutorMatrixMultiplication:
    def multiply(self, A, B, threads):
        A = np.array(A, dtype=np.float32)
        B = np.array(B, dtype=np.float32)

        rowsA, colsA = A.shape
        colsB = B.shape[1]

        result = np.zeros((rowsA, colsB), dtype=np.float32)

        def process_row(row):
            for j in range(colsB):
                result[row, j] = np.dot(A[row, :], B[:, j])

        with ThreadPoolExecutor(max_workers=threads) as executor:
            executor.map(process_row, range(rowsA))

        return result
