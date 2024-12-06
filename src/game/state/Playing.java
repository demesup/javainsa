package game.state;


import game.Game;
import game.Level;
import game.handlers.EnemyHandler;
import game.handlers.ItemHandler;
import game.handlers.LevelHandler;
import game.objects.entity.Player;
import game.state.playingutils.BarHandler;
import game.state.playingutils.CloudHandler;
import utils.LoadSafe;
import utils.constant.GameImage;
import utils.constant.GameSound;
import utils.constant.Song;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import static utils.Utils.checkCloseToBorder;
import static utils.constant.Constants.GAME_HEIGHT;
import static utils.constant.Constants.GAME_WIDTH;

public class Playing extends State {
  private final BufferedImage background;
  private final CloudHandler cloudHandler;
  private final BarHandler barHandler;

  private LevelHandler levelHandler;
  private EnemyHandler enemyHandler;
  private ItemHandler itemHandler;

  private Player player;
  private boolean paused = false;
  private boolean playerDying = false;
  private boolean gameOver = false;
  private boolean levelCompleted = false;
  private int xLvlOffset;
  private int maxLevelOffsetX;

  public Playing(Game game) {
    super(game);
    initClasses();

    background = LoadSafe.getSpriteAtlas(GameImage.PLAYING_BACKGROUND);
    cloudHandler = new CloudHandler();
    barHandler = new BarHandler();

    calculateLevelOffset();
    loadLevel();
  }

  @Override
  public void run() {
    game.playing();
  }

  private void loadLevel() {
    itemHandler.loadItems(levelHandler.getCurrentLevel());
  }

  private void calculateLevelOffset() {
    maxLevelOffsetX = levelHandler.getCurrentLevel().getLevelOffset();
  }

  private void initClasses() {
    levelHandler = new LevelHandler(game);
    itemHandler = new ItemHandler(game);

    Level currentLevel = levelHandler.getCurrentLevel();
    enemyHandler = new EnemyHandler(game, currentLevel);

    Point spawn = currentLevel.getPlayerSpawn();
    player = new Player(spawn.x, spawn.y, game);
    player.loadLevelData(currentLevel.getLevelData());
  }

  public Player getPlayer() {
    return player;
  }

  public ItemHandler getItemHandler() {
    return itemHandler;
  }

  @Override
  public void windowFocusLost() {
    player.resetDirBooleans();
  }

  @Override
  public void update() {
    if (game.overlayOpened()) {
      game.getCurrentOverlay().update();
      return;
    }

    if (playerDying) {
      player.update();
      return;
    }

    int[][] levelData = levelHandler.getCurrentLevel().getLevelData();
    player.update();
    itemHandler.update(levelData, player);
    enemyHandler.update(levelData, player);
    xLvlOffset = checkCloseToBorder(player, xLvlOffset, maxLevelOffsetX);
  }

  @Override
  public void checkEnemyHit() {
    enemyHandler.checkEnemyHit(player);
  }

  @Override
  public void draw(Graphics graphics) {
    graphics.drawImage(background, 0, 0, GAME_WIDTH, GAME_HEIGHT, null);
    drawHandlers(graphics);
    drawOverlay(graphics);
  }

  private void drawOverlay(Graphics graphics) {
    if (paused) {
      game.getPauseOverlay().draw(graphics);
    } else if (gameOver) {
      game.getGameOverOverlay().draw(graphics);
    } else if (levelCompleted) {
      game.getCompletedOverlay().draw(graphics);
    }
  }

  private void drawHandlers(Graphics graphics) {
    cloudHandler.drawClouds(graphics, xLvlOffset);
    levelHandler.draw(graphics, xLvlOffset);
    player.draw(graphics, xLvlOffset);
    itemHandler.draw(graphics, xLvlOffset);
    enemyHandler.draw(graphics, xLvlOffset);
    barHandler.draw(graphics);
  }

