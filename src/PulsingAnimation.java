import java.awt.*;
import javax.swing.*;

public class PulsingAnimation {
  private Vector2f position;
  private Vector2f size;
  private Image image;
  private float opacity;
  private Timer timer;
  private boolean running = true;
  private boolean fade;

  public PulsingAnimation(Vector2f position, Vector2f size, Image image, float opacity) {
    this.position = position;
    this.size = size;
    this.image = image;
    this.opacity = opacity;

    timer = new Timer(30, e -> updateEffect());
    timer.start();
  }

  public void stop() {
    timer.stop();
  }

  private void updateEffect() {
    if (running) {
      size.x += 10;
      size.y += 10;
      running = false;
    }
    if (!running) {
      size.x -= 10;
      size.y -= 10;
      running = true;
    }
    if (!fade){
      opacity+=0.05f;
    }
    if (fade) {
      opacity -= 0.05f;
    }
    if (opacity<=0.1f){
      fade = false;
    }
    if (opacity>=0.9f){
      fade = true;
    }
  }

  public void paint(Graphics g) {
    Graphics2D g2d = (Graphics2D) g;
    g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity));
    g2d.drawImage(image, (int) position.x, (int) position.y, (int) size.x, (int) size.y, null);
    g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
  }
}
