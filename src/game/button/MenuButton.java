package game.button;

import game.state.State;
import utils.constant.GameImage;

import java.awt.*;
import java.awt.image.BufferedImage;

import static utils.LoadSafe.getSpriteAtlas;
import static utils.constant.ItemInfo.BUTTON_I;


public class MenuButton extends GameButton {
  private static final int X_OFFSET_CENTER = BUTTON_I.width / 2;
  private int index;
  private BufferedImage[] images;

  public MenuButton(int x, int y, int rowIndex, State state) {
    super(x, y, new Rectangle(x - X_OFFSET_CENTER, y, BUTTON_I.width, BUTTON_I.height), state::run);
    this.rowIndex = rowIndex;
    loadImages();
  }


  @Override
  protected void loadImages() {
    images = new BufferedImage[3];
    BufferedImage temp = getSpriteAtlas(GameImage.MENU_BUTTONS);
    for (int i = 0; i < images.length; i++) {
      images[i] = temp.getSubimage(i * BUTTON_I.defaultWidth, rowIndex * BUTTON_I.defaultHeight,
          BUTTON_I.defaultWidth, BUTTON_I.defaultHeight);
    }
  }

  @Override
  public void draw(Graphics graphics) {
    graphics.drawImage(images[index],
        x - X_OFFSET_CENTER, y,
        BUTTON_I.width, BUTTON_I.height, null);
  }

  @Override
  public void update() {
    index = 0;
    if (mouseOver) {
      index = 1;
    }
    if (mousePressed) {
      index = 2;
    }
  }
}
