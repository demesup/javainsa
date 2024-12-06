package game.objects;

import game.interfaces.Drawable;
import game.interfaces.Resettable;
import game.interfaces.Updatable;

import java.awt.*;
import java.awt.geom.Rectangle2D;

public abstract class GameObject implements Updatable, Drawable, Resettable {
  protected float x;
  protected float y;
  protected Rectangle2D.Float hitBox;
  protected int animationIndex;
  protected int animationSpeed;
  protected int animationTick;

  protected GameObject(float x, float y, int animationSpeed) {
    this.x = x;
    this.y = y;
    this.animationSpeed = animationSpeed;
  }

  protected void initHitBox(int width, int height) {
    hitBox = new Rectangle2D.Float(x, y, width, height);
  }

  public void drawHitBox(Graphics graphics, int xLevelOffset) {
    graphics.setColor(Color.CYAN);
    graphics.drawRect((int) hitBox.x - xLevelOffset, (int) hitBox.y, (int) hitBox.width, (int) hitBox.height);
  }


  protected abstract void updateAnimationTick();

  public int getAnimationIndex() {
    return animationIndex;
  }

  public Rectangle2D.Float getHitBox() {
    return hitBox;
  }

  public int getAnimationTick() {
    return animationTick;
  }

  protected void resetAnimationTick() {
    animationTick = 0;
    animationIndex = 0;
  }
}
