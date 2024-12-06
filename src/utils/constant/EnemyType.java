package utils.constant;

import java.util.Objects;

public enum EnemyType {
  MUSHROOM {
    @Override
    public int getSpriteAmount(EnemyState state) {
      if (Objects.requireNonNull(state) == EnemyState.IDLE || state == EnemyState.HIT || state == EnemyState.DEAD) {
        return 4;
      } else if (state == EnemyState.RUN || state == EnemyState.ATTACK) {
        return 8;
      }
      return 0;
    }
  },
  EYE {
    @Override
    public int getSpriteAmount(EnemyState state) {
      if (Objects.requireNonNull(state) == EnemyState.HIT || state == EnemyState.DEAD) {
        return 4;
      } else if (state == EnemyState.RUN || state == EnemyState.ATTACK) {
        return 8;
      }
      return 0;
    }
  };

  public abstract int getSpriteAmount(EnemyState state);
}
