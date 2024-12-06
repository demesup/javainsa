package game.state.overlay;

import game.Game;
import game.button.UrmButton;
import utils.LoadSafe;
import utils.constant.GameImage;

import java.awt.event.KeyEvent;

import static utils.constant.Constants.*;
import static utils.constant.ItemInfo.URM_I;


public class GameOverOverlay extends Overlay {
  public GameOverOverlay(Game game) {
    super(game);

    image = LoadSafe.getSpriteAtlas(GameImage.DEATH);
    width = (int) (image.getWidth() * SCALE);
    height = (int) (image.getHeight() * SCALE);
    x = GAME_WIDTH_CENTER - width / 2;
    y = GAME_HEIGHT_CENTER - height / 2;

    initButtons();
  }

  private void initButtons() {
    int y = GAME_HEIGHT_CENTER - URM_I.height / 4;
    buttons.add(new UrmButton(x + width * 2 / 7 - URM_I.width / 2, y, 2, game::menu));
    buttons.add(new UrmButton(x + width * 5 / 7 - URM_I.width / 2, y, 0, () -> game.getPlaying().restart()));
  }

  @Override
  public void run() {
    game.gameOver();
  }

  @Override
  public void keyPressed(KeyEvent e) {
    if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
      game.returnToMenu();
    }
  }
}
