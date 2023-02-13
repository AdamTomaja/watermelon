package com.cydercode.watermelon.config;

import java.io.File;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ProcessConfig {

  private transient File processDirectory;
  private String inputDirectory;
  private String outputDirectory;
  private String resourcesDirectory;
  private List<Step> steps;
}
