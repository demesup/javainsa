package game.state.overlay;


import game.Game;
import game.button.UrmButton;
import utils.LoadSafe;
import utils.constant.GameImage;

import static utils.constant.Constants.GAME_WIDTH_CENTER;
import static utils.constant.Constants.PositionConstants.COMPLETED_BG_Y;
import static utils.constant.Constants.SCALE;
import static utils.constant.ItemInfo.URM_I;

public class CompletedOverlay extends Overlay {
  public CompletedOverlay(Game game) {
    super(game);
    initImage();
    initButtons();
  }

  @Override
  public void run() {
    game.completedOverlay();
  }

  private void initButtons() {
    int menuX = GAME_WIDTH_CENTER - (width / 3);
    int nextX = GAME_WIDTH_CENTER + (width / 3) - URM_I.width;
    int buttonsY = y + height * 3 / 5;

    UrmButton menu = new UrmButton(menuX, buttonsY, 2, game::menu);
    UrmButton next = new UrmButton(nextX, buttonsY, 0, game::nextLevel);

    buttons.add(menu);
    buttons.add(next);
  }

  private void initImage() {
    image = LoadSafe.getSpriteAtlas(GameImage.COMPLETED);
    width = (int) (image.getWidth() * SCALE);
    height = (int) (image.getHeight() * SCALE);
    x = GAME_WIDTH_CENTER - width / 2;
    y = COMPLETED_BG_Y;
  }
}
