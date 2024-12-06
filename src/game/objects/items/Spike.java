package game.objects.items;

import game.objects.GameObject;
import utils.LoadSafe;
import utils.constant.GameImage;
import utils.constant.ItemType;

import java.awt.*;
import java.awt.image.BufferedImage;

import static utils.constant.ItemInfo.SPIKE_I;

public class Spike extends GameObject {
  private static final BufferedImage spikeImage = LoadSafe.getSpriteAtlas(GameImage.SPIKES);

  public Spike(float x, float y) {
    super(x, y, 0);
    initHitBox(ItemType.SPIKE.hitBox.width(), ItemType.SPIKE.hitBox.height());
    hitBox.y += ItemType.SPIKE.hitBox.offsetY();
  }

  @Override
  public void draw(Graphics graphics, int xLevelOffset) {
    graphics.drawImage(spikeImage, (int) hitBox.x - xLevelOffset,
        (int) hitBox.y - ItemType.SPIKE.hitBox.height(), SPIKE_I.width, SPIKE_I.height, null);
  }

  @Override
  public void resetAll() {
    // we do not reset spikes since they are always active and can not be killed by user
  }

  @Override
  protected void updateAnimationTick() {
    // spikes do not have animation
  }
}
