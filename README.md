# Matrix Multiplication Optimization Project

This project investigates the optimization of matrix multiplication, a critical operation in various computational fields such as artificial intelligence, image processing, and scientific simulations. Given its computational intensity, especially for large matrices, the study explores and benchmarks a range of optimization techniques, including parallelization and vectorization, to enhance efficiency and performance. Implementations were conducted in both Java and Python, with an optional analysis comparing the two languages and incorporating advanced metrics.

## Cover Page:

- **Subject:** Big Data (BD)
- **Academic Year:** 2024-2025
- **Degree:** Data Science and Engineering (GCID)
- **School:** School of Computer Engineering (EII)
- **University:** University of Las Palmas de Gran Canaria (ULPGC)

## Development Environment:

- **IDEs/Editors Used:** IntelliJ and PyCharm.
- **Version Control:** Git & GitHub for source code management and collaboration.

## Implementation

### Methodology

The project implements and analyzes multiple matrix multiplication algorithms using a modular, scalable approach. The Java implementation is structured using the Maven framework, ensuring maintainability and precise performance measurements, while Python complements the analysis with analogous implementations and GPU-based techniques.

It is important to note that the project in Python is called 'ParallelAndVectorized' and in Java 'ParallelAndVectorizedMatrices'.

#### Java Implementation

##### Interfaces:

- Parallel Algorithms: Defines a method multiply(double matrixA, double matrixB, int numThreads) to accommodate multi-threading.
- Sequential and Vectorized Algorithms: Includes the method multiply(double matrixA, double matrixB) for non-parallelized operations.

##### Algorithm:

**Basic Matrix Multiplication:** Simple sequential implementation used as a baseline.
**Parallel Algorithms:**
- `AtomicMatrixMultiplication`
- `FixedThreadsMatrixMultiplication`
- `MatrixMultiplicationThreads`
- `SynchronizedMatrixMultiplication`
- `ExecutorMatrixMultiplication`
- `ParallelOpenMPMatrixMultiplication`

**Vectorized and Sequential Algorithms:**
- `VectorizedMatrixMultiplication`
- `OptimizedVectorizedMatrixMultiplication`
- `StreamMatrixMultiplication`
- `VectorizedSIMDMatrixMultiplication`

##### Testing:

- Benchmarked using the **Java Microbenchmark Harness (JMH)**.
- Resource usage metrics tracked with libraries like `oshi.hardware.CentralProcessor` and `oshi.SystemInfo`.

---

#### Python Implementation

Includes implementations analogous to Java and additional GPU-based optimizations using CUDA. 

##### Algorithm:

**Basic Matrix Multiplication:** Simple sequential implementation used as a baseline.

**Parallel Algorithms:**
- `SynchronizedMatrixMultiplication`
- `ExecutorMatrixMultiplication`
- `CudaMatrixMultiplication`
- `MatrixMultiplicationThreads`
- `ParallelMatrixMultiplicationOpenMP`

**Vectorized Algorithms:**
- `OptimizedVectorizedMatrixMultiplication`
- `VectorizedMatrixMultiplicationSIMD`
- `StreamMatrixMultiplication`

This comprehensive implementation ensures scalability, maintainability, and precise benchmarking across different configurations.

Note: As explained in the paper, it is important to note that although the stream algorithm is neither a sequential nor a vectorized algorithm, it is included in this category because it does not require specifying the number of threads, as it calculates it automatically.

## Libraries and Dependencies
### Java

- **Maven**
- **Java Microbenchmark Harness (JMH)**
- **OSHI library** (`oshi.hardware.CentralProcessor`, `oshi.SystemInfo`)

### Python

- **NumPy**
- **Numba**
- **PyCUDA** (for CUDA-based algorithms)
-  `os`
- `platform`
- `psutil`
- `json`
- `threading`

## How to Run

To use the project, follow these steps:

1. **Clone the repository to your local machine:**

    ```bash
    git clone https://github.com/MariaAlonsoLeon/Matrix_Multiplication_Task3
    ```

2. **Open the project in IntelliJ IDEA or PyCharm**, depending on the programming language you want to test.

3. **Navigate to the module corresponding to the programming language:**
    - For Java: Open the `java/` module.
    - For Python: Open the `Algorithm/` folder.

#### In IntelliJ IDEA (Java):
- Locate the test classes under the `src/test/java` directory.
- Right-click on the desired test class and select **Run** to execute the tests.

#### In PyCharm (Python):
- Locate the Test folder and right-click on the desired test class and select **Run** to execute the tests.

### Benchmarking Results:
- The tests will execute and generate benchmarking results in JSON format.
- These files can be found in the respective output directories and used for further analysis.

## Results

- **VectorizedSIMD** emerged as the best-performing algorithm, consistently achieving the highest speedup and lowest execution time per operation.
- Parallel algorithms demonstrated significant improvements for large matrices but suffered from overhead at smaller sizes.

For more detailed results please go to the paper.

## Additional Analysis and Optional Assignments

### Optional Analysis:
- Exhaustive benchmarking of vectorized algorithms.
- Cross-language comparison between Java and Python implementations.

### Additional Metrics:
- Execution time per operation (ms/ops).
- Memory usage per operation (MB/ops).
- CPU utilization.

### Comparisons:
- Each algorithm was benchmarked against the basic implementation to assess relative performance.

This optional exploration enriched the study, providing a deeper understanding of algorithmic efficiency across diverse computational contexts.
  
