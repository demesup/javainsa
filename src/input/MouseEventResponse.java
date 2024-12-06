package input;

import java.awt.event.MouseEvent;

public interface MouseEventResponse {
  default void mouseClicked(MouseEvent e) {
  }

  default void mousePressed(MouseEvent e) {
  }

  default void mouseReleased(MouseEvent e) {
  }

  default void mouseDragged(MouseEvent e) {
  }

  default void mouseMoved(MouseEvent e) {
  }
}
