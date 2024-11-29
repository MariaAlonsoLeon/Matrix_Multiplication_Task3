class AtomicMatrixMultiplication:
    def multiply(self, matrixA, matrixB):
        size = len(matrixA)
        result = [[0.0 for _ in range(size)] for _ in range(size)]

        row = 0

        while row < size:
            r = row
            row += 1

            if r < size:
                for c in range(size):
                    for k in range(size):
                        result[r][c] += matrixA[r][k] * matrixB[k][c]

        return result
