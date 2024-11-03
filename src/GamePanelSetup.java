import java.awt.*;

public class GamePanelSetup {
  public static Map createMap(Vector2f screenCenter) {
    Map map = new Map(new Vector2f(
      screenCenter.x - AppConfig.MAP_WIDTH * AppConfig.PIXEL_SIZE / 2,
      screenCenter.y - AppConfig.MAP_HEIGHT * AppConfig.PIXEL_SIZE / 2 - 2 * AppConfig.PIXEL_SIZE
    ), AppConfig.MAP_WIDTH, AppConfig.MAP_HEIGHT, AppConfig.PIXEL_SIZE);

    map.setFrameColors(new Color(87, 146, 183), new Color(75, 126, 159));
    map.setBackgroundColor(new Color(87, 146, 183));
    map.setStrokes(10, 4);
    map.setGridColor(new Color(100, 174, 224));
    return map;
  }

  public static GuiPanel createGuiPanel(Map map, Vector2f screenCenter) {
    GuiPanel mapPanel = new GuiPanel(map);
    mapPanel.setGradientColor(new Color(75, 126, 159));
    mapPanel.setBackgroundColor(new Color(87, 146, 183));
    mapPanel.addPanel(createBlockChoosePanel(screenCenter, 0));
    mapPanel.addPanel(createBlockChoosePanel(screenCenter, 3));
    mapPanel.addPanel(createBlockChoosePanel(screenCenter, 6));
    Vector2f offset = new Vector2f(screenCenter.x - AppConfig.MAP_WIDTH * AppConfig.PIXEL_SIZE / 2 + AppConfig.PIXEL_SIZE * 2.5f, screenCenter.y - AppConfig.MAP_HEIGHT * AppConfig.PIXEL_SIZE / 2 - 4 * AppConfig.PIXEL_SIZE);
    mapPanel.addTextPanel(createTextPanel("SCORE", new Vector2f(offset.x, offset.y - AppConfig.PIXEL_SIZE), true));
    mapPanel.addTextPanel(createTextPanel("0", offset, true));
    mapPanel.addGameOverPanel(createGameOverPanel(screenCenter));
    mapPanel.setBounds(0, 0, AppConfig.WIDTH, AppConfig.HEIGHT);
    return mapPanel;
  }

  private static BlockChoosePanel createBlockChoosePanel(Vector2f screenCenter, int columnOffset) {
    BlockChoosePanel blockChoosePanel = new BlockChoosePanel(
      new Vector2f(screenCenter.x - AppConfig.MAP_WIDTH * AppConfig.PIXEL_SIZE / 2 + columnOffset * AppConfig.PIXEL_SIZE,
        screenCenter.y + AppConfig.MAP_HEIGHT * AppConfig.PIXEL_SIZE / 2 - AppConfig.PIXEL_SIZE),
      new Vector2f(3, 3), 30);
    blockChoosePanel.setFrameColor(Color.white);
    blockChoosePanel.setBlockScalingFactor(0.90F);
    return blockChoosePanel;
  }

  private static TextPanel createTextPanel(String text, Vector2f position, boolean centered) {
    TextPanel textPanel = new TextPanel(text, position, new Vector2f(AppConfig.PIXEL_SIZE * 3, AppConfig.PIXEL_SIZE));
    textPanel.setTextSize(AppConfig.PIXEL_SIZE / 2);
    if (centered) {
      textPanel.setTextPosition("CENTER");
    }
    return textPanel;
  }

  private static TextPanel createGameOverPanel(Vector2f screenCenter) {
    TextPanel gameOverPanel = new TextPanel("PRESS ENTER TO START GAME",
      new Vector2f(screenCenter.x - AppConfig.MAP_WIDTH * AppConfig.PIXEL_SIZE / 2, screenCenter.y - AppConfig.PIXEL_SIZE * AppConfig.MAP_HEIGHT / 2 - 2 * AppConfig.PIXEL_SIZE),
      new Vector2f(AppConfig.PIXEL_SIZE * AppConfig.MAP_WIDTH, AppConfig.PIXEL_SIZE * AppConfig.MAP_HEIGHT));
    gameOverPanel.setTextSize(AppConfig.PIXEL_SIZE / 2);
    gameOverPanel.setColor(new Color(163, 217, 255));
    gameOverPanel.setTextPosition("CENTER");
    return gameOverPanel;
  }
}
