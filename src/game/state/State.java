package game.state;


import game.Game;
import game.button.GameButton;
import game.interfaces.Drawable;
import game.interfaces.Resettable;
import game.interfaces.Updatable;
import input.KeyEventResponse;
import input.MouseEventResponse;
import utils.LoadSafe;
import utils.constant.GameImage;

import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.LinkedList;
import java.util.List;

public abstract class State implements MouseEventResponse, KeyEventResponse, Drawable, Updatable, Resettable {
  protected static final BufferedImage background;

  static {
    background = LoadSafe.getSpriteAtlas(GameImage.BACKGROUND_MENU);
  }

  protected final Game game;
  protected List<GameButton> buttons = new LinkedList<>();

  protected State(Game game) {
    this.game = game;
  }

  public abstract void run();

  @Override
  public void resetAll() {
    buttons.forEach(GameButton::resetBooleans);
  }

  public void windowFocusLost() {
  }

  public void checkEnemyHit() {
  }


  @Override
  public void mouseMoved(MouseEvent e) {
    buttons.forEach(button -> button.setMouseOver(false));

    buttons.stream().filter(button -> button.isIn(e)).findFirst().ifPresent(button -> button.setMouseOver(true));
  }

  @Override
  public void mousePressed(MouseEvent e) {
    buttons.stream().filter(button -> button.isIn(e))
        .findFirst().ifPresent(b -> b.setMousePressed(true));
  }

  @Override
  public void mouseReleased(MouseEvent e) {
    buttons.stream().filter(button -> button.isIn(e))
        .findFirst().filter(GameButton::isMousePressed)
        .ifPresent(GameButton::runOnPressed);
    resetAll();
  }

  public void unpauseGame() {

  }
}
