package utils.constant;

import static utils.constant.ItemInfo.TILES_I;

public final class Constants {
  public static final float SCALE = 1.5F;
  public static final int GAME_WIDTH = TILES_I.size * TILES_I.defaultWidth;
  public static final int GAME_WIDTH_CENTER = GAME_WIDTH / 2;
  public static final int GAME_HEIGHT = TILES_I.size * TILES_I.defaultHeight;
  public static final int GAME_HEIGHT_CENTER = GAME_HEIGHT / 2;

  public static final int LEFT_BORDER = (int) (0.2 * GAME_WIDTH);
  public static final int RIGHT_BORDER = (int) (0.8 * GAME_WIDTH);

  public static final int MAX_POWER = 200;
  public static final int POWER_GROW_SPEED = 15;
  public static final int POWER_ATTACK_VALUE = 60;

  public static final int CANNON_SIGHT_DISTANCE = TILES_I.size * 5;
  public static final float CANNON_BALL_SPEED = SCALE * 0.8f;
  public static final int CANNON_DAMAGE = 25;

  public static final float PLAYER_JUMP_SPEED = -2.25f * SCALE;
  public static final int EYE_ATTACK_MULTIPLIER = 7;

  public static final float GRAVITY = 0.04f * SCALE;
  public static final float FALL_SPEED = 0.5f * SCALE;

  public static class PotionValues {
    public static final int RED_POTION = 40;
    public static final int BLUE_POTION = 50;
    public static final float FLOATING_SPEED = 0.05f * SCALE;
  }

  public static class RgbImageConstants {
    public static final int PLAYER_SPAWN = 100;
    public static final int AIR = 11;
    public static final int MAX_TILE_RED = 48;

  }

  public static class PositionConstants {
    public static final int BIG_CLOUD_Y = (int) (204 * SCALE);
    public static final int PAUSE_MENU_X = (int) (313 * SCALE);
    public static final int PAUSE_REPLAY_X = (int) (387 * SCALE);
    public static final int PAUSE_UNPAUSE_X = (int) (462 * SCALE);
    public static final int PAUSE_BUTTON_Y = (int) (325 * SCALE);
    public static final int PAUSE_BG_Y = (int) (25 * SCALE);
    public static final int COMPLETED_BG_Y = (int) (120 * SCALE);

    public static final int VOLUME_X = (int) (309 * SCALE);
    public static final int VOLUME_Y = (int) (278 * SCALE);

    public static final int SOUND_X = (int) (450 * SCALE);
    public static final int MUSIC_Y = (int) (140 * SCALE);
    public static final int SFX_Y = (int) (186 * SCALE);

    public static final float RIGHT_CANNON_X_OFFSET = -3 * SCALE;
    public static final float LEFT_CANNON_X_OFFSET = -3 * SCALE;
    public static final float CANNON_Y_OFFSET = 11 * SCALE;

    public static final int STATUS_BAR_X = (int) (10 * SCALE);
    public static final int HEALTH_BAR_X = (int) (34 * SCALE);
    public static final int POWER_BAR_X = (int) (44 * SCALE);

    public static final int STATUS_BAR_Y = (int) (10 * SCALE);
    public static final int HEALTH_BAR_Y = (int) (14 * SCALE);
    public static final int POWER_BAR_Y = (int) (34 * SCALE);

    public static final int GAME_MENU_PLAY_CENTER_OFFSET = (int) (-60 * SCALE);
    public static final int GAME_MENU_OPTIONS_CENTER_OFFSET = (int) (5 * SCALE);
    public static final int GAME_MENU_QUIT_CENTER_OFFSET = (int) (70 * SCALE);
  }
}
