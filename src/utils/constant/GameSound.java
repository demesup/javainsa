package utils.constant;

import java.util.List;

public enum GameSound {
  COMPLETED("audio/completed.wav"),
  GAME_OVER("audio/game_over.wav"),
  JUMP("audio/jump.wav"),
  DEATH("audio/death.wav"),
  DIE("audio/die.wav"),
  ATTACK_1("audio/attack1.wav"),
  ATTACK_2("audio/attack2.wav"),
  ATTACK_3("audio/attack3.wav");

  public static final List<GameSound> attack = List.of(ATTACK_1, ATTACK_2, ATTACK_3);
  public final String path;

  GameSound(String path) {
    this.path = path;
  }
}
