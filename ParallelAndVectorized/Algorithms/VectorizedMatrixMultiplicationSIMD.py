import numpy as np

class VectorizedMatrixMultiplicationSIMD:
    def multiply(self, matrixA, matrixB):
        matrixA = np.array(matrixA)
        matrixB = np.array(matrixB)

        transposedB = matrixB.T

        result = np.dot(matrixA, transposedB)
        return result.tolist()
