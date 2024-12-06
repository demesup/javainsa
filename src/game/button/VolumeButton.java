package game.button;


import utils.constant.GameImage;

import java.awt.*;
import java.awt.image.BufferedImage;

import static utils.LoadSafe.getSpriteAtlas;
import static utils.constant.Constants.GAME_WIDTH_CENTER;
import static utils.constant.ItemInfo.SLIDER_I;
import static utils.constant.ItemInfo.VOLUME_I;


public class VolumeButton extends PauseButton {
  private final int minX;
  private final int maxX;
  private BufferedImage[] images;
  private BufferedImage slider;
  private int index = 0;
  private int buttonX;
  private float floatValue = 0f;


  public VolumeButton(int x, int y, int width, int height) {
    super(x + width / 2, y, VOLUME_I.width, height);
    bounds.x -= VOLUME_I.width / 2;
    buttonX = x + width / 2;
    this.x = x;
    this.width = width;

    loadImages();

    minX = GAME_WIDTH_CENTER - SLIDER_I.width / 2 + VOLUME_I.width / 2;
    maxX = minX + SLIDER_I.width - VOLUME_I.width;

    whenPressed = () -> {
      // empty block
    };
  }

  public void changeX(int x) {
    if (x < minX) {
      buttonX = minX;
      setX(minX);
    } else {
      buttonX = Math.min(x, maxX);
      setX(x);
    }
    updateFloatValue();
    bounds.x = buttonX - VOLUME_I.width / 2;
  }

  @Override
  protected void loadImages() {
    BufferedImage temp = getSpriteAtlas(GameImage.VOLUME_BUTTONS.getPath());
    images = new BufferedImage[3];
    for (int i = 0; i < images.length; i++) {
      images[i] = temp.getSubimage(i * VOLUME_I.defaultWidth, 0, VOLUME_I.defaultWidth, VOLUME_I.defaultHeight);
    }

    slider = temp.getSubimage(3 * VOLUME_I.defaultWidth, 0, SLIDER_I.defaultWidth, VOLUME_I.defaultHeight);
  }

  @Override
  public void draw(Graphics graphics) {
    graphics.drawImage(slider, GAME_WIDTH_CENTER - SLIDER_I.width / 2, y, width, height, null);
    graphics.drawImage(images[index], buttonX - VOLUME_I.width / 2, y, VOLUME_I.width, VOLUME_I.height, null);
  }

  @Override
  public void update() {
    index = 0;
    if (mouseOver) index = 1;
    if (mousePressed) index = 2;
  }

  private void updateFloatValue() {
    float range = (float) maxX - minX;
    float value = (float) buttonX - minX;
    floatValue = value / range;
  }

  public float getFloatValue() {
    return floatValue;
  }
}
