package com.cydercode.watermelon;

import static com.cydercode.watermelon.App.PROCESS_JSON_FILENAME;

import com.cydercode.watermelon.config.ProcessConfig;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class ConfigLoader {
  public ProcessConfig loadConfig(String processDir) throws FileNotFoundException {
    File processDirFile = new File(processDir);
    if (!processDirFile.exists()) {
      throw new FileNotFoundException(processDir);
    }

    Gson gson = new Gson();
    JsonReader reader =
        new JsonReader(new FileReader(new File(processDirFile, PROCESS_JSON_FILENAME)));
    ProcessConfig processConfig = gson.fromJson(reader, ProcessConfig.class);
    processConfig.setProcessDirectory(processDirFile);
    return processConfig;
  }
}
