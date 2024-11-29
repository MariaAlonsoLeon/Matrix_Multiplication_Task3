import numpy as np
import concurrent.futures


class ParallelMatrixMultiplicationOpenMP:

    def multiply(self, A, B, threads):
        size = A.shape[0]
        threshold = self.calculate_threshold(size)

        C = np.zeros((size, size), dtype=np.float32)

        with concurrent.futures.ThreadPoolExecutor(max_workers=threads) as executor:
            futures = [executor.submit(self._multiply_task, A, B, C, 0, size, threshold)]
            for future in concurrent.futures.as_completed(futures):
                future.result()

        return C

    def _multiply_task(self, A, B, C, start_row, end_row, threshold):
        rows = end_row - start_row
        if rows <= threshold:
            for i in range(start_row, end_row):
                for j in range(B.shape[1]):
                    sum_ = 0
                    for k in range(A.shape[1]):
                        sum_ += A[i, k] * B[k, j]
                    C[i, j] = sum_
        else:
            mid_row = start_row + rows // 2
            futures = []
            with concurrent.futures.ThreadPoolExecutor() as executor:
                futures.append(executor.submit(self._multiply_task, A, B, C, start_row, mid_row, threshold))
                futures.append(executor.submit(self._multiply_task, A, B, C, mid_row, end_row, threshold))
                for future in concurrent.futures.as_completed(futures):
                    future.result()

    def calculate_threshold(self, size):
        return size // 10
