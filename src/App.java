import javax.swing.*;
import java.awt.*;

public class App {
  public static void main(String[] args) {
    JFrame mainWindow = GameWindow.createWindow();
    GameInitializer.initializeComponents(mainWindow);
    mainWindow.setVisible(true);
  }
}

