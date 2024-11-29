package benchmarks;

import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.openjdk.jmh.results.format.ResultFormatType;

public class BenchmarkExecutor {

    public static void main(String[] args) throws RunnerException {
        runBenchmark(ParallelMatrixMultiplicationBenchmark.class);

        runBenchmark(MatrixMultiplicationBenchmark.class);
    }

    private static void runBenchmark(Class<?> benchmarkClass) throws RunnerException {
        String benchmarkName = benchmarkClass.getSimpleName();
        System.out.println(benchmarkName);

        Options options = new OptionsBuilder()
                .include(benchmarkClass.getSimpleName())
                .warmupIterations(2)
                .measurementIterations(5)
                .forks(1)
                .threads(Runtime.getRuntime().availableProcessors())
                .resultFormat(ResultFormatType.JSON)
                .result("gc-sequetial  " + "-benchmark-results.json")
                .jvmArgs("--add-modules", "jdk.incubator.vector", "-XX:+UseG1GC", "-XX:+PrintGCDetails")
                .addProfiler("gc")
                .build();

        Runner runner = new Runner(options);
        runner.run();
    }
}
