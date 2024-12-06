package game.handlers;


import game.Game;
import game.Level;
import game.state.Playing;
import utils.LoadSafe;
import utils.constant.GameImage;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import static utils.LoadSafe.getAllLevels;
import static utils.constant.ItemInfo.TILES_I;


public class LevelHandler extends Handler {
  private BufferedImage[] levelSprite;
  private List<Level> levels;
  private int levelIndex;

  public LevelHandler(Game game) {
    super(game);
    importOutsideSprites();
    buildLevels();
    levelIndex = 0;
  }

  public void loadNextLevel(Playing playing) {
    levelIndex++;
    if (levelIndex >= levels.size()) {
      levelIndex = 0;
      game.menu();
    }

    Level level = levels.get(levelIndex);
    playing.getPlayer().setSpawn(level.getPlayerSpawn());

    playing.getItemHandler().loadItems(level);
    playing.getPlayer().loadLevelData(level.getLevelData());
    playing.setMaxLevelOffset(level.getLevelOffset());
  }

  private void buildLevels() {
    levels = new ArrayList<>();
    for (BufferedImage bufferedImage : getAllLevels()) {
      Level apply = new Level(bufferedImage, game);
      levels.add(apply);
    }
  }

  private void importOutsideSprites() {
    BufferedImage image = LoadSafe.getSpriteAtlas(GameImage.OUTSIDE);

    levelSprite = new BufferedImage[48];
    for (int i = 0; i < 4; i++) {
      for (int j = 0; j < 12; j++) {
        levelSprite[i * 12 + j] = image.getSubimage(j * TILES_I.defaultSize, i * TILES_I.defaultSize, TILES_I.defaultSize, TILES_I.defaultSize);
      }
    }
  }

  @Override
  public void draw(Graphics g, int levelOffset) {
    Level level = levels.get(levelIndex);
    for (int j = 0; j < TILES_I.defaultHeight; j++) {
      for (int i = 0; i < level.getLevelData()[0].length; i++) {
        int index = level.getSpriteIndex(j, i);
        g.drawImage(levelSprite[index], TILES_I.size * i - levelOffset, TILES_I.size * j, TILES_I.size, TILES_I.size, null);
      }
    }
  }

  public Level getCurrentLevel() {
    return levels.get(levelIndex);
  }
}
