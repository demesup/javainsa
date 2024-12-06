package game.objects.items;

import game.objects.GameObject;
import utils.constant.ItemType;

public abstract class InteractableObject extends GameObject {
  protected final ItemType itemType;
  protected int xDrawOffset;
  protected int yDrawOffset;

  protected boolean doAnimation;
  protected boolean visible;

  protected InteractableObject(float x, float y, ItemType itemType) {
    super(x, y, itemType.animationSpeed);
    this.itemType = itemType;
    this.doAnimation = false;
    this.visible = true;

    initHitBox(itemType.hitBox.width(), itemType.hitBox.height());
    this.xDrawOffset = itemType.hitBox.offsetX();
    this.yDrawOffset = itemType.hitBox.offsetY();
  }

  @Override
  protected void updateAnimationTick() {
    animationTick++;
    if (animationTick == animationSpeed) {
      animationTick = 0;
      animationIndex++;
      if (animationIndex >= itemType.spriteAmount) {
        animationIndex = 0;
      }
    }
  }

  public boolean isVisible() {
    return visible;
  }

  public void setVisible(boolean visible) {
    this.visible = visible;
  }

  public boolean isAnimating() {
    return doAnimation;
  }
}
