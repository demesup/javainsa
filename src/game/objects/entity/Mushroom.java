package game.objects.entity;


import game.Game;
import utils.LoadSafe;
import utils.constant.Direction;
import utils.constant.GameImage;

import java.awt.*;
import java.awt.image.BufferedImage;

import static utils.constant.Constants.SCALE;
import static utils.constant.EnemyState.*;
import static utils.constant.EnemyType.MUSHROOM;
import static utils.constant.EntityInfo.MUSHROOM_STATS;
import static utils.constant.ItemInfo.MUSHROOM_I;

public class Mushroom extends Enemy {
  protected static final BufferedImage[][] mushroomAnimations;

  static {
    mushroomAnimations = new BufferedImage[5][8];
    BufferedImage temp = LoadSafe.getSpriteAtlas(GameImage.MUSHROOM);

    for (int i = 0; i < mushroomAnimations.length; i++) {
      for (int j = 0; j < mushroomAnimations[i].length; j++) {
        mushroomAnimations[i][j] = temp.getSubimage(j * MUSHROOM_I.defaultWidth, i * MUSHROOM_I.defaultHeight, MUSHROOM_I.defaultWidth, MUSHROOM_I.defaultHeight);
      }
    }
  }

  public Mushroom(float x, float y, Game game) {
    super(x, y, MUSHROOM, MUSHROOM_STATS, game);
    initHitBox(MUSHROOM_STATS.hitBox.width(), MUSHROOM_STATS.hitBox.height());
    initAttackBox(x, y, MUSHROOM_STATS.attackBox.width(), MUSHROOM_STATS.attackBox.height());
    enemyState = IDLE;
  }

  @Override
  public void updateBehavior(int[][] levelData, Player player) {
    isFirstUpdate(levelData);

    if (inAir) {
      updateInAir(levelData);
      return;
    }

    updateState(levelData, player);
  }

  private void updateState(int[][] levelData, Player player) {
    switch (enemyState) {
      case IDLE -> enemyState = RUN;
      case RUN -> {
        if (canSeePlayer(levelData, player)) {
          turnToPlayer(player);
          if (isPlayerInAttackRange(player)) {
            setState(ATTACK);
          }
        }
        move(levelData);
      }
      case ATTACK -> {
        if (animationIndex == 0) attackChecked = false;
        if (animationIndex == 3 && !attackChecked) {
          checkAttack(attackBox, player);
        }
      }
      case HIT, DEAD -> {
        // do nothing
      }
      default -> throw new IllegalStateException("Unexpected value: " + enemyState);
    }
  }

  public int flipX() {
    return direction == Direction.RIGHT ? width : 0;
  }

  public int flipW() {
    return direction == Direction.RIGHT ? 1 : -1;
  }

  @Override
  public void draw(Graphics graphics, int xLevelOffset) {
    graphics.drawImage(
        mushroomAnimations[enemyState.ordinal()][animationIndex],
        (int) (hitBox.x - (direction == Direction.RIGHT ? (int) (115 * SCALE) : MUSHROOM_STATS.hitBox.offsetX())) - xLevelOffset + flipX(),
        (int) hitBox.y - MUSHROOM_STATS.hitBox.offsetY(),
        MUSHROOM_I.width * flipW(), MUSHROOM_I.height,
        null
    );
  }

  @Override
  protected void updateAttackBox() {
    if (direction == Direction.RIGHT) {
      attackBox.x = hitBox.x + hitBox.width + attackBoxOffsetX;
    } else {
      attackBox.x = hitBox.x - attackBox.width - attackBoxOffsetX;
    }
    attackBox.y = hitBox.y;
  }

  @Override
  public void resetAll() {
    super.resetAll();
    currentHealth = MUSHROOM_STATS.stats.maxHealth();
    enemyState = IDLE;
  }
}
