package game.state.playingutils;

import utils.constant.Constants;
import utils.constant.GameImage;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

import static utils.LoadSafe.getSpriteAtlas;
import static utils.constant.Constants.SCALE;
import static utils.constant.ItemInfo.BIG_CLOUDS_I;
import static utils.constant.ItemInfo.SMALL_CLOUDS_I;

public class CloudHandler {
  private final BufferedImage bigClouds;
  private final BufferedImage smallClouds;
  private final int[] smallCloudsY;
  private final Random random = new Random();

  public CloudHandler() {
    smallClouds = getSpriteAtlas(GameImage.SMALL_CLOUDS);
    bigClouds = getSpriteAtlas(GameImage.BIG_CLOUDS);

    smallCloudsY = new int[8];
    for (int i = 0; i < smallCloudsY.length; i++) {
      smallCloudsY[i] = (int) (random.nextInt(95, 190) * SCALE);
    }
  }

  public void drawClouds(Graphics graphics, int xLvlOffset) {
    for (int i = 0; i < 3; i++) {
      graphics.drawImage(bigClouds,
          i * BIG_CLOUDS_I.width - (int) (xLvlOffset * 0.3),
          Constants.PositionConstants.BIG_CLOUD_Y, BIG_CLOUDS_I.width, BIG_CLOUDS_I.height, null);
    }

    for (int i = 0; i < smallCloudsY.length; i++) {
      graphics.drawImage(smallClouds,
          SMALL_CLOUDS_I.width * 4 * i - (int) (xLvlOffset * 0.7),
          smallCloudsY[i], SMALL_CLOUDS_I.width, SMALL_CLOUDS_I.height, null);
    }
  }
}
