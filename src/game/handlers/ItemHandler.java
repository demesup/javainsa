package game.handlers;

import game.Game;
import game.Level;
import game.interfaces.Resettable;
import game.interfaces.Updatable;
import game.objects.entity.Entity;
import game.objects.entity.Player;
import game.objects.items.Spike;
import game.objects.items.cannons.Ball;
import game.objects.items.cannons.Cannon;
import game.objects.items.containers.GameContainer;
import game.objects.items.potions.Potion;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;

import static utils.constant.Constants.CANNON_DAMAGE;

public class ItemHandler extends Handler implements Updatable, Resettable {
  private final List<Ball> balls;
  private List<Potion> potions;
  private List<GameContainer> containers;
  private List<Spike> spikes;
  private List<Cannon> cannons;


  public ItemHandler(Game game) {
    super(game);
    balls = new ArrayList<>();
  }

  public void checkSpikeTouched(Entity entity) {
    for (Spike spike : spikes) {
      if (entity.getHitBox().intersects(spike.getHitBox())) {
        entity.dead();
        return;
      }
    }
  }

  public void checkPotionTouched(Rectangle2D.Float hitBox) {
    for (Potion potion : potions) {
      if (potion.isVisible() && hitBox.intersects(potion.getHitBox())) {
        potion.applyAffect(game.getPlaying().getPlayer());
        return;
      }
    }
  }

  public boolean checkContainerHit(Rectangle2D.Float attackBox) {
    for (GameContainer container : containers) {
      if (container.operable() && attackBox.intersects(container.getHitBox())) {
        Potion open = container.open();

        potions.add(open);
        return true;
      }
    }
    return false;
  }

  @Override
  public void update(int[][] levelData, Player player) {
    updateContainers();
    updatePotions();
    updateCannons(levelData, player);
    updateBalls(levelData, player);
  }

  private void updateBalls(int[][] levelData, Player player) {
    for (Ball ball : balls) {
      if (ball.isVisible()) {
        ball.update();

        if (ball.getHitBox().intersects(player.getHitBox())) {
          player.changeHealth(-CANNON_DAMAGE);
          ball.setVisible(false);
        } else if (ball.collides(levelData)) {
          ball.setVisible(false);
        }
      }
    }
  }

  private void updateCannons(int[][] levelData, Player player) {
    for (Cannon cannon : cannons) {
      cannon.update(levelData, player);
      if (cannon.isAnimating() && cannon.getAnimationIndex() == 4 && cannon.getAnimationTick() == 0) {
        balls.add(cannon.shoot());
      }

      if (!cannon.isAnimating() && cannon.canSeePlayer(levelData, player)) {
        cannon.startAnimation();
      }
    }
  }

  private void updatePotions() {
    for (Potion potion : potions) {
      if (potion.isVisible()) {
        potion.update();
      }
    }
  }

  private void updateContainers() {
    for (GameContainer container : containers) {
      if (container.isVisible()) {
        container.update();
      }
    }
  }

  @Override
  public void draw(Graphics graphics, int xLevelOffset) {
    drawPotions(graphics, xLevelOffset);
    drawContainers(graphics, xLevelOffset);
    drawSpikes(graphics, xLevelOffset);
    drawCannons(graphics, xLevelOffset);
    drawBalls(graphics, xLevelOffset);
  }

  private void drawBalls(Graphics graphics, int xLevelOffset) {
    for (Ball ball : balls) {
      if (ball.isVisible()) {
        ball.draw(graphics, xLevelOffset);
      }
    }
  }

  private void drawCannons(Graphics graphics, int xLevelOffset) {
    for (Cannon cannon : cannons) {
      cannon.draw(graphics, xLevelOffset);
    }
  }

  private void drawSpikes(Graphics graphics, int xLevelOffset) {
    for (Spike spike : spikes) {
      spike.draw(graphics, xLevelOffset);
    }
  }

  private void drawPotions(Graphics graphics, int xLevelOffset) {
    for (Potion potion : potions) {
      if (potion.isVisible()) {
        potion.draw(graphics, xLevelOffset);
      }
    }
  }

  private void drawContainers(Graphics graphics, int xLevelOffset) {
    for (GameContainer container : containers) {
      if (container.isVisible()) {
        container.draw(graphics, xLevelOffset);
      }
    }
  }

  public void loadItems(Level level) {
    potions = level.getPotions();
    containers = level.getContainers();
    spikes = level.getSpikes();
    cannons = level.getCannons();
    balls.clear();
  }

  @Override
  public void resetAll() {
    potions.forEach(Resettable::resetAll);
    containers.forEach(Resettable::resetAll);
    cannons.forEach(Resettable::resetAll);
    balls.clear();
  }
}
