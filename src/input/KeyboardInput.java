package input;

import game.GamePanel;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyboardInput implements KeyListener {
  private final GamePanel gamePanel;

  public KeyboardInput(GamePanel gamePanel) {
    this.gamePanel = gamePanel;
  }

  @Override
  public void keyTyped(KeyEvent e) {
    // empty block
  }

  @Override
  public void keyPressed(KeyEvent e) {
    if (gamePanel.getGame().overlayOpened()) {
      gamePanel.getGame().getCurrentOverlay().keyPressed(e);
    } else {
      gamePanel.getGame().getCurrentState().keyPressed(e);
    }
  }

  @Override
  public void keyReleased(KeyEvent e) {
    if (gamePanel.getGame().overlayOpened()) {
      gamePanel.getGame().getCurrentOverlay().keyReleased(e);
    } else {
      gamePanel.getGame().getCurrentState().keyReleased(e);
    }
  }
}
