package utils.constant;

import static utils.constant.Constants.SCALE;

public enum EntityInfo {
  PLAYER_STATS(
      new Stats(100, 10, SCALE, 0),
      50, 45,
      new Box(10, 29, 20, 14),
      new Box(50, 29, -20, 0),
      25
  ),
  MUSHROOM_STATS(
      new Stats(20, 15, SCALE * 0.35f, 0),
      50, 501,
      new Box(20, 29, -85, 70),
      new Box(30, 29, -10, 20),
      25
  ),
  EYE_STATS(
      new Stats(50, 15, SCALE * 0.35f, 0),
      50, 501,
      new Box(20, 29, -85, 70),
      new Box(30, 29, -10, 20),
      25
  );

  public final Stats stats;
  public final int width;
  public final int height;
  public final Box hitBox;
  public final Box attackBox;
  public final int animationSpeed;

  EntityInfo(Stats stats, int width, int height, Box hitBox, Box attackBox, int animationSpeed) {
    this.stats = stats;
    this.width = (int) (width * SCALE);
    this.height = (int) (height * SCALE);
    this.hitBox = hitBox;
    this.attackBox = attackBox;
    this.animationSpeed = animationSpeed;
  }
}
