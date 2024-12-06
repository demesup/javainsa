package game.objects.items.containers;

import game.objects.items.InteractableObject;
import game.objects.items.potions.Potion;
import utils.LoadSafe;
import utils.constant.GameImage;
import utils.constant.ItemType;

import java.awt.*;
import java.awt.image.BufferedImage;

import static utils.constant.Constants.SCALE;
import static utils.constant.ItemInfo.CONTAINER_I;


public abstract class GameContainer extends InteractableObject {
  private static final BufferedImage[][] containerImages;

  static {
    BufferedImage containerAtlas = LoadSafe.getSpriteAtlas(GameImage.BARRELS);
    containerImages = new BufferedImage[2][8];

    for (int i = 0; i < containerImages.length; i++) {
      for (int j = 0; j < containerImages[i].length; j++) {
        containerImages[i][j] = containerAtlas.getSubimage(CONTAINER_I.defaultWidth * j, CONTAINER_I.defaultHeight * i,
            CONTAINER_I.defaultWidth, CONTAINER_I.defaultHeight);
      }
    }
  }

  protected GameContainer(float x, float y, ItemType itemType) {
    super(x, y, itemType);
    hitBox.y += yDrawOffset + (int) (SCALE * 2);
    hitBox.x += (float) xDrawOffset / 2;
  }

  public abstract Potion open();

  @Override
  public void update() {
    if (doAnimation) {
      updateAnimationTick();
    }
  }

  @Override
  public void resetAll() {
    resetAnimationTick();
    doAnimation = false;
    visible = true;
  }

  @Override
  protected void updateAnimationTick() {
    animationTick++;
    if (animationTick == animationSpeed) {
      animationTick = 0;
      animationIndex++;
      if (animationIndex >= itemType.spriteAmount) {
        animationIndex = 0;
        doAnimation = false;
        visible = false;
      }
    }
  }

  @Override
  public void draw(Graphics graphics, int xLevelOffset) {
    int type = 0;
    if (itemType == ItemType.BARREL) {
      type = 1;
    }

    graphics.drawImage(containerImages[type][animationIndex],
        (int) (hitBox.x - xDrawOffset - xLevelOffset),
        (int) (hitBox.y - yDrawOffset),
        CONTAINER_I.width, CONTAINER_I.height, null);
  }

  public boolean operable() {
    return visible && !doAnimation;
  }
}
