package utils;


import game.objects.entity.Player;
import utils.constant.Constants;

import java.awt.geom.Rectangle2D;
import java.util.stream.IntStream;

import static utils.constant.Constants.*;
import static utils.constant.ItemInfo.TILES_I;


public class Utils {
  public static boolean canMoveHere(float x, float y, float width, float height, int[][] levelData) {
    if (isSolid(x, y, levelData)) return false;
    if (isSolid(x + width, y + height, levelData)) return false;
    if (isSolid(x + width, y, levelData)) return false;
    return !isSolid(x, y + height, levelData);
  }

  public static boolean isSolid(float x, float y, int[][] levelData) {
    if (notInGameWindow(x, y, levelData)) return true;

    float xIndex = x / TILES_I.size;
    float yIndex = y / TILES_I.size;

    return isTileSolid((int) xIndex, (int) yIndex, levelData);
  }

  private static boolean notInGameWindow(float x, float y, int[][] levelData) {
    int maxWidth = levelData[0].length * TILES_I.size;
    return x < 0 || x >= maxWidth || y < 0 || y >= GAME_HEIGHT;
  }

  private static boolean isTileSolid(int x, int y, int[][] levelData) {
    int value = levelData[y][x];
    return value != Constants.RgbImageConstants.AIR;
  }

  public static float getEntityXPositionNextToWall(Rectangle2D.Float hitBox, float xSpeed) {
    int currentTile = (int) (hitBox.x / TILES_I.size);
    if (xSpeed > 0) {
      int tileXPosition = currentTile * TILES_I.size;
      int xOffset = (int) (TILES_I.size - hitBox.width);
      return (float) tileXPosition + xOffset - 1;
    } else {
      return (float) currentTile * TILES_I.size;
    }
  }

  public static float getEntityYPositionUnderRoofOrAboveFloor(Rectangle2D.Float hitBox, float airSpeed) {
    int currentTile = (int) (hitBox.y / TILES_I.size);
    if (airSpeed > 0) {
      int tileYPosition = currentTile * TILES_I.size;
      int yOffset = (int) (TILES_I.size - hitBox.height);
      return (float) tileYPosition + yOffset - 1;
    } else {
      return (float) currentTile * TILES_I.size;
    }
  }

  public static boolean isEntityNotOnFloor(Rectangle2D.Float hitBox, int[][] levelData) {
    if (!isSolid(hitBox.x, hitBox.y + hitBox.height + 1, levelData)) {
      return !isSolid(hitBox.x + hitBox.width, hitBox.y + hitBox.height + 1, levelData);
    }
    return false;
  }

  public static boolean isFloor(Rectangle2D.Float hitBox, float xSpeed, int[][] levelData) {
    float x = hitBox.x + (xSpeed > 0 ? hitBox.width + xSpeed : xSpeed);
    return isSolid(x, hitBox.y + hitBox.height + 1, levelData);
  }

  public static boolean isSightClear(Rectangle2D.Float enemyHitBox, Rectangle2D.Float playerHitBox, int tileY, int[][] levelData) {
    int firstXTile = (int) (enemyHitBox.x / TILES_I.size);
    int secondXTile = getSecondXTile(playerHitBox, levelData);

    IntStream stream = (firstXTile > secondXTile) ? IntStream.range(secondXTile, firstXTile) :
        IntStream.range(firstXTile, secondXTile);
    return allTilesWalkable(stream, tileY, levelData);
  }

  private static int getSecondXTile(Rectangle2D.Float playerHitBox, int[][] levelData) {
    int secondXTile;

    if (isSolid(playerHitBox.x, playerHitBox.y + playerHitBox.height + 1, levelData)) {
      secondXTile = (int) (playerHitBox.x / TILES_I.size);
    } else {
      secondXTile = (int) ((playerHitBox.x + playerHitBox.width) / TILES_I.size);
    }
    return secondXTile;
  }

  public static boolean allTilesWalkable(IntStream stream, int y, int[][] levelData) {
    return stream.noneMatch(x -> isSolid(x, y, levelData) && !isTileSolid(x, y + 1, levelData));
  }

  /*
   * Checks position relative to border and returns calculated x level offset
   * */
  public static int checkCloseToBorder(Player player, int xLvlOffset, int maxLevelOffsetX) {
    int playerX = (int) player.getHitBox().x;
    int diff = playerX - xLvlOffset;

    if (diff > RIGHT_BORDER)
      xLvlOffset += diff - RIGHT_BORDER;
    else if (diff < LEFT_BORDER)
      xLvlOffset += diff - LEFT_BORDER;

    if (xLvlOffset > maxLevelOffsetX)
      xLvlOffset = maxLevelOffsetX;
    else if (xLvlOffset < 0)
      xLvlOffset = 0;
    return xLvlOffset;
  }
}
