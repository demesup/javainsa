package game.handlers;

import utils.constant.GameSound;
import utils.constant.Song;

import javax.sound.sampled.BooleanControl;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import java.util.Random;

import static utils.LoadSafe.loadClip;
import static utils.constant.GameSound.JUMP;

public class AudioPlayer {
  private final Random rand = new Random();
  private Clip[] songs;
  private Clip[] effects;
  private int currentSongId = -1;
  private float volume = 0.5f;
  private boolean muteSong;
  private boolean muteEffect;
  private boolean disable = false;

  public AudioPlayer() {
    loadSongs();
    loadEffects();
    updateVolume();
  }

  private void loadSongs() {
    songs = new Clip[Song.values().length];

    for (int i = 0; i < Song.values().length; i++) {
      if (disable) break;
      songs[i] = loadClip(Song.values()[i].path, this);
    }
  }

  private void loadEffects() {
    effects = new Clip[GameSound.values().length];

    for (int i = 0; i < GameSound.values().length; i++) {
      if (disable) break;
      effects[i] = loadClip(GameSound.values()[i].path, this);
    }
  }


  public void toggleSongMute() {
    if (disable) return;
    muteSong = !muteSong;
    for (Clip clip : songs) {
      muteClip(clip, muteSong);
    }
  }

  public void toggleEffectMute() {
    if (disable) return;
    muteEffect = !muteEffect;
    for (Clip clip : effects) {
      muteClip(clip, muteEffect);
    }
    if (!muteEffect) {
      // to let player know that the sfx is back on
      playEffect(JUMP);
    }
  }

  public void setVolume(float volume) {
    if (disable) return;
    this.volume = volume;
    updateVolume();
  }

  public void stopSong() {
    if (disable) return;
    if (currentSongId != -1 && songs[currentSongId].isActive()) {
      songs[currentSongId].stop();
      currentSongId = -1;
    }
  }

  public void playLevelSong() {
    if (disable) return;
    playSong(Song.LEVEL);
  }

  public void playAttack() {
    if (disable) return;
    GameSound attack = GameSound.attack.get(rand.nextInt(GameSound.attack.size()));
    playEffect(attack);
  }

  public void playEffect(GameSound sound) {
    if (disable) return;
    Clip effect = effects[sound.ordinal()];
    effect.setMicrosecondPosition(0);
    effect.start();
  }

  public void playSong(Song song) {
    if (disable) return;
    stopSong();

    currentSongId = song.ordinal();
    updateVolume(songs[currentSongId]);
    if (song == Song.MENU) {
      songs[currentSongId].setMicrosecondPosition(100);

    } else {
      songs[currentSongId].setMicrosecondPosition(0);

    }
    songs[currentSongId].loop(Clip.LOOP_CONTINUOUSLY);
  }

  private void muteClip(Clip clip, boolean mute) {
    if (disable) return;
    BooleanControl booleanControl = (BooleanControl) clip.getControl(BooleanControl.Type.MUTE);
    booleanControl.setValue(mute);
  }

  private void updateVolume() {
    if (disable) return;
    if (currentSongId != -1) {
      updateVolume(songs[currentSongId]);
    }

    for (Clip effect : effects) {
      updateVolume(effect);
    }
  }

  private void updateVolume(Clip clip) {
    if (disable) return;
    FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
    float range = gainControl.getMaximum() - gainControl.getMinimum();
    float gain = range * volume + gainControl.getMinimum();
    gainControl.setValue(gain);
  }

  public void disable() {
    disable = true;
  }
}
