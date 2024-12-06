package game;

import game.handlers.AudioHandler;
import game.handlers.AudioPlayer;
import game.state.*;
import game.state.overlay.CompletedOverlay;
import game.state.overlay.GameOverOverlay;
import game.state.overlay.Overlay;
import game.state.overlay.PauseOverlay;
import utils.constant.GameSound;
import utils.constant.Song;

import java.awt.*;

public class Game {
  private final GamePanel gamePanel;
  private final Playing playing;
  private final GameOptions options;
  private final Quit quit;
  private final GameMenu menu;
  private final PauseOverlay pauseOverlay;
  private final GameOverOverlay gameOverOverlay;
  private final CompletedOverlay completedOverlay;

  private final AudioHandler audioHandler;
  private final AudioPlayer audioPlayer;

  private State currentState;
  private Overlay currentOverlay;

  public Game() {
    audioHandler = new AudioHandler(this);

    playing = new Playing(this);
    options = new GameOptions(this);
    quit = new Quit(this);
    menu = new GameMenu(this);
    pauseOverlay = new PauseOverlay(this);
    gameOverOverlay = new GameOverOverlay(this);
    completedOverlay = new CompletedOverlay(this);

    currentState = menu;
    currentOverlay = null;

    gamePanel = new GamePanel(this);
    new GameWindow(gamePanel, this);
    gamePanel.setFocusable(true);
    gamePanel.requestFocus();

    audioPlayer = new AudioPlayer();
  }

  public void startGameLoop() {
    new GameThread(this).start();
    audioPlayer.playSong(Song.MENU);
  }

  protected void update() {
    currentState.update();
    if (currentOverlay != null) currentOverlay.update();
  }

  public void render(Graphics graphics) {
    currentState.draw(graphics);
    if (currentOverlay != null) currentOverlay.draw(graphics);
  }

  public void windowFocusLost() {
    currentState.windowFocusLost();
  }

  public GamePanel getGamePanel() {
    return gamePanel;
  }

  public void returnToMenu() {
    setCurrentState(menu);
    setCurrentOverlay(null);
    playing.resetAll();
  }

  public void levelCompleted() {
    if (currentState.equals(playing)) {
      audioPlayer.playEffect(GameSound.COMPLETED);
      playing.setLevelCompleted(true);
      setCurrentOverlay(completedOverlay);
    }
  }

  public void nextLevel() {
    if (currentState.equals(playing)) {
      playing.loadNextLevel();
      setCurrentOverlay(null);
    }
  }

  public State getCurrentState() {
    return currentState;
  }

  public void setCurrentState(State newState) {
    this.currentState = newState;
  }

  public Overlay getCurrentOverlay() {
    return currentOverlay;
  }

  public void setCurrentOverlay(Overlay overlay) {
    this.currentOverlay = overlay;
  }

  public Playing getPlaying() {
    return playing;
  }

  public GameOptions getOptions() {
    return options;
  }

  public Quit getQuit() {
    return quit;
  }

  public PauseOverlay getPauseOverlay() {
    return pauseOverlay;
  }

  public GameOverOverlay getGameOverOverlay() {
    return gameOverOverlay;
  }

  public CompletedOverlay getCompletedOverlay() {
    return completedOverlay;
  }

  public void closeOverlay() {
    currentOverlay = null;
  }

  public void setOverlay(Overlay overlay) {
    currentOverlay = overlay;
  }

  public void pausedOverlay() {
    setOverlay(pauseOverlay);
  }

  public void completedOverlay() {
    setOverlay(completedOverlay);
  }

  public void gameOver() {
    setOverlay(gameOverOverlay);
  }

  public void playing() {
    playing.restart();
    currentOverlay = null;
    audioPlayer.playLevelSong();
    currentState = playing;
  }

  public void menu() {
    if (currentState == playing) {
      audioPlayer.playSong(Song.MENU);
    }

    if (currentState == menu) {
      throw new IllegalArgumentException();
    }
    currentState = menu;
    playing.unpauseGame();
    currentOverlay = null;
  }

  public void options() {
    if (currentState == options) {
      throw new IllegalArgumentException();
    }
    currentState = options;
  }

  public void quit() {
    currentState = quit;
  }

  public boolean overlayOpened() {
    return currentOverlay != null;
  }

  public AudioHandler getAudioHandler() {
    return audioHandler;
  }

  public AudioPlayer getAudioPlayer() {
    return audioPlayer;
  }
}