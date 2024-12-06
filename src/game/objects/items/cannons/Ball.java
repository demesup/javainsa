package game.objects.items.cannons;

import game.objects.items.InteractableObject;
import utils.LoadSafe;
import utils.constant.GameImage;
import utils.constant.ItemType;

import java.awt.*;
import java.awt.image.BufferedImage;

import static utils.Utils.isSolid;
import static utils.constant.Constants.CANNON_BALL_SPEED;
import static utils.constant.ItemInfo.BALL_I;

public class Ball extends InteractableObject {
  private static final BufferedImage ballImage = LoadSafe.getSpriteAtlas(GameImage.BALL);

  private final int dir;

  protected Ball(float x, float y, int dir) {
    super(x, y, ItemType.BALL);
    this.dir = dir;
  }

  @Override
  public void update() {
    hitBox.x += dir * CANNON_BALL_SPEED;
  }


  @Override
  protected void updateAnimationTick() {
    //empty code block
  }


  @Override
  public void resetAll() {
    hitBox.x = x;
    hitBox.y = y;
    doAnimation = false;
    visible = true;
  }

  public boolean collides(int[][] levelData) {
    return isSolid((float) hitBox.getCenterX(), (float) hitBox.getCenterY(), levelData);
  }

  @Override
  public void draw(Graphics graphics, int xLevelOffset) {
    graphics.drawImage(ballImage, (int) (hitBox.x - xLevelOffset), (int) hitBox.y, BALL_I.width, BALL_I.height, null);
  }
}
