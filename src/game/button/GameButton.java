package game.button;


import game.interfaces.Drawable;
import game.interfaces.Updatable;

import java.awt.*;
import java.awt.event.MouseEvent;

public abstract class GameButton implements Updatable, Drawable {
  protected int x;
  protected int y;
  protected Rectangle bounds;
  protected boolean mouseOver;
  protected boolean mousePressed;
  protected int rowIndex;
  protected Runnable whenPressed;

  protected GameButton(int x, int y, Rectangle bounds, Runnable whenPressed) {
    this.x = x;
    this.y = y;
    this.bounds = bounds;
    this.whenPressed = whenPressed;
  }

  protected GameButton(int x, int y, Rectangle bounds) {
    this.x = x;
    this.y = y;
    this.bounds = bounds;
  }

  protected GameButton(int x, int y, int rowIndex, Rectangle bounds, Runnable whenPressed) {
    this.x = x;
    this.y = y;
    this.rowIndex = rowIndex;
    this.bounds = bounds;
    this.whenPressed = whenPressed;
  }


  public int getX() {
    return x;
  }

  public void setX(int x) {
    this.x = x;
  }

  public int getY() {
    return y;
  }

  public void setY(int y) {
    this.y = y;
  }

  public void setMouseOver(boolean mouseOver) {
    this.mouseOver = mouseOver;
  }

  public boolean isMousePressed() {
    return mousePressed;
  }

  public void setMousePressed(boolean mousePressed) {
    this.mousePressed = mousePressed;
  }

  public void resetBooleans() {
    mouseOver = false;
    mousePressed = false;
  }

  protected abstract void loadImages();

  public void runOnPressed() {
    if (mousePressed) {
      whenPressed.run();
    }
  }

  public boolean isIn(MouseEvent e) {
    return bounds.contains(e.getPoint());
  }
}
