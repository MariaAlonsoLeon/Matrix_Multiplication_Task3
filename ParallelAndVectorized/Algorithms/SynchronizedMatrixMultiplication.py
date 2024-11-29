import numpy as np
import threading

class SynchronizedMatrixMultiplication:

    def multiply(self, A, B, threads):
        rows_A, cols_A = A.shape
        rows_B, cols_B = B.shape

        if cols_A != rows_B:
            raise ValueError("The number of columns in matrix A must be equal to the number of rows in matrix B.")

        result = np.zeros((rows_A, cols_B), dtype=np.float32)

        semaphore = threading.Semaphore(threads)

        row_counter = [0]

        def task():
            while True:
                with threading.Lock():
                    row = row_counter[0]
                    row_counter[0] += 1

                if row >= rows_A:
                    break

                for j in range(cols_B):
                    sum_ = 0
                    for k in range(cols_A):
                        sum_ += A[row, k] * B[k, j]
                    result[row, j] = sum_

                semaphore.release()

        threads_list = []
        for _ in range(threads):
            semaphore.acquire()
            t = threading.Thread(target=task)
            threads_list.append(t)
            t.start()

        for t in threads_list:
            t.join()

        return result
