package utils;


import game.handlers.AudioPlayer;
import utils.constant.GameImage;

import javax.imageio.ImageIO;
import javax.sound.sampled.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public final class LoadSafe {
  static String location = LoadSafe.class.getProtectionDomain().getCodeSource().getLocation().getPath() + "resources/";

  public static Clip loadClip(String path, AudioPlayer player) {
    String filePath = location + path;
    try {
      File soundFile = new File(filePath);
      AudioInputStream audioStream = AudioSystem.getAudioInputStream(soundFile);

      Clip clip = AudioSystem.getClip();

      clip.open(audioStream);
      return clip;
    } catch (IllegalArgumentException e) {
      player.disable();
      return null;
    } catch (UnsupportedAudioFileException e) {
      throw new LoadException("The specified audio file format is not supported.");
    } catch (LineUnavailableException e) {
      throw new LoadException("Audio line for playing back is unavailable.");
    } catch (IOException e) {
      throw new LoadException("Error reading the audio file.");
    }
  }

  public static BufferedImage getSpriteAtlas(String path) {
    try (InputStream is = new FileInputStream(location + path)) {
      return ImageIO.read(Objects.requireNonNull(is));
    } catch (Exception e) {
      throw new LoadException(e);
    }
  }

  public static BufferedImage getSpriteAtlas(GameImage image) {
    return getSpriteAtlas(image.getPath());
  }

  public static List<BufferedImage> getAllLevels() {
    File file = new File(location + "levels");
    File[] files = file.listFiles();
    List<BufferedImage> list = new ArrayList<>(Objects.requireNonNull(files).length);
    for (File value : files) {
      try {
        list.add(ImageIO.read(value));
      } catch (IOException e) {
        throw new LoadException(e);
      }
    }
    return list;
  }
}
