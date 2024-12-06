package utils.constant;


import static utils.constant.Constants.SCALE;

public enum ItemInfo {
  TILES_I(26, 14, 32),
  BUTTON_I(140, 56),
  STATUS_I(192, 58),
  POWER_I(104, 2),
  HEALTH_I(150, 4),
  SOUND_I(42, 42),
  URM_I(56, 56, 56),
  VOLUME_I(28, 44),
  SLIDER_I(215),
  BIG_CLOUDS_I(448, 101),
  SMALL_CLOUDS_I(75, 24),

  POTION_I(12, 16),
  CONTAINER_I(40, 30),
  SPIKE_I(32, 32, 32),
  CANNON_I(40, 26),
  BALL_I(15, 15),

  MUSHROOM_I(150, 150),
  EYE_I(150, 150),
  PLAYER_I(50, 37),

  OPTIONS_PLATE(282, 393);

  public final int defaultWidth;
  public final int defaultHeight;
  public final int defaultSize;
  public final int width;
  public final int height;
  public final int size;

  ItemInfo(int defaultWidth) {
    this.defaultWidth = defaultWidth;
    this.defaultHeight = 0;
    this.defaultSize = 0;
    this.width = (int) (defaultWidth * SCALE);
    this.height = 0;
    this.size = 0;
  }

  ItemInfo(int defaultWidth, int defaultHeight) {
    this.defaultWidth = defaultWidth;
    this.defaultHeight = defaultHeight;
    this.defaultSize = 0;
    this.width = (int) (defaultWidth * SCALE);
    this.height = (int) (defaultHeight * SCALE);
    this.size = 0;
  }

  ItemInfo(int defaultWidth, int defaultHeight, int defaultSize) {
    this.defaultWidth = defaultWidth;
    this.defaultHeight = defaultHeight;
    this.defaultSize = defaultSize;
    this.width = (int) (defaultWidth * SCALE);
    this.height = (int) (defaultHeight * SCALE);
    this.size = (int) (defaultSize * SCALE);
  }
}
