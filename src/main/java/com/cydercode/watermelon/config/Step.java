package com.cydercode.watermelon.config;

import lombok.Data;

@Data
public class Step {

  public enum StepType {
    WRITE_IMAGE,
    SCALE,
  }

  private StepType type;
  private String fileName;
  private int x;
  private int y;
  private Float alpha;
}