  @Override
  public void mouseClicked(MouseEvent e) {
    if (gameOver) {
      return;
    }
    if (e.getButton() == MouseEvent.BUTTON1) {
      player.setAttacking(true);
    } else if (e.getButton() == MouseEvent.BUTTON3) {
      player.powerAttack();
    }
  }

  @Override
  public void mousePressed(MouseEvent e) {
    if (game.overlayOpened()) {
      game.getCurrentOverlay().mousePressed(e);
    }
  }

  @Override
  public void mouseReleased(MouseEvent e) {
    if (game.overlayOpened()) {
      game.getCurrentOverlay().mouseReleased(e);
    }
  }

  @Override
  public void mouseDragged(MouseEvent e) {
    if (gameOver) {
      return;
    }
    if (paused) {
      game.getPauseOverlay().mouseDragged(e);
    }
  }

  @Override
  public void mouseMoved(MouseEvent e) {
    if (game.overlayOpened()) {
      game.getCurrentOverlay().mouseMoved(e);
    }
  }

  @Override
  public void keyPressed(KeyEvent e) {
    if (gameOver) {
      game.getGameOverOverlay().keyPressed(e);
      return;
    }
    switch (e.getKeyCode()) {
      case KeyEvent.VK_A, KeyEvent.VK_LEFT -> player.setLeft(true);
      case KeyEvent.VK_D, KeyEvent.VK_RIGHT -> player.setRight(true);
      case KeyEvent.VK_SPACE, KeyEvent.VK_W, KeyEvent.VK_UP -> player.setJump(true);

      case KeyEvent.VK_BACK_SPACE -> game.menu();
      case KeyEvent.VK_ESCAPE -> {
        paused = !paused;
        game.pausedOverlay();
      }
      default -> {
        // empty block
      }
    }
  }

  @Override
  public void keyReleased(KeyEvent e) {
    if (gameOver) {
      return;
    }
    switch (e.getKeyCode()) {
      case KeyEvent.VK_A, KeyEvent.VK_LEFT -> player.setLeft(false);
      case KeyEvent.VK_D, KeyEvent.VK_RIGHT -> player.setRight(false);
      case KeyEvent.VK_SPACE, KeyEvent.VK_W, KeyEvent.VK_UP -> player.setJump(false);
      default -> {
        // empty block
      }
    }
  }

  @Override
  public void unpauseGame() {
    paused = false;
  }

  public void restart() {
    resetAll();
    unpauseGame();
    gameOver = false;
    game.closeOverlay();
    game.getAudioPlayer().playSong(Song.LEVEL);
  }

  @Override
  public void resetAll() {
    player.resetAll();
    enemyHandler.resetAll();
    itemHandler.resetAll();
    paused = false;
    levelCompleted = false;
    playerDying = false;
    gameOver = false;
  }

  public void setGameOver(boolean gameOver) {
    this.gameOver = gameOver;
    game.gameOver();
  }

  public void setLevelCompleted(boolean levelCompleted) {
    this.levelCompleted = levelCompleted;
  }

  public void setMaxLevelOffset(int levelOffset) {
    this.maxLevelOffsetX = levelOffset;
  }

  public void loadNextLevel() {
    resetAll();
    levelHandler.loadNextLevel(this);
  }

  public void checkPotionTouched(Player player) {
    itemHandler.checkPotionTouched(player.getHitBox());
  }

  public boolean checkContainerHit(Player player) {
    return itemHandler.checkContainerHit(player.getAttackBox());
  }

  public void checkSpikeTouched(Player player) {
    itemHandler.checkSpikeTouched(player);
  }

  public void setPlayerDying(boolean playerDying) {
    this.playerDying = playerDying;
    game.getAudioPlayer().stopSong();
    game.getAudioPlayer().playEffect(GameSound.DEATH);
  }

  public void resetReceivingDamage() {
    enemyHandler.resetReceivingDamage();
  }

  public BarHandler getBarHandler() {
    return barHandler;
  }
}
