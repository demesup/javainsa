package utils.constant;

import static utils.constant.Constants.SCALE;

public record Box(int width, int height, int offsetX, int offsetY) {
  public Box(int width, int height, int offsetX, int offsetY) {
    this.width = (int) (width * SCALE);
    this.height = (int) (height * SCALE);
    this.offsetX = (int) (offsetX * SCALE);
    this.offsetY = (int) (offsetY * SCALE);
  }
}
