package game.objects.items.potions;

import game.objects.entity.Player;
import utils.constant.ItemType;

import static utils.constant.Constants.PotionValues.BLUE_POTION;
import static utils.constant.Constants.SCALE;

public class BluePotion extends Potion {
  public BluePotion(float x, float y) {
    super(x, y, ItemType.BLUE_POTION);
    maxHoverOffset = (int) (6 * SCALE);
  }

  @Override
  public void applyAffect(Player player) {
    visible = false;
    player.changePower(BLUE_POTION);
  }
}
