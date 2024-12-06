package game.objects.items.containers;

import game.objects.items.potions.BluePotion;
import game.objects.items.potions.Potion;
import utils.constant.ItemType;

public class GameBox extends GameContainer {
  public GameBox(float x, float y) {
    super(x, y, ItemType.BOX);
  }

  @Override
  public Potion open() {
    doAnimation = true;
    return new BluePotion(hitBox.x + hitBox.width / 3, hitBox.y);
  }
}
