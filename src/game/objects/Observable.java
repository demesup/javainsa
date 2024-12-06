package game.objects;

import game.objects.entity.Player;

public interface Observable {
  boolean isPlayerInSightRange(Player player);

  boolean checkY(Player player);

  boolean canSeePlayer(int[][] levelData, Player player);
}
