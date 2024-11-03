import javax.swing.*;

public class GameInitializer {
  public static void initializeComponents(JFrame mainWindow) {
    Vector2f screenCenter = new Vector2f(AppConfig.WIDTH / 2, AppConfig.HEIGHT / 2);
    Map map = GamePanelSetup.createMap(screenCenter);
    GuiPanel mapPanel = GamePanelSetup.createGuiPanel(map, screenCenter);
    mainWindow.add(mapPanel);
  }
}
