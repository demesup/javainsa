package game;

import game.objects.entity.Enemy;
import game.objects.entity.Eye;
import game.objects.entity.Mushroom;
import game.objects.entity.Player;
import game.objects.items.Spike;
import game.objects.items.cannons.Cannon;
import game.objects.items.cannons.LeftCannon;
import game.objects.items.cannons.RightCannon;
import game.objects.items.containers.GameBarrel;
import game.objects.items.containers.GameBox;
import game.objects.items.containers.GameContainer;
import game.objects.items.potions.BluePotion;
import game.objects.items.potions.Potion;
import game.objects.items.potions.RedPotion;
import utils.constant.Constants;
import utils.constant.EnemyType;
import utils.constant.ItemType;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import static utils.constant.ItemInfo.TILES_I;

public class Level {
  private final BufferedImage image;
  private final Game game;
  private final int[][] levelData;
  private final List<Enemy> enemies = new ArrayList<>();
  private final List<Potion> potions = new ArrayList<>();
  private final List<Spike> spikes = new ArrayList<>();
  private final List<Cannon> cannons = new ArrayList<>();
  private final List<GameContainer> containers = new ArrayList<>();


  private int maxLevelOffsetX;
  private Point spawn;

  public Level(BufferedImage image, Game game) {
    this.image = image;
    this.game = game;
    calculateLevelOffset();
    levelData = new int[image.getHeight()][image.getWidth()];
    loadLevelDataFromImage(image, this);
  }

  private static void loadLevelDataFromImage(BufferedImage image, Level level) {
    for (int j = 0; j < image.getHeight(); j++) {
      for (int i = 0; i < image.getWidth(); i++) {
        Color color = new Color(image.getRGB(i, j));
        int red = color.getRed();
        int green = color.getGreen();
        int blue = color.getBlue();

        loadLevelData(red, j, i, level);
        loadItems(blue, j, i, level);
        loadEntities(green, j, i, level);
      }
    }
  }

  private static void loadEntities(int green, int j, int i, Level level) {
    if (green == EnemyType.MUSHROOM.ordinal()) {
      level.enemies.add(new Mushroom(TILES_I.size * i, j * TILES_I.size, level.game));
    } else if (green == EnemyType.EYE.ordinal()) {
      level.enemies.add(new Eye(TILES_I.size * i, j * TILES_I.size, level.game));
    } else if (green == Constants.RgbImageConstants.PLAYER_SPAWN) {
      level.spawn = new Point(i * TILES_I.size, j * TILES_I.size);
    }
  }

  private static void loadLevelData(int red, int j, int i, Level level) {
    int value = red;
    if (value >= Constants.RgbImageConstants.MAX_TILE_RED) {
      value = 0;
    }
    level.levelData[j][i] = value;
  }

  private static void loadItems(int blue, int j, int i, Level level) {
    if (blue >= ItemType.values().length) {
      return;
    }

    switch (ItemType.values()[blue]) {
      case RED_POTION -> level.potions.add(new RedPotion(TILES_I.size * i, j * TILES_I.size));
      case BLUE_POTION -> level.potions.add(new BluePotion(TILES_I.size * i, j * TILES_I.size));
      case SPIKE -> level.spikes.add(new Spike(TILES_I.size * i, j * TILES_I.size));
      case CANNON_LEFT -> level.cannons.add(new LeftCannon(TILES_I.size * i, j * TILES_I.size));
      case CANNON_RIGHT -> level.cannons.add(new RightCannon(TILES_I.size * i, j * TILES_I.size));
      case BOX -> level.containers.add(new GameBox(TILES_I.size * i, j * TILES_I.size));
      case BARREL -> level.containers.add(new GameBarrel(TILES_I.size * i, j * TILES_I.size));
    }
  }

  private void calculateLevelOffset() {
    int maxTilesOffset;
    int levelTilesWide;
    levelTilesWide = image.getWidth();
    maxTilesOffset = levelTilesWide - TILES_I.defaultWidth;
    maxLevelOffsetX = TILES_I.size * maxTilesOffset;
  }

  public int getSpriteIndex(int x, int y) {
    return levelData[x][y];
  }

  public int[][] getLevelData() {
    return levelData;
  }

  public int getLevelOffset() {
    return maxLevelOffsetX;
  }

  public Point getPlayerSpawn() {
    return spawn;
  }

  public List<Enemy> getEnemies() {
    return enemies;
  }

  public List<Potion> getPotions() {
    return potions;
  }

  public List<GameContainer> getContainers() {
    return containers;
  }

  public List<Spike> getSpikes() {
    return spikes;
  }

  public List<Cannon> getCannons() {
    return cannons;
  }

  public void update(int[][] levelData, Player player) {
    List<Enemy> alive = enemies.stream().filter(Enemy::isActive).toList();
    if (alive.isEmpty()) {
      game.levelCompleted();
      return;
    }
    alive.forEach(m -> m.update(levelData, player));
  }

  public void draw(Graphics graphics, int xLevelOffset) {
    enemies.stream().filter(Enemy::isActive).forEach(m -> m.draw(graphics, xLevelOffset));
  }
}