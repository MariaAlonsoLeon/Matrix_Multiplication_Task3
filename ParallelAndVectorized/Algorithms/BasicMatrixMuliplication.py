class BasicMatrixMultiplication:
    def multiply(self, A, B):
        rowsA = len(A)
        colsA = len(A[0])
        colsB = len(B[0])

        result = [[0.0 for _ in range(colsB)] for _ in range(rowsA)]

        for i in range(rowsA):
            for j in range(colsB):
                for k in range(colsA):
                    result[i][j] += A[i][k] * B[k][j]

        return result
