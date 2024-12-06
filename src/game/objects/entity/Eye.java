package game.objects.entity;


import game.Game;
import utils.LoadSafe;
import utils.constant.Direction;
import utils.constant.GameImage;

import java.awt.*;
import java.awt.image.BufferedImage;

import static utils.Utils.canMoveHere;
import static utils.Utils.isFloor;
import static utils.constant.Constants.EYE_ATTACK_MULTIPLIER;
import static utils.constant.Constants.SCALE;
import static utils.constant.EnemyState.ATTACK;
import static utils.constant.EnemyState.RUN;
import static utils.constant.EnemyType.EYE;
import static utils.constant.EntityInfo.EYE_STATS;
import static utils.constant.ItemInfo.EYE_I;

public class Eye extends Enemy {
  protected static final BufferedImage[][] eyeAnimations;

  static {
    eyeAnimations = new BufferedImage[4][8];
    BufferedImage temp = LoadSafe.getSpriteAtlas(GameImage.EYE);

    for (int i = 0; i < eyeAnimations.length; i++) {
      for (int j = 0; j < eyeAnimations[i].length; j++) {
        eyeAnimations[i][j] = temp.getSubimage(j * EYE_I.defaultWidth, i * EYE_I.defaultHeight, EYE_I.defaultWidth, EYE_I.defaultHeight);
      }
    }
  }

  public Eye(float x, float y, Game game) {
    super(x, y, EYE, EYE_STATS, game);
    initHitBox(EYE_STATS.hitBox.width(), EYE_STATS.hitBox.height());
    initAttackBox(x, y, EYE_STATS.attackBox.width(), EYE_STATS.attackBox.height());
    enemyState = RUN;
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
        if (animationIndex == 4) {
          if (!attackChecked) {
            checkAttack(attackBox, player);
          }
          attackMove(levelData);
        }
      }
      case HIT, DEAD -> {
        // do nothing
      }
      default -> throw new IllegalStateException("Unexpected value: " + enemyState);
    }
  }

  private void attackMove(int[][] levelData) {
    float xSpeed = attackSpeed();
    if (canMoveHere(hitBox.x + xSpeed, hitBox.y, hitBox.width, hitBox.height, levelData) && isFloor(hitBox, xSpeed, levelData)) {
      hitBox.x += xSpeed;
      return;
    }
    setState(RUN);
  }

  private float attackSpeed() {
    return (direction == Direction.LEFT ? -1 : 1) * speed * EYE_ATTACK_MULTIPLIER;
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
        eyeAnimations[enemyState.ordinal() - 1][animationIndex],
        (int) (hitBox.x - (direction == Direction.RIGHT ? (int) (115 * SCALE) : EYE_STATS.hitBox.offsetX())) - xLevelOffset + flipX(),
        (int) hitBox.y - EYE_STATS.hitBox.offsetY(),
        EYE_I.width * flipW(), EYE_I.height,
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
    currentHealth = EYE_STATS.stats.maxHealth();
    enemyState = RUN;
  }


}
