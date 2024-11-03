import javax.swing.*;

public class GameWindow {
  public static JFrame createWindow() {
    JFrame mainWindow = new JFrame("App");
    mainWindow.setSize(AppConfig.WIDTH, AppConfig.HEIGHT);
    mainWindow.setLocationRelativeTo(null);
    mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    mainWindow.setResizable(false);
    mainWindow.setLayout(null);
    return mainWindow;
  }
}
