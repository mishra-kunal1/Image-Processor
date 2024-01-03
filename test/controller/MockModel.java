package controller;

import model.Image;
import model.Model;

/** Mock model for testing. */
public class MockModel extends Model {
  private StringBuilder log;
  protected boolean isInModel = true;
  protected boolean isGrayScale = false;
  protected boolean isSameSize = true;

  /** Constructor for the mock model. */
  public MockModel() {
    this.log = new StringBuilder();
  }

  public StringBuilder getLog() {
    return this.log;
  }

  public String getLastLog() {
    String[] logs = log.toString().split("\n");
    return logs[logs.length - 1];
  }

  @Override
  public void addImage(String name, Image image) {
    log.append("addImage called with name: " + name + " image: \n");
  }

  @Override
  public Image getImage(String name) {
    log.append("getImage called with name: " + name + "\n");
    return null;
  }

  @Override
  public boolean isInModel(String name) {
    log.append("isInModel called with name: " + name + "\n");
    return isInModel;
  }

  @Override
  public void removeImage(String name) {
    log.append("removeImage called with name: " + name + "\n");
  }

  @Override
  public boolean containsImage(String name) {
    log.append("containsImage called with name: " + name + "\n");
    return false;
  }

  @Override
  public void brightenImage(int amount, String name, String destImageName) {
    log.append(
        "brightenImage called with amount: "
            + amount
            + " name: "
            + name
            + " destImageName: "
            + destImageName
            + "\n");
  }

  @Override
  public void blurImage(String name, String destImageName) {
    log.append("blurImage called with name: " + name + " destImageName: " + destImageName + "\n");
  }

  @Override
  public void sharpenImage(String name, String destImageName) {
    log.append(
        "sharpenImage called with name: " + name + " destImageName: " + destImageName + "\n");
  }

  @Override
  public void sepiaImage(String name, String destImageName) {
    log.append("sepiaImage called with name: " + name + " destImageName: " + destImageName + "\n");
  }

  @Override
  public void redComponent(String name, String destImageName) {
    log.append(
        "redComponent called with name: " + name + " destImageName: " + destImageName + "\n");
  }

  @Override
  public void greenComponent(String name, String destImageName) {
    log.append(
        "greenComponent called with name: " + name + " destImageName: " + destImageName + "\n");
  }

  @Override
  public void blueComponent(String name, String destImageName) {
    log.append(
        "blueComponent called with name: " + name + " destImageName: " + destImageName + "\n");
  }

  @Override
  public void calculateValue(String name, String destImageName) {
    log.append(
        "calculateValue called with name: " + name + " destImageName: " + destImageName + "\n");
  }

  @Override
  public void calculateIntensity(String name, String destImageName) {
    log.append(
        "calculateIntensity called with name: " + name + " destImageName: " + destImageName + "\n");
  }

  @Override
  public void calculateLuma(String name, String destImageName) {
    log.append(
        "calculateLuma called with name: " + name + " destImageName: " + destImageName + "\n");
  }

  @Override
  public void horizontalFlip(String name, String destImageName) {
    log.append(
        "horizontalFlip called with name: " + name + " destImageName: " + destImageName + "\n");
  }

  @Override
  public void verticalFlip(String name, String destImageName) {
    log.append(
        "verticalFlip called with name: " + name + " destImageName: " + destImageName + "\n");
  }

  @Override
  public void splitRGB(
      String name, String redDestImageName, String greenDestImageName, String blueDestImageName) {
    log.append(
        "splitRGB called with name: "
            + name
            + " redDestImageName: "
            + redDestImageName
            + " greenDestImageName: "
            + greenDestImageName
            + " blueDestImageName: "
            + blueDestImageName
            + "\n");
  }

  @Override
  public void combineRGB(
      String destImageName, String redImageName, String greenImageName, String blueImageName) {
    log.append(
        "combineRGB called with destImageName: "
            + destImageName
            + " redImageName: "
            + redImageName
            + " greenImageName: "
            + greenImageName
            + " blueImageName: "
            + blueImageName
            + "\n");
  }

  @Override
  public void previewImage(String name, String destImageName, int percent, String operation) {
    switch (operation) {
      case "blur":
      case "value":
      case "sepia":
      case "sharpen":
      case "color-correct":
      case "level-adjust":
        log.append(
            "previewImage called with name: "
                + name
                + " destImageName: "
                + destImageName
                + " percent: "
                + percent
                + " operation: "
                + operation
                + "\n");
        break;
      default:
        break;
    }
  }

  @Override
  public void histogram(String name, String destImageName) {
    log.append("histogram called with name: " + name + " destImageName: " + destImageName + "\n");
  }

  @Override
  public void colorCorrect(String name, String destImageName) {
    log.append(
        "colorCorrect called with name: " + name + " destImageName: " + destImageName + "\n");
  }

  @Override
  public void levelAdjust(int b, int m, int w, String name, String destImageName) {
    log.append(
        "levelAdjust called with b: "
            + b
            + " m: "
            + m
            + " w: "
            + w
            + " name: "
            + name
            + " destImageName: "
            + destImageName
            + "\n");
  }

  @Override
  public void compressImage(String name, String destImageName, int factor) {
    log.append(
        "compressImage called with name: "
            + name
            + " destImageName: "
            + destImageName
            + " factor: "
            + factor
            + "\n");
  }

  @Override
  public boolean isGrayScale(String s) {
    log.append("isGrayScale called with s: " + s + "\n");
    return isGrayScale;
  }

  @Override
  public boolean isSameSize(String s1, String s2) {
    log.append("isSameSize called with s1: " + s1 + " s2: " + s2 + "\n");
    return isSameSize;
  }
}
