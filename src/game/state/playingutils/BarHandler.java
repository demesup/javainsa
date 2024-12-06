package game.state.playingutils;

import game.interfaces.Drawable;
import game.objects.entity.Bar;

import java.awt.*;

import static utils.constant.Constants.MAX_POWER;
import static utils.constant.Constants.PositionConstants.*;
import static utils.constant.EntityInfo.PLAYER_STATS;
import static utils.constant.ItemInfo.*;

public class BarHandler implements Drawable {
  private final Bar healthBar;
  private final Bar statusBar;
  private final Bar powerBar;


  public BarHandler() {
    statusBar = new Bar(STATUS_I.width, STATUS_I.height, STATUS_BAR_X, STATUS_BAR_Y);
    healthBar = new Bar(HEALTH_I.width, HEALTH_I.height, HEALTH_BAR_X, HEALTH_BAR_Y);
    powerBar = new Bar(POWER_I.width, POWER_I.height, POWER_BAR_X, POWER_BAR_Y);
  }

  @Override
  public void draw(Graphics graphics) {
    drawBar(graphics);
    drawState(graphics, Color.RED, healthBar);
    drawState(graphics, Color.YELLOW, powerBar);
  }

  private void drawState(Graphics graphics, Color red, Bar healthBar) {
    graphics.setColor(red);
    graphics.fillRect(
        healthBar.x + statusBar.x,
        healthBar.y + statusBar.y,
        healthBar.getCurrentWidth(),
        healthBar.height
    );
  }

  private void drawBar(Graphics graphics) {
    graphics.drawImage(Bar.getImage(),
        statusBar.x,
        statusBar.y,
        statusBar.width,
        statusBar.height,
        null);
  }

  public void updateHealth(int currentHealth) {
    healthBar.setCurrentWidth((int) ((currentHealth / (float) PLAYER_STATS.stats.maxHealth()) * healthBar.width));
  }

  public void updatePower(int currentPower) {
    powerBar.setCurrentWidth((int) ((currentPower / (float) MAX_POWER) * powerBar.width));
  }
}
