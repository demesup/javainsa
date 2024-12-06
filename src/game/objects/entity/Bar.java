package game.objects.entity;


import game.interfaces.Updatable;
import utils.LoadSafe;
import utils.constant.GameImage;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Bar implements Updatable {
  protected static BufferedImage image = LoadSafe.getSpriteAtlas(GameImage.BAR);
  public final int width;
  public final int height;
  public final int x;
  public final int y;

  protected int currentWidth;


  public Bar(int width, int height, int x, int y) {
    this.width = width;
    this.height = height;
    this.x = x;
    this.y = y;
    this.currentWidth = width;
  }

  public static Image getImage() {
    return image;
  }

  public int getCurrentWidth() {
    return currentWidth;
  }

  public void setCurrentWidth(int currentWidth) {
    this.currentWidth = currentWidth;
  }
}
