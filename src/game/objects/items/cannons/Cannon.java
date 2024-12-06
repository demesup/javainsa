package game.objects.items.cannons;

import game.objects.Observable;
import game.objects.entity.Player;
import game.objects.items.InteractableObject;
import utils.LoadSafe;
import utils.constant.GameImage;
import utils.constant.ItemType;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.stream.IntStream;

import static utils.constant.ItemInfo.CANNON_I;
import static utils.constant.ItemInfo.TILES_I;

public abstract class Cannon extends InteractableObject implements Observable {
  private static final BufferedImage[] cannonImages;

  static {
    BufferedImage cannonAtlas = LoadSafe.getSpriteAtlas(GameImage.CANNONS);
    cannonImages = new BufferedImage[7];

    for (int i = 0; i < cannonImages.length; i++) {
      cannonImages[i] = cannonAtlas.getSubimage(CANNON_I.defaultWidth * i, 0,
          CANNON_I.defaultWidth, CANNON_I.defaultHeight);
    }
  }

  protected int tileY;

  protected Cannon(float x, float y, ItemType type) {
    super(x, y, type);
    this.tileY = (int) (y / TILES_I.size);
    hitBox.y += type.hitBox.offsetY();
  }

  @Override
  public void update() {
    if (doAnimation) {
      updateAnimationTick();
    }
  }

  @Override
  public void update(int[][] levelData, Player player) {
    if (doAnimation) {
      updateAnimationTick();
    }
  }


  public abstract Ball shoot();

  @Override
  public boolean checkY(Player player) {
    int playerTileY = (int) (player.getHitBox().y / TILES_I.size);
    return playerTileY == tileY;
  }


  @Override
  public boolean canSeePlayer(int[][] levelData, Player player) {
    return isPlayerInSightRange(player) && cannonSightClear(this.hitBox, player.getHitBox(), tileY, levelData);
  }

  private boolean cannonSightClear(Rectangle2D.Float hitBox, Rectangle2D.Float hitBox1, int tileY, int[][] levelData) {
    int firstXTile = (int) (hitBox.x / TILES_I.size);
    int secondXTile = (int) (hitBox1.x / TILES_I.size);

    return IntStream.range(firstXTile, secondXTile).allMatch(i -> 11 == levelData[tileY][i]);
  }

  @Override
  public void resetAll() {
    resetAnimationTick();
    visible = true;
    doAnimation = false;
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
      }
    }
  }

  @Override
  public void draw(Graphics graphics, int xLevelOffset) {
    int x = (int) (hitBox.x - xLevelOffset);

    if (this instanceof LeftCannon) {
      graphics.drawImage(cannonImages[animationIndex], x + CANNON_I.width - 2 * itemType.hitBox.offsetX(), (int) hitBox.y,
          -CANNON_I.width, CANNON_I.height, null);
    } else {
      graphics.drawImage(cannonImages[animationIndex], x - itemType.hitBox.offsetX(), (int) hitBox.y,
          CANNON_I.width, CANNON_I.height, null);
    }
  }

  public void startAnimation() {
    doAnimation = true;
  }
}
