package game.objects.entity;


import game.Game;
import game.objects.GameObject;
import utils.constant.EntityInfo;
import utils.constant.EntityState;

import java.awt.*;
import java.awt.geom.Rectangle2D;


public abstract class Entity extends GameObject {
  protected final Game game;
  protected final float speed;
  protected int width;
  protected int height;
  protected Rectangle2D.Float attackBox;
  protected int attackBoxOffsetX;
  protected float fallSpeed;

  protected int currentHealth;

  protected boolean inAir;
  protected boolean attackChecked;

  protected Entity(float x, float y,
                   EntityInfo info, Game game) {
    super(x, y, info.animationSpeed);
    this.game = game;

    inAir = false;
    this.width = info.width;
    this.height = info.height;
    this.currentHealth = info.stats.maxHealth();
    this.speed = info.stats.speed();
    this.fallSpeed = info.stats.fallSpeed();
    this.attackBoxOffsetX = info.attackBox.offsetX();
    resetAnimationTick();
  }

  protected void initAttackBox(float x, float y, int width, int height) {
    attackBox = new Rectangle2D.Float(x, y, width, height);
  }

  protected abstract void updateAttackBox();

  protected void updateHitBox() {
    hitBox.x = (int) x;
    hitBox.y = (int) y;
  }

  protected void drawAttackBox(Graphics graphics, int xLevelOffset) {
    graphics.setColor(Color.RED);
    graphics.drawRect(
        (int) (attackBox.x - xLevelOffset),
        (int) attackBox.y,
        (int) attackBox.width,
        (int) attackBox.height
    );
  }


  public abstract void setState(EntityState enemyState);

  public Rectangle2D.Float getAttackBox() {
    return attackBox;
  }

  public void dead() {
    currentHealth = 0;
  }
}
