package game.state.overlay;

import game.Game;
import game.interfaces.Updatable;
import game.state.State;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import static utils.constant.Constants.GAME_HEIGHT;
import static utils.constant.Constants.GAME_WIDTH;

public abstract class Overlay extends State {
  protected BufferedImage image;
  protected int x;
  protected int y;
  protected int width;
  protected int height;

  protected Overlay(Game game) {
    super(game);
  }

  @Override
  public void draw(Graphics graphics) {
    graphics.setColor(new Color(0, 0, 0, 150));
    graphics.fillRect(0, 0, GAME_WIDTH, GAME_HEIGHT);
    graphics.drawImage(image, x, y, width, height, null);
    buttons.forEach(b -> b.draw(graphics));
  }

  @Override
  public void update() {
    buttons.forEach(Updatable::update);
  }
}
