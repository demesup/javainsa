package game.objects.entity;


import game.Game;
import game.state.playingutils.BarHandler;
import utils.constant.EntityState;
import utils.constant.PlayerState;

import java.awt.*;
import java.util.Arrays;

import static utils.Utils.*;
import static utils.constant.Constants.*;
import static utils.constant.EntityInfo.PLAYER_STATS;
import static utils.constant.PlayerState.*;


public class Player extends Entity {

  private int currentPower;

  private PlayerState playerState;
  private boolean moving;
  private boolean attacking;
  private boolean powerAttack;
  private int powerAttackTick;
  private int powerGrowTick;

  private boolean left;
  private boolean right;
  private boolean jump;

  private int[][] levelData;

  private int flipX = 0;
  private int flipW = 1;

  public Player(float x, float y, Game game) {
    super(x, y, PLAYER_STATS, game);
    playerState = IDLE;

    loadAnimations();
    initHitBox(PLAYER_STATS.hitBox.width(), PLAYER_STATS.hitBox.height());
    initAttackBox(x, y, PLAYER_STATS.attackBox.width(), PLAYER_STATS.attackBox.height());

    moving = false;
    attacking = false;
    powerAttack = false;
    powerAttackTick = 0;
    currentPower = MAX_POWER;
  }

  public void setSpawn(Point spawn) {
    this.x = spawn.x;
    this.y = spawn.y;
    updateHitBox();
    updateAttackBox();
  }


  @Override
  public void update() {
    updateHealthBar();
    updatePowerBar();

    if (currentHealth <= 0) {
      updateDying();
      return;
    }

    updateAttackBox();
    updatePosition();

    runChecks();

    updateAnimationTick();
    setAnimation();
  }

  /*
   * Checks interaction with other game objects
   * */
  private void runChecks() {
    checkPotionTouched();
    checkSpikeTouched();

    if (attacking || powerAttack) {
      checkAttack();
      if (!attackChecked) checkContainerHit();
    }
  }

  private void updateDying() {
    if (playerState != DEAD) {
      playerState = DEAD;
      resetAnimationTick();
      game.getPlaying().setPlayerDying(true);
      return;
    }

    if (animationIndex == DEAD.getSpriteAmount() - 1 && animationTick >= animationSpeed - 1) {
      game.getPlaying().setGameOver(true);
      return;
    }

    updateAnimationTick();
  }

  private void checkSpikeTouched() {
    game.getPlaying().checkSpikeTouched(this);
  }

  private void checkPotionTouched() {
    game.getPlaying().checkPotionTouched(this);
  }

  private void checkContainerHit() {
    if (!attackChecked && game.getPlaying().checkContainerHit(this)) {
      attacking = true;
    }
  }

  private void checkAttack() {
    game.getAudioPlayer().playAttack();
    if (attackChecked || animationIndex != 1) {
      game.getPlaying().checkEnemyHit();
      return;
    }
    attackChecked = !powerAttack;
  }

  @Override
  protected void updateAttackBox() {
    if (flipX == 0) {
      attackBox.x = hitBox.x + hitBox.width + attackBoxOffsetX;
    } else {
      attackBox.x = hitBox.x - attackBox.width - attackBoxOffsetX;
    }
    attackBox.y = hitBox.y;
  }

  private BarHandler getBarHandler() {
    return game.getPlaying().getBarHandler();
  }

  private void updateHealthBar() {
    getBarHandler().updateHealth(currentHealth);
  }

  private void updatePowerBar() {
    getBarHandler().updatePower(currentPower);

    powerGrowTick++;
    if (powerGrowTick >= POWER_GROW_SPEED) {
      powerGrowTick = 0;
      changePower(1);
    }
  }

  @Override
  public void draw(Graphics graphics, int xLevelOffset) {
    graphics.drawImage(playerState.getAnimation()[animationIndex],
        (int) (hitBox.x - PLAYER_STATS.hitBox.offsetX()) - xLevelOffset + flipX,
        (int) (hitBox.y - PLAYER_STATS.hitBox.offsetY()),
        width * flipW, height, null);
  }


  @Override
  protected void updateAnimationTick() {
    animationTick++;
    if (animationTick >= animationSpeed) {
      animationTick = 0;
      animationIndex++;
      if (animationIndex >= playerState.getSpriteAmount()) {
        resetAnimation();
      }
    }
  }

  private void resetAnimation() {
    if (playerState == HIT) {
      playerState = IDLE;
    } else if (playerState == ATTACK) {
      game.getPlaying().resetReceivingDamage();
    }
    animationIndex = 0;
    attacking = false;
    attackChecked = false;
  }

  private void setAnimation() {
    PlayerState startAni = playerState;
    playerState = moving ? RUN : IDLE;
    if (inAir) {
      playerState = fallSpeed < 0 ? JUMP : FALL;
    }
    if (attacking) {
      playerState = ATTACK;
      if (startAni != ATTACK) {
        animationIndex = 1;
        animationTick = 0;
        return;
      }
    }
    if (startAni != playerState) resetAnimationTick();
  }


