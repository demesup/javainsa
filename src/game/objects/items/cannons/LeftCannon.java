package game.objects.items.cannons;

import game.objects.entity.Player;

import static utils.constant.Constants.CANNON_SIGHT_DISTANCE;
import static utils.constant.Constants.PositionConstants.CANNON_Y_OFFSET;
import static utils.constant.Constants.PositionConstants.LEFT_CANNON_X_OFFSET;
import static utils.constant.ItemType.CANNON_LEFT;

public class LeftCannon extends Cannon {

  public LeftCannon(float x, float y) {
    super(x, y, CANNON_LEFT);
    hitBox.x += CANNON_LEFT.hitBox.offsetX();
  }


  @Override
  public boolean isPlayerInSightRange(Player player) {
    if (!checkY(player)) return false;
    if (player.getHitBox().x <= hitBox.x) return false;
    int absValue = (int) Math.abs(player.getHitBox().x - this.hitBox.x);
    return absValue <= CANNON_SIGHT_DISTANCE;
  }


  public Ball shoot() {
    return new Ball(x + LEFT_CANNON_X_OFFSET, y + CANNON_Y_OFFSET, 1);
  }
}
