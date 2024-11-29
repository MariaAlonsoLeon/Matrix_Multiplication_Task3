import os
import platform
import psutil
import json
import threading
import time


def gather_system_info():
    system_info = {
        "Operating System": platform.system() + " " + platform.release(),
        "Version": platform.version(),
        "Architecture": platform.architecture()[0],
        "Available Processors": os.cpu_count(),
        "Total Memory (MB)": round(psutil.virtual_memory().total / 1024 / 1024),
        "Free Memory (MB)": round(psutil.virtual_memory().available / 1024 / 1024),
        "Active Thread Count": threading.active_count()
    }
    return system_info


def save_system_info_to_json(filepath):
    start_time = time.perf_counter()
    system_info = gather_system_info()
    end_time = time.perf_counter()

    system_info["Execution Time (ms)"] = round((end_time - start_time) * 1000, 2)

    with open(filepath, "w") as file:
        json.dump(system_info, file, indent=4)
    print(f"System information saved to {filepath}")


if __name__ == "__main__":
    save_system_info_to_json("system_info.json")
