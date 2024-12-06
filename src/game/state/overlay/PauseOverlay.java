package game.state.overlay;

import game.Game;
import game.button.UrmButton;

import java.awt.event.MouseEvent;

import static utils.LoadSafe.getSpriteAtlas;
import static utils.constant.Constants.GAME_WIDTH_CENTER;
import static utils.constant.Constants.PositionConstants.*;
import static utils.constant.Constants.SCALE;
import static utils.constant.GameImage.PAUSE_BACKGROUND;

public class PauseOverlay extends Overlay {

  private UrmButton menuButton;
  private UrmButton replayButton;
  private UrmButton unpauseButton;

  public PauseOverlay(Game game) {
    super(game);
    loadBackground();
    createUrmButtons();
    buttons.addAll(game.getAudioHandler().getButtons());
  }

  @Override
  public void run() {
    game.pausedOverlay();
  }


  private void createUrmButtons() {
    menuButton = new UrmButton(PAUSE_MENU_X, PAUSE_BUTTON_Y, 2, game::menu);
    replayButton = new UrmButton(PAUSE_REPLAY_X, PAUSE_BUTTON_Y, 1, game::playing);
    unpauseButton = new UrmButton(PAUSE_UNPAUSE_X, PAUSE_BUTTON_Y, 0, game.getPlaying()::unpauseGame);

    buttons.add(menuButton);
    buttons.add(replayButton);
    buttons.add(unpauseButton);
  }


  private void loadBackground() {
    image = getSpriteAtlas(PAUSE_BACKGROUND);
    width = (int) (image.getWidth() * SCALE);
    height = (int) (image.getHeight() * SCALE);
    x = GAME_WIDTH_CENTER - width / 2;
    y = PAUSE_BG_Y;
  }

  @Override
  public void mouseDragged(MouseEvent e) {
    game.getAudioHandler().mouseDragged(e);
  }

  @Override
  public void mouseReleased(MouseEvent e) {
    super.mouseReleased(e);
    if (unpauseButton.isIn(e) || replayButton.isIn(e) || menuButton.isIn(e)) game.closeOverlay();
  }

}
