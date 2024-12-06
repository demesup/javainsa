package utils.constant;

import java.awt.image.BufferedImage;

import static utils.LoadSafe.getSpriteAtlas;
import static utils.constant.ItemInfo.PLAYER_I;


public enum PlayerState implements EntityState {
  IDLE(8, "player/idle.png"),
  RUN(6, "player/run.png"),
  JUMP(4, "player/jump.png"),
  FALL(2, "player/fall.png"),
  ATTACK(11, "player/attack.png"),
  HIT(3, "player/hit.png"),
  DEAD(7, "player/death.png");

  final int spriteAmount;
  final String path;
  BufferedImage[] animation;

  PlayerState(int spriteAmount, String path) {
    this.spriteAmount = spriteAmount;
    this.path = path;
  }

  public int getSpriteAmount() {
    return spriteAmount;
  }

  public void loadAnimation() {
    BufferedImage img = getSpriteAtlas(path);
    BufferedImage[] animations = new BufferedImage[spriteAmount];
    for (int i = 0; i < animations.length; i++) {
      animations[i] = img.getSubimage(i * PLAYER_I.defaultWidth, 0, PLAYER_I.defaultWidth, PLAYER_I.defaultHeight);
    }
    this.animation = animations;
  }

  public BufferedImage[] getAnimation() {
    return animation;
  }
}
