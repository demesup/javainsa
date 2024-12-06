package utils.constant;

public enum ItemType {
  BLUE_POTION(15, new ItemBox(7, 14, 3, 2), 7),
  RED_POTION(15, new ItemBox(7, 14, 3, 2), 7),
  BARREL(15, new ItemBox(23, 25, 8, 5), 8),
  BOX(15, new ItemBox(25, 18, 7, 12), 8),
  SPIKE(0, new ItemBox(32, 16, 0, 16), 1),
  CANNON_RIGHT(15, new ItemBox(25, 18, 4, 6), 7),
  CANNON_LEFT(15, new ItemBox(25, 18, 4, 6), 7),
  BALL(0, new ItemBox(15, 15, 0, 0), 1);

  public final int animationSpeed;
  public final ItemBox hitBox;
  public final int spriteAmount;

  ItemType(int animationSpeed, ItemBox hitBox, int spriteAmount) {
    this.animationSpeed = animationSpeed;
    this.hitBox = hitBox;
    this.spriteAmount = spriteAmount;
  }

}
