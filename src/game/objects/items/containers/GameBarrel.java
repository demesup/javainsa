package game.objects.items.containers;

import game.objects.items.potions.Potion;
import game.objects.items.potions.RedPotion;
import utils.constant.ItemType;

public class GameBarrel extends GameContainer {
  public GameBarrel(float x, float y) {
    super(x, y, ItemType.BARREL);
  }

  @Override
  public Potion open() {
    doAnimation = true;
    return new RedPotion(hitBox.x + hitBox.width / 3, hitBox.y);
  }
}
