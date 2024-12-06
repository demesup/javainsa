package game.button;


import utils.constant.GameImage;

import java.awt.*;
import java.awt.image.BufferedImage;

import static utils.LoadSafe.getSpriteAtlas;
import static utils.constant.ItemInfo.SOUND_I;


public class SoundButton extends PauseButton {
  private BufferedImage[][] soundImages;
  private boolean muted;
  private int columnIndex = 0;

  public SoundButton(int x, int y, int width, int height, Runnable whenPressed) {
    super(x, y, width, height, whenPressed);

    loadImages();
  }

  @Override
  protected void loadImages() {
    loadSoundImages();
  }

  private void loadSoundImages() {
    BufferedImage temp = getSpriteAtlas(GameImage.SOUND_BUTTON);
    soundImages = new BufferedImage[2][3];
    for (int i = 0; i < soundImages.length; i++) {
      for (int j = 0; j < soundImages[i].length; j++) {
        soundImages[i][j] = temp.getSubimage(
            j * SOUND_I.defaultWidth,
            i * SOUND_I.defaultHeight,
            SOUND_I.defaultWidth,
            SOUND_I.defaultHeight);
      }
    }
  }

  @Override
  public void update() {
    if (muted) {
      rowIndex = 1;
    } else rowIndex = 0;

    columnIndex = 0;
    if (mouseOver) columnIndex = 1;
    if (mousePressed) columnIndex = 2;
  }

  @Override
  public void draw(Graphics graphics) {
    graphics.drawImage(soundImages[rowIndex][columnIndex], x, y, width, height, null);
  }

  public boolean isMuted() {
    return muted;
  }

  public void setMuted(boolean muted) {
    this.muted = muted;
  }

}
