package game.objects.items.potions;

import game.objects.entity.Player;
import game.objects.items.InteractableObject;
import utils.LoadSafe;
import utils.constant.GameImage;
import utils.constant.ItemType;

import java.awt.*;
import java.awt.image.BufferedImage;

import static utils.constant.Constants.PotionValues.FLOATING_SPEED;
import static utils.constant.ItemInfo.POTION_I;

public abstract class Potion extends InteractableObject {
  private static final BufferedImage[][] potionImages;

  static {
    BufferedImage potionAtlas = LoadSafe.getSpriteAtlas(GameImage.POTIONS);
    potionImages = new BufferedImage[2][7];

    for (int i = 0; i < potionImages.length; i++) {
      for (int j = 0; j < potionImages[i].length; j++) {
        potionImages[i][j] = potionAtlas.getSubimage(POTION_I.defaultWidth * j, POTION_I.defaultHeight * i,
            POTION_I.defaultWidth, POTION_I.defaultHeight);
      }
    }
  }

  protected int maxHoverOffset;
  private float hoverOffset;
  private int hoverDir;

  protected Potion(float x, float y, ItemType type) {
    super(x, y, type);
    doAnimation = true;
    hoverDir = 1;
  }

  @Override
  public void update() {
    updateAnimationTick();
    updateHover();
  }

  private void updateHover() {
    hoverOffset += FLOATING_SPEED * hoverDir;

    if (hoverOffset > maxHoverOffset) {
      hoverDir = -1;
    } else if (hoverOffset < 0) {
      hoverDir = 1;
    }

    hitBox.y = y + hoverOffset;
  }

  @Override
  public void resetAll() {
    resetAnimationTick();
    doAnimation = true;
    visible = true;
  }

  @Override
  public void draw(Graphics graphics, int xLevelOffset) {
    int type = 0;
    if (itemType == ItemType.RED_POTION) {
      type = 1;
    }

    graphics.drawImage(potionImages[type][animationIndex],
        (int) (hitBox.x - xDrawOffset - xLevelOffset),
        (int) (hitBox.y - yDrawOffset),
        POTION_I.width, POTION_I.height, null);
  }

  public abstract void applyAffect(Player player);
}
