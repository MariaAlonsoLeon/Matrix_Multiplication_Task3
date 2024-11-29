package resources_tests;

import com.fasterxml.jackson.databind.ObjectMapper;
import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ResourceMonitor {

    private static final SystemInfo systemInfo = new SystemInfo();

    public static Map<String, Object> monitorResources() {
        Map<String, Object> resourceMetrics = new HashMap<>();

        CentralProcessor processor = systemInfo.getHardware().getProcessor();
        resourceMetrics.put("CPU Cores (Logical)", processor.getLogicalProcessorCount());
        resourceMetrics.put("CPU Cores (Physical)", processor.getPhysicalProcessorCount());
        resourceMetrics.put("CPU Frequency (MHz)", processor.getMaxFreq() / 1000.0);
        resourceMetrics.put("CPU Usage (%)", getCpuUsage());

        Runtime runtime = Runtime.getRuntime();
        resourceMetrics.put("FreeMemory", runtime.freeMemory());
        resourceMetrics.put("TotalMemory", runtime.totalMemory());
        resourceMetrics.put("MaxMemory", runtime.maxMemory());
        resourceMetrics.put("UsedMemory", runtime.totalMemory() - runtime.freeMemory());

        resourceMetrics.put("AvailableProcessors", runtime.availableProcessors());

        resourceMetrics.put("ActiveThreads", Thread.activeCount());

        return resourceMetrics;
    }

    private static double getCpuUsage() {
        CentralProcessor processor = systemInfo.getHardware().getProcessor();
        long[] prevTicks = processor.getSystemCpuLoadTicks();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        long[] ticks = processor.getSystemCpuLoadTicks();
        double cpuLoad = processor.getSystemCpuLoadBetweenTicks(prevTicks) * 100;
        return cpuLoad;
    }

    public static void saveMetricsToJson(List<Map<String, Object>> results, String filename) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writerWithDefaultPrettyPrinter();
        try {
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File(filename), results);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
