package game.handlers;


import game.Game;
import game.button.GameButton;
import game.button.PauseButton;
import game.button.SoundButton;
import game.button.VolumeButton;
import game.interfaces.Resettable;
import game.interfaces.Updatable;
import input.MouseEventResponse;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.stream.Stream;

import static utils.constant.Constants.PositionConstants.*;
import static utils.constant.ItemInfo.*;


public class AudioHandler extends Handler implements Updatable, Resettable, MouseEventResponse {
  private SoundButton musicButton;
  private SoundButton sfxButton;
  private VolumeButton volumeButton;

  public AudioHandler(Game game) {
    super(game);

    createSoundButtons();
    createVolumeButton();
  }

  private void createVolumeButton() {
    volumeButton = new VolumeButton(VOLUME_X, VOLUME_Y, SLIDER_I.width, VOLUME_I.height);
  }

  private void createSoundButtons() {
    musicButton = new SoundButton(SOUND_X, MUSIC_Y, SOUND_I.width, SOUND_I.height, () -> {
      musicButton.setMuted(!musicButton.isMuted());
      game.getAudioPlayer().toggleSongMute();
    });
    sfxButton = new SoundButton(SOUND_X, SFX_Y, SOUND_I.width, SOUND_I.height, () -> {
      sfxButton.setMuted(!sfxButton.isMuted());
      game.getAudioPlayer().toggleEffectMute();
    });
  }

  @Override
  public void draw(Graphics graphics) {
    volumeButton.draw(graphics);
    musicButton.draw(graphics);
    sfxButton.draw(graphics);
  }

  @Override
  public void update() {
    volumeButton.update();
    musicButton.update();
    sfxButton.update();
  }

  @Override
  public void mouseDragged(MouseEvent e) {
    if (volumeButton.isMousePressed()) {
      float valueBefore = volumeButton.getFloatValue();
      volumeButton.changeX(e.getX());
      float valueAfter = volumeButton.getFloatValue();
      if (valueBefore != valueAfter)
        game.getAudioPlayer().setVolume(valueAfter);
    }
  }

  @Override
  public void mousePressed(MouseEvent e) {
    getButtonStream().filter(b -> b.isIn(e))
        .findFirst().ifPresent(b -> b.setMousePressed(true));
  }

  @Override
  public void mouseReleased(MouseEvent e) {
    getButtonStream().filter(button -> button.isIn(e))
        .findFirst().filter(GameButton::isMousePressed)
        .ifPresent(GameButton::runOnPressed);
    resetAll();
  }

  private Stream<PauseButton> getButtonStream() {
    return Stream.of(musicButton, sfxButton, volumeButton);
  }

  @Override
  public void resetAll() {
    getButtonStream().forEach(GameButton::resetBooleans);
  }

  @Override
  public void mouseMoved(MouseEvent e) {
    getButtonStream()
        .peek(b -> b.setMouseOver(false))
        .filter(b -> b.isIn(e))
        .findFirst().ifPresent(b -> b.setMouseOver(true));

  }

  public List<GameButton> getButtons() {
    return List.of(volumeButton, musicButton, sfxButton);
  }
}
