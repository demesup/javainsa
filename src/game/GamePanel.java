package game;


import input.KeyboardInput;
import input.MouseInput;

import javax.swing.*;
import java.awt.*;

import static utils.constant.Constants.GAME_HEIGHT;
import static utils.constant.Constants.GAME_WIDTH;


public class GamePanel extends JPanel {
  private final transient Game game;

  public GamePanel(Game game) {
    this.game = game;
    setPanelSize();

    addKeyListener(new KeyboardInput(this));
    MouseInput mouseInputs = new MouseInput(this);
    addMouseListener(mouseInputs);
    addMouseMotionListener(mouseInputs);
  }


  private void setPanelSize() {
    Dimension size = new Dimension(GAME_WIDTH, GAME_HEIGHT);
    setPreferredSize(size);
  }

  @Override
  public void paintComponent(Graphics graphics) {
    super.paintComponent(graphics);
    game.render(graphics);
  }

  public Game getGame() {
    return game;
  }
}