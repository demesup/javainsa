package game.state;


import game.Game;
import game.button.GameButton;
import game.button.UrmButton;
import game.interfaces.Updatable;
import utils.constant.GameImage;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import static utils.LoadSafe.getSpriteAtlas;
import static utils.constant.Constants.*;
import static utils.constant.ItemInfo.OPTIONS_PLATE;
import static utils.constant.ItemInfo.URM_I;


public class GameOptions extends State {
  private final BufferedImage tile;

  public GameOptions(Game game) {
    super(game);

    tile = getSpriteAtlas(GameImage.OPTIONS);
    buttons.addAll(game.getAudioHandler().getButtons());
    buttons.add(new UrmButton(GAME_WIDTH_CENTER - URM_I.width / 2, GAME_HEIGHT * 5 / 7, 2, game::menu));
  }


  @Override
  public void run() {
    game.options();
  }


  @Override
  public void keyPressed(KeyEvent e) {
    if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
      game.menu();
    }
  }

  @Override
  public void draw(Graphics graphics) {
    graphics.drawImage(background, 0, 0, GAME_WIDTH, GAME_HEIGHT, null);
    graphics.drawImage(tile, GAME_WIDTH_CENTER - OPTIONS_PLATE.width / 2, GAME_HEIGHT_CENTER - OPTIONS_PLATE.height / 2,
        (int) (tile.getWidth() * SCALE), (int) (tile.getHeight() * SCALE), null);
    for (GameButton button : buttons) {
      button.draw(graphics);
    }
  }

  @Override
  public void update() {
    buttons.forEach(Updatable::update);
  }

  @Override
  public void mouseDragged(MouseEvent e) {
    game.getAudioHandler().mouseDragged(e);
  }

  @Override
  public void mousePressed(MouseEvent e) {
    super.mousePressed(e);
    game.getAudioHandler().mousePressed(e);
  }
}
