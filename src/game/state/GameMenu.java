package game.state;


import game.Game;
import game.button.GameButton;
import game.button.MenuButton;
import utils.constant.GameImage;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import static utils.LoadSafe.getSpriteAtlas;
import static utils.constant.Constants.*;
import static utils.constant.Constants.PositionConstants.*;


public class GameMenu extends State {
  private BufferedImage plateImage;
  private int menuX;
  private int menuY;
  private int menuWidth;
  private int menuHeight;

  public GameMenu(Game game) {
    super(game);
    loadButtons();
    loadPlate();
  }

  @Override
  public void run() {
    game.menu();
  }

  private void loadPlate() {
    plateImage = getSpriteAtlas(GameImage.MENU_PLATE);
    menuWidth = (int) (plateImage.getWidth() * SCALE);
    menuHeight = (int) (plateImage.getHeight() * SCALE);
    menuX = GAME_WIDTH_CENTER - menuWidth / 2;
    menuY = GAME_HEIGHT_CENTER - menuHeight / 2;
  }

  // hard coded to make them aligned
  private void loadButtons() {
    buttons.add(new MenuButton(GAME_WIDTH / 2, GAME_HEIGHT_CENTER + GAME_MENU_PLAY_CENTER_OFFSET, 0, game.getPlaying()));
    buttons.add(new MenuButton(GAME_WIDTH / 2, GAME_HEIGHT_CENTER + GAME_MENU_OPTIONS_CENTER_OFFSET, 1, game.getOptions()));
    buttons.add(new MenuButton(GAME_WIDTH / 2, GAME_HEIGHT_CENTER + GAME_MENU_QUIT_CENTER_OFFSET, 2, game.getQuit()));
  }

  @Override
  public void update() {
    for (GameButton button : buttons) {
      button.update();
    }
  }

  @Override
  public void draw(Graphics graphics) {
    graphics.drawImage(background, 0, 0, GAME_WIDTH, GAME_HEIGHT, null);
    graphics.drawImage(plateImage, menuX, menuY, menuWidth, menuHeight, null);
    for (GameButton button : buttons) {
      button.draw(graphics);
    }
  }

  @Override
  public void keyPressed(KeyEvent e) {
    if (e.getKeyCode() == KeyEvent.VK_ENTER) {
      game.playing();
    } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
      System.exit(0);
    }
  }
}
