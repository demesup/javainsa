package game.handlers;

import game.Game;
import game.interfaces.Drawable;

public abstract class Handler implements Drawable {
  protected final Game game;

  protected Handler(Game game) {
    this.game = game;
  }
}
