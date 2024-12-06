package game.interfaces;

import game.objects.entity.Player;

public interface Updatable {
  default void update() {
  }

  default void update(int[][] levelData, Player player) {
  }
}
