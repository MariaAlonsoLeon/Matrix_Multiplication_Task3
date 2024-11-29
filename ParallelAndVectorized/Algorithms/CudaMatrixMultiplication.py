import numpy as np
from numba import cuda

class CudaMatrixMultiplication:
    @staticmethod
    @cuda.jit
    def _cuda_kernel(A, B, C):
        row, col = cuda.grid(2)  # Obtener el índice de fila y columna
        if row < C.shape[0] and col < C.shape[1]:
            temp = 0
            for k in range(A.shape[1]):
                temp += A[row, k] * B[k, col]
            C[row, col] = temp

    def multiply(self, A, B):
        if A.shape[1] != B.shape[0]:
            raise ValueError("The number of columns in matrix A must be equal to the number of rows in matrix B.")

        C = np.zeros((A.shape[0], B.shape[1]), dtype=np.float32)

        A_device = cuda.to_device(A)
        B_device = cuda.to_device(B)
        C_device = cuda.to_device(C)

        threads_per_block = (16, 16)
        blocks_per_grid_x = int(np.ceil(C.shape[0] / threads_per_block[0]))
        blocks_per_grid_y = int(np.ceil(C.shape[1] / threads_per_block[1]))
        blocks_per_grid = (blocks_per_grid_x, blocks_per_grid_y)

        self._cuda_kernel[blocks_per_grid, threads_per_block](A_device, B_device, C_device)

        return C_device.copy_to_host()
