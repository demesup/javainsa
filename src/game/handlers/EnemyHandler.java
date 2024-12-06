package game.handlers;


import game.Game;
import game.Level;
import game.interfaces.Resettable;
import game.interfaces.Updatable;
import game.objects.entity.Enemy;
import game.objects.entity.Player;

import java.awt.*;

import static utils.constant.EntityInfo.PLAYER_STATS;


public class EnemyHandler extends Handler implements Updatable, Resettable {
  private final Level level;

  public EnemyHandler(Game game, Level level) {
    super(game);
    this.level = level;
  }

  @Override
  public void update(int[][] levelData, Player player) {
    level.update(levelData, player);
  }

  @Override
  public void draw(Graphics graphics, int xLevelOffset) {
    level.draw(graphics, xLevelOffset);
  }

  public void checkEnemyHit(Player player) {
    for (Enemy mushroom : level.getEnemies())
      if (mushroom.isActive() && !mushroom.isReceivingDamage() && player.getAttackBox().intersects(mushroom.getHitBox())) {
        mushroom.receivedDamage(PLAYER_STATS.stats.damage());
        return;
      }
  }

  @Override
  public void resetAll() {
    level.getEnemies().forEach(Enemy::resetAll);
  }

  public void resetReceivingDamage() {
    level.getEnemies().forEach(m -> m.setReceivingDamage(false));
  }
}
