package game;

import javax.swing.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;

public class GameWindow extends JFrame {
  public GameWindow(GamePanel gamePanel, Game game) {
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    add(gamePanel);
    setResizable(false);
    pack();
    setLocationRelativeTo(null);
    setVisible(true);
    addWindowFocusListener(new WindowFocusListener() {

      @Override
      public void windowLostFocus(WindowEvent e) {
        game.windowFocusLost();
      }

      @Override
      public void windowGainedFocus(WindowEvent e) {
      }
    });
  }
}
