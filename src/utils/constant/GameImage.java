package utils.constant;

public enum GameImage {
  MUSHROOM("enemy/mushroom.png"),
  EYE("enemy/eye.png"),

  BACKGROUND_MENU("background_menu.png"),
  PLAYING_BACKGROUND("playing_bg_img.png"),

  MENU_PLATE("plate/menu.png"),
  PAUSE_BACKGROUND("plate/pause.png"),
  COMPLETED("plate/completed.png"),
  DEATH("plate/death.png"),
  OPTIONS("plate/options.png"),

  MENU_BUTTONS("button/menu_buttons.png"),
  SOUND_BUTTON("button/sound_button.png"),
  URM_BUTTONS("button/urm_buttons.png"),
  VOLUME_BUTTONS("button/volume_buttons.png"),

  OUTSIDE("map/outside_sprites.png"),
  BIG_CLOUDS("map/big_clouds.png"),
  SMALL_CLOUDS("map/small_clouds.png"),

  BAR("health_power_bar.png"),

  POTIONS("item/potions.png"),
  BARRELS("item/barrels.png"),
  SPIKES("item/spikes.png"),
  CANNONS("item/cannons.png"),
  BALL("item/ball.png"),

  LEVEL_ONE("levels/1.png"),
  LEVEL_TWO("levels/2.png"),
  LEVEL_THREE("levels/3.png"),
  LEVEL_FOUR("levels/4.png");

  private final String path;

  GameImage(String path) {
    this.path = path;
  }

  public String getPath() {
    return path;
  }
}
