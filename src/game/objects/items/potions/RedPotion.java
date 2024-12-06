package game.objects.items.potions;

import game.objects.entity.Player;
import utils.constant.ItemType;

import static utils.constant.Constants.PotionValues.RED_POTION;
import static utils.constant.Constants.SCALE;

public class RedPotion extends Potion {
  public RedPotion(float x, float y) {
    super(x, y, ItemType.RED_POTION);
    maxHoverOffset = (int) (10 * SCALE);
  }

  @Override
  public void applyAffect(Player player) {
    visible = false;
    player.changeHealth(RED_POTION);
  }
}
