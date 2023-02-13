package com.cydercode.watermelon;

import com.cydercode.watermelon.config.ProcessConfig;
import com.cydercode.watermelon.config.Step;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import javax.imageio.ImageIO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
public class Process {

  private final ProcessConfig processConfig;

  public void run() {
    File inputDirectoryFile =
        new File(processConfig.getProcessDirectory(), processConfig.getInputDirectory());
    if (!inputDirectoryFile.exists()) {
      log.warn("Input directory not found, there`s nothing to do");
      return;
    }

    File[] files = inputDirectoryFile.listFiles();
    Arrays.stream(files).forEach(this::runProcessForImage);
  }

  private void runProcessForImage(File file) {
    log.info("Running process for file: {}", file);
    File outputDirectory =
        new File(processConfig.getProcessDirectory(), processConfig.getOutputDirectory());
    if (!outputDirectory.exists()) {
      throw new IllegalArgumentException("Cannot find output directory: " + outputDirectory);
    }

    try {
      BufferedImage source = ImageIO.read(file);
      for (Step step : processConfig.getSteps()) {
        source = runStep(source, step);
      }

      File outputFile = new File(outputDirectory, file.getName());
      ImageIO.write(source, "jpg", outputFile);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  private BufferedImage runStep(BufferedImage image, Step step) {
    switch (step.getType()) {
      case WRITE_IMAGE:
        return writeImage(processConfig, image, step);
      case SCALE:
        return scaleImage(image, step);
      default:
        throw new IllegalArgumentException();
    }
  }

  private static BufferedImage scaleImage(BufferedImage image, Step step) {
    double scaleX = step.getX() / 100.0;
    double scaleY = step.getY() / 100.0;
    int targetWith = (int) (image.getWidth() * scaleX);
    int targetHeight = (int) (image.getHeight() * scaleY);
    Image scaledImage = image.getScaledInstance(targetWith, targetHeight, Image.SCALE_SMOOTH);
    BufferedImage bufferedImage =
        new BufferedImage(targetWith, targetHeight, BufferedImage.TYPE_INT_RGB);
    bufferedImage.getGraphics().drawImage(scaledImage, 0, 0, null);
    return bufferedImage;
  }

  private static BufferedImage writeImage(
      ProcessConfig processConfig, BufferedImage image, Step step) {
    log.info("Loading WRITE_IMAGE file: {}", step.getFileName());
    try {
      File resourcesDir =
          new File(processConfig.getProcessDirectory(), processConfig.getResourcesDirectory());
      BufferedImage stepImage = ImageIO.read(new File(resourcesDir, step.getFileName()));

      Graphics2D graphics = (Graphics2D) image.getGraphics();
      AlphaComposite alphaChannel = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, step.getAlpha() == null ? 1 : step.getAlpha());
      graphics.setComposite(alphaChannel);
      int posX = getPaddedPos(image.getWidth(), stepImage.getWidth(), step.getX());
      int posY = getPaddedPos(image.getHeight(), stepImage.getHeight(), step.getY());
      graphics.drawImage(stepImage, posX, posY, null);
      graphics.dispose();
      return image;
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  private static int getPaddedPos(int sourceDimension, int imgDimension, int pos) {
    if (pos < 0) {
      pos = -1 * pos;
      return sourceDimension - pos - imgDimension;
    }

    return pos;
  }
}
