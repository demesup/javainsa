package game.button;


import utils.constant.GameImage;

import java.awt.*;
import java.awt.image.BufferedImage;

import static utils.LoadSafe.getSpriteAtlas;
import static utils.constant.ItemInfo.URM_I;


public class UrmButton extends PauseButton {
  private int index;
  private BufferedImage[] images;

  public UrmButton(int x, int y, int rowIndex, Runnable whenPressed) {
    super(x, y, URM_I.width, URM_I.height, rowIndex, whenPressed);
    loadImages();
  }

  @Override
  protected void loadImages() {
    images = new BufferedImage[3];
    BufferedImage temp = getSpriteAtlas(GameImage.URM_BUTTONS.getPath());
    for (int i = 0; i < images.length; i++) {
      images[i] = temp.getSubimage(i * URM_I.defaultWidth, rowIndex * URM_I.defaultHeight,
          URM_I.defaultWidth, URM_I.defaultHeight);
    }
  }

  @Override
  public void draw(Graphics graphics) {
    graphics.drawImage(images[index], x, y, URM_I.size, URM_I.size, null);
  }

  @Override
  public void update() {
    index = 0;
    if (mouseOver) index = 1;
    if (mousePressed) index = 2;
  }
}
