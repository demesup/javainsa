package game.button;

import java.awt.*;

public abstract class PauseButton extends GameButton {
  protected int width;
  protected int height;

  protected PauseButton(int x, int y, int width, int height, Runnable whenPressed) {
    super(x, y, new Rectangle(x, y, width, height), whenPressed);
    this.width = width;
    this.height = height;
    createBounds();
  }

  protected PauseButton(int x, int y, int width, int height) {
    super(x, y, new Rectangle(x, y, width, height));
    this.width = width;
    this.height = height;
    createBounds();
  }

  protected PauseButton(int x, int y, int width, int height, int rowIndex, Runnable whenPressed) {
    super(x, y, rowIndex, new Rectangle(x, y, width, height), whenPressed);
    this.width = width;
    this.height = height;
  }

  private void createBounds() {
    bounds = new Rectangle(x, y, width, height);
  }

  public int getWidth() {
    return width;
  }

  public void setWidth(int width) {
    this.width = width;
  }

  public int getHeight() {
    return height;
  }

  public void setHeight(int height) {
    this.height = height;
  }
}
