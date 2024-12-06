package game.objects.entity;


import game.Game;
import game.objects.Observable;
import utils.constant.*;

import java.awt.geom.Rectangle2D;

import static utils.Utils.*;
import static utils.constant.Constants.GRAVITY;
import static utils.constant.Direction.LEFT;
import static utils.constant.Direction.RIGHT;
import static utils.constant.EnemyState.*;
import static utils.constant.EntityInfo.MUSHROOM_STATS;
import static utils.constant.ItemInfo.TILES_I;

public abstract class Enemy extends Entity implements Observable {
  protected final EnemyType enemyType;
  protected EnemyState enemyState;

  protected boolean firstUpdate = true;
  protected Direction direction;
  protected int tileY;
  protected float attackDistance = TILES_I.size;
  protected float sightDistance = attackDistance * 5;
  protected boolean active = true;
  protected boolean receivingDamage = false;

  protected Enemy(float x, float y, EnemyType enemyType, EntityInfo info, Game game) {
    super(x, y, info, game);
    this.enemyType = enemyType;
    this.direction = RIGHT;
  }

  @Override
  protected void updateAnimationTick() {
    animationTick++;
    if (animationTick >= animationSpeed) {
      animationTick = 0;
      animationIndex++;
      if (animationIndex >= enemyType.getSpriteAmount(enemyState)) {
        animationIndex = 0;
        switch (enemyState) {
          case ATTACK, HIT -> enemyState = enemyType == EnemyType.EYE ? RUN : IDLE;
          case DEAD -> active = false;
          default -> {
            // empty block
          }
        }
      }
    }
  }

  @Override
  public void update(int[][] levelData, Player player) {
    updateBehavior(levelData, player);
    updateAnimationTick();
    updateAttackBox();
  }

  public abstract void updateBehavior(int[][] levelData, Player player);

  protected void changeDirection() {
    direction = (direction == LEFT) ? RIGHT : LEFT;
  }

  protected void isFirstUpdate(int[][] levelData) {
    if (firstUpdate) {
      if (isEntityNotOnFloor(hitBox, levelData)) {
        inAir = true;
      }
      firstUpdate = false;
    }
  }

  protected void updateInAir(int[][] levelData) {
    if (canMoveHere(hitBox.x, hitBox.y + fallSpeed, hitBox.width, hitBox.height, levelData)) {
      hitBox.y += fallSpeed;
      fallSpeed += GRAVITY;
    } else {
      inAir = false;
      hitBox.y = getEntityYPositionUnderRoofOrAboveFloor(hitBox, fallSpeed);
      tileY = (int) (hitBox.y / TILES_I.size);
    }
  }

  protected void move(int[][] levelData) {
    float xSpeed = direction == LEFT ? -speed : speed;
    if (canMoveHere(hitBox.x + xSpeed, hitBox.y, hitBox.width, hitBox.height, levelData)
        && isFloor(hitBox, xSpeed, levelData)) {
      hitBox.x += xSpeed;
      return;
    }

    changeDirection();
  }

  @Override
  public boolean canSeePlayer(int[][] levelData, Player player) {
    return checkY(player) && isPlayerInSightRange(player) && isSightClear(hitBox, player.getHitBox(), tileY, levelData);
  }

  @Override
  public boolean checkY(Player player) {
    int playerTileY = (int) (player.getHitBox().y / TILES_I.size);
    return playerTileY == tileY;
  }

  public void receivedDamage(int value) {
    receivingDamage = true;
    currentHealth -= value;
    if (currentHealth <= 0) {
      game.getAudioPlayer().playEffect(GameSound.DIE);
      setState(DEAD);
    } else {
      setState(HIT);
    }
  }

  protected void turnToPlayer(Player player) {
    direction = player.getHitBox().x > hitBox.x ? RIGHT : LEFT;
  }

  @Override
  public boolean isPlayerInSightRange(Player player) {
    if (!checkY(player)) return false;
    int absValue = (int) Math.abs(player.getHitBox().x - this.hitBox.x);
    return absValue <= sightDistance;
  }

  protected boolean isPlayerInAttackRange(Player player) {
    int absValue = (int) Math.abs(player.getHitBox().x - this.hitBox.x);
    return absValue <= attackDistance * (enemyType == EnemyType.EYE ? 2 : 0.5);
  }

  protected void checkAttack(Rectangle2D.Float attackBox, Player player) {
    if (attackBox.intersects(player.getHitBox())) {
      player.receiveDamage(MUSHROOM_STATS.stats.damage());
      attackChecked = true;
    }
  }

  public EnemyState getState() {
    return enemyState;
  }

  @Override
  public void setState(EntityState enemyState) {
    this.enemyState = (EnemyState) enemyState;
    resetAnimationTick();
  }

  public boolean isActive() {
    return active;
  }

  public boolean isReceivingDamage() {
    return receivingDamage;
  }

  public void setReceivingDamage(boolean receivingDamage) {
    this.receivingDamage = receivingDamage;
  }

  @Override
  public void resetAll() {
    hitBox.x = x;
    hitBox.y = y;
    firstUpdate = true;
    active = true;
    fallSpeed = 0;
  }
}
