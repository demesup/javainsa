package game.state;


import game.Game;

public class Quit extends State {
  public Quit(Game game) {
    super(game);
  }

  @Override
  public void run() {
    game.quit();
  }

  @Override
  public void update() {
    System.exit(0);
  }
}
