import javax.swing.*;
import java.awt.*;


public class App {

    static int width = 1000;
    static int height = 1000;
    static int pixelSize = 40;
    static int mapWidth = 9;
    static int mapHeight = 9;

    public static void main(String[] args) {
        JFrame mainWindow = new JFrame("App");
        mainWindow.setSize(width, height);
        mainWindow.setLocationRelativeTo(null);
        mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainWindow.setResizable(false);
        Vector2f screenCenter = new Vector2f(width/2, height/2);
        Map map = new Map(new Vector2f(screenCenter.x-mapWidth*pixelSize/2, screenCenter.y-mapHeight*pixelSize/2 - 2* pixelSize),mapWidth, mapHeight,pixelSize);
        map.setFrameColor(Color.white);
        BlockChoosePanel blockChoosePanelLeft = new BlockChoosePanel(new Vector2f(screenCenter.x-mapWidth*pixelSize/2, screenCenter.y+mapHeight*pixelSize/2-2*pixelSize), new Vector2f(4,4),30);
        blockChoosePanelLeft.setFrameColor(Color.white);
        blockChoosePanelLeft.setBlockScalingFactor(0.90F);
        BlockChoosePanel blockChoosePanelCenter = new BlockChoosePanel(new Vector2f(screenCenter.x-mapWidth*pixelSize/2 + 3*pixelSize, screenCenter.y+mapHeight*pixelSize/2-2*pixelSize), new Vector2f(4,4),30);
        blockChoosePanelCenter.setFrameColor(Color.white);
        blockChoosePanelCenter.setBlockScalingFactor(0.90F);
        BlockChoosePanel blockChoosePanelRight = new BlockChoosePanel(new Vector2f(screenCenter.x-mapWidth*pixelSize/2 + 6*pixelSize, screenCenter.y+mapHeight*pixelSize/2-2*pixelSize), new Vector2f(4,4),30);
        blockChoosePanelRight.setFrameColor(Color.white);
        blockChoosePanelRight.setBlockScalingFactor(0.90F);
        Vector2f of = new Vector2f(screenCenter.x-mapWidth*pixelSize/2 + pixelSize*3, screenCenter.y-mapHeight*pixelSize/2 - 4* pixelSize);
        TextPanel scoreTextPanel = new TextPanel("SCORE",new Vector2f(of.x,of.y-pixelSize),new Vector2f(pixelSize*3, pixelSize));
        scoreTextPanel.setTextSize(pixelSize/2);
        scoreTextPanel.setTextPosition("CENTER");
        TextPanel scorePanel = new TextPanel("000000",of,new Vector2f(pixelSize*3,pixelSize));
        scorePanel.setTextSize(pixelSize/2);
        scorePanel.setTextPosition("CENTER");

        GuiPanel mapPanel = new GuiPanel(map);
        mapPanel.setGradientColor(new Color(26, 79, 90));
        mapPanel.setBackgroundColor(new Color(81, 139, 147));
        mapPanel.addPanel(blockChoosePanelLeft);
        mapPanel.addPanel(blockChoosePanelCenter);
        mapPanel.addPanel(blockChoosePanelRight);
        mapPanel.addTextPanel(scoreTextPanel);
        mapPanel.addTextPanel(scorePanel);
        mapPanel.setBounds(0, 0, width, height);
        mainWindow.setLayout(null);

        mainWindow.add(mapPanel);

        mainWindow.setVisible(true);
    }
}

