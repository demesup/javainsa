package input;

import game.GamePanel;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class MouseInput implements MouseListener, MouseMotionListener {
  private final GamePanel gamePanel;

  public MouseInput(GamePanel gamePanel) {
    this.gamePanel = gamePanel;
  }

  @Override
  public void mouseClicked(MouseEvent e) {
    if (gamePanel.getGame().overlayOpened()) {
      gamePanel.getGame().getCurrentOverlay().mouseClicked(e);
    } else {
      gamePanel.getGame().getCurrentState().mouseClicked(e);
    }
  }

  @Override
  public void mousePressed(MouseEvent e) {
    if (gamePanel.getGame().overlayOpened()) {
      gamePanel.getGame().getCurrentOverlay().mousePressed(e);
    } else {
      gamePanel.getGame().getCurrentState().mousePressed(e);
    }
  }

  @Override
  public void mouseReleased(MouseEvent e) {
    if (gamePanel.getGame().overlayOpened()) {
      gamePanel.getGame().getCurrentOverlay().mouseReleased(e);
    } else {
      gamePanel.getGame().getCurrentState().mouseReleased(e);
    }
  }

  @Override
  public void mouseEntered(MouseEvent e) {
    // empty block
  }

  @Override
  public void mouseExited(MouseEvent e) {
    // empty block
  }

  @Override
  public void mouseDragged(MouseEvent e) {
    if (gamePanel.getGame().overlayOpened()) {
      gamePanel.getGame().getCurrentOverlay().mouseDragged(e);
    } else {
      gamePanel.getGame().getCurrentState().mouseDragged(e);
    }
  }

  @Override
  public void mouseMoved(MouseEvent e) {
    if (gamePanel.getGame().overlayOpened()) {
      gamePanel.getGame().getCurrentOverlay().mouseMoved(e);
    } else {
      gamePanel.getGame().getCurrentState().mouseMoved(e);
    }
  }
}
