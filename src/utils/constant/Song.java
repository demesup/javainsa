package utils.constant;

public enum Song {
  MENU("audio/menu.wav"),
  LEVEL("audio/level.wav");

  public final String path;

  Song(String path) {
    this.path = path;
  }
}
