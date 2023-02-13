package com.cydercode.watermelon;

import com.cydercode.watermelon.config.ProcessConfig;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class App {

  public static final String PROCESS_JSON_FILENAME = "process.json";

  public static void main(String[] args) throws IOException {
    ProcessConfig config = new ConfigLoader().loadConfig(args[0]);
    Process process = new Process(config);
    process.run();
  }
}