  private void updatePosition() {
    moving = false;
    handleJump();
    if (shouldNotMove()) {
      return;
    }

    float xSpeed = calculateXSpeed();
    if (powerAttack) {
      xSpeed = powerUpXSpeed(xSpeed);
    }

    checkIsInAir();
    if (inAir && !powerAttack) {
      updateInAirPosition(xSpeed);
    } else {
      updateXPosition(xSpeed);
    }
    moving = true;
  }

  private float powerUpXSpeed(float xSpeed) {
    if (!left && !right) {
      xSpeed = flipW == -1 ? -speed : speed;
    }

    xSpeed *= 3;
    powerAttackTick++;
    if (powerAttackTick >= 25 || runsToBorder()) {
      powerAttackTick = 0;
      powerAttack = false;
    }

    return xSpeed;
  }

  private void checkIsInAir() {
    if (!inAir && isEntityNotOnFloor(hitBox, levelData)) {
      inAir = true;
    }
  }

  private float calculateXSpeed() {
    float xSpeed = 0;

    if (left) {
      xSpeed -= speed;
      flipX = width;
      flipW = -1;
    }
    if (right) {
      xSpeed += speed;
      flipX = 0;
      flipW = 1;
    }
    return xSpeed;
  }

  private boolean shouldNotMove() {
    return !inAir && !powerAttack && ((!left && !right) || (right && left));
  }

  private boolean runsToBorder() {
    if (inAir) return false;
    return !isSolid(hitBox.x, hitBox.y + getHitBox().height + 1, levelData)
        || !isSolid(hitBox.x + hitBox.width, hitBox.y + getHitBox().height + 1, levelData);
  }

  private void updateInAirPosition(float xSpeed) {
    if (canMoveHere(hitBox.x, hitBox.y + fallSpeed, hitBox.width, hitBox.height, levelData)) {
      hitBox.y += fallSpeed;
      fallSpeed += GRAVITY;
    } else {
      hitBox.y = getEntityYPositionUnderRoofOrAboveFloor(hitBox, fallSpeed);
      if (fallSpeed > 0) {
        resetInAir();
      } else {
        fallSpeed = FALL_SPEED;
      }
    }
    updateXPosition(xSpeed);
  }

  private void handleJump() {
    if (!jump || inAir) return;
    inAir = true;
    fallSpeed = PLAYER_JUMP_SPEED;
  }

  private void resetInAir() {
    inAir = false;
    fallSpeed = 0;
  }

  private void updateXPosition(float xSpeed) {
    if (canMoveHere(hitBox.x + xSpeed, hitBox.y, hitBox.width, hitBox.height, levelData)) {
      hitBox.x += xSpeed;
    } else {
      hitBox.x = getEntityXPositionNextToWall(hitBox, xSpeed);
    }
  }

  public void changeHealth(int value) {
    currentHealth += value;

    if (currentHealth <= 0)
      currentHealth = 0;
    else if (currentHealth >= PLAYER_STATS.stats.maxHealth())
      currentHealth = PLAYER_STATS.stats.maxHealth();
  }


  public void changePower(int value) {
    currentPower += value;
    if (currentPower >= MAX_POWER)
      currentPower = MAX_POWER;
    else if (currentPower <= 0)
      currentPower = 0;
  }

  private void loadAnimations() {
    Arrays.stream(PlayerState.values()).forEach(PlayerState::loadAnimation);
  }

  public void loadLevelData(int[][] levelData) {
    this.levelData = levelData;
    if (isEntityNotOnFloor(hitBox, levelData)) {
      inAir = true;
    }
  }

  public void resetDirBooleans() {
    left = false;
    right = false;
  }

  public void setAttacking(boolean attacking) {
    this.attacking = attacking;
  }


  public void setLeft(boolean left) {
    this.left = left;
  }


  public void setRight(boolean right) {
    this.right = right;
  }


  public void setJump(boolean jump) {
    this.jump = jump;
  }

  @Override
  public void resetAll() {
    resetBooleans();
    setState(IDLE);
    currentHealth = PLAYER_STATS.stats.maxHealth();
    currentPower = MAX_POWER;
    flipX = 0;
    flipW = 1;

    updateHitBox();
    updateAttackBox();
    if (isEntityNotOnFloor(hitBox, levelData)) {
      inAir = true;
    }
  }

  @Override
  public void setState(EntityState state) {
    playerState = (PlayerState) state;
    resetAnimationTick();
  }

  private void resetBooleans() {
    resetDirBooleans();
    inAir = false;
    attacking = false;
    moving = false;
    jump = false;
  }

  public void powerAttack() {
    if (powerAttack) {
      return;
    }

    if (currentPower >= POWER_ATTACK_VALUE) {
      powerAttack = true;
      changePower(-POWER_ATTACK_VALUE);
    }
  }

  public void receiveDamage(int i) {
    changeHealth(-i);
    setState(HIT);
  }
}
