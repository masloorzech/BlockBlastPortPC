import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Arrays;
import java.util.Vector;

import static java.lang.Thread.sleep;


public class GuiPanel extends JPanel{
    boolean debug = true;
    int pixelSize = 40;
    private final Map map;
    private Block movingBlock = null;
    private final Vector<BlockChoosePanel> blockChoosePanels;
    private final Vector<TextPanel> textsPanels;
    private Vector2f mousePosition;
    private boolean isMousePressed =false;
    private boolean showBlockShadow = false;
    public boolean gameOver = false;
    Vector2f indexes;
    int points =0;
    private Color gradientColor;
    private Color backgroundColor;
    Vector<Vector2f> potentialToVanish=new Vector<>();

    private final Vector<Vector2f> potentialIndexes = new Vector<>();

    boolean isMouseInsidePanel(Vector2f mousePosition, BlockChoosePanel panel) {
        return mousePosition.x >= panel.offset.x && mousePosition.x <= panel.offset.x + panel.size.x * blockChoosePanels.getFirst().pixelSize
                && mousePosition.y >= panel.offset.y && mousePosition.y <= panel.offset.y + panel.size.y * blockChoosePanels.getFirst().pixelSize;
    }
    Vector2f getIndexesOfMapWhileHoldingBlock(Vector2f mousePosition) {
        for (int i = 0; i < map.width; i++) {
            for (int j = 0; j < map.height; j++) {
                float mapLeftPixelWallX = map.offset.x+pixelSize*i;
                float mapTopPixelWallY = map.offset.y+pixelSize*j;
                float mapRighttPixelWallX = map.offset.x+(i+1)* pixelSize;
                float mapDownPixelWallY =  map.offset.y+(j+1)* pixelSize;
                if (mousePosition.x>=mapLeftPixelWallX && mousePosition.x <= mapRighttPixelWallX && mousePosition.y>=mapTopPixelWallY && mousePosition.y <= mapDownPixelWallY){
                    return new Vector2f(i,j);
                }
            }
        }
        return null;
    }
    public GuiPanel(Map map){
        this.map = map;
        blockChoosePanels = new Vector<>();
        textsPanels = new Vector<>();
        setFocusable(true);
        requestFocusInWindow();
        addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                if (!gameOver) {
                    int x = e.getX();
                    int y = e.getY();
                    mousePosition = new Vector2f(x, y);
                    for (BlockChoosePanel panel : blockChoosePanels) {
                        if (isMouseInsidePanel(mousePosition, panel)) {
                            panel.blockScalingFactor = 1.10f;
                            panel.select();
                        } else {
                            panel.blockScalingFactor = 0.90f;
                            panel.unselect();
                        }
                    }
                }
                repaint();
            }
            @Override
            public void mouseDragged(MouseEvent e) {
                if (!gameOver) {
                    int x = e.getX();
                    int y = e.getY();
                    showBlockShadow = false;
                    potentialIndexes.clear();
                    mousePosition = new Vector2f(x, y);
                    indexes = getIndexesOfMapWhileHoldingBlock(mousePosition);
                    potentialToVanish.clear();
                    potentialIndexes.clear();
                    if (indexes != null && movingBlock != null) {
                        for (int i = 0; i < movingBlock.getShape().length; i++) {
                            for (int j = 0; j < movingBlock.getShape()[i].length; j++) {
                                Vector2f g = new Vector2f((int) (indexes.x - movingBlock.getShape().length / 2 + i), (int) (indexes.y - movingBlock.getShape()[0].length / 2 + j));
                                if (movingBlock.getShape()[i][j]) {
                                    potentialIndexes.add(g);
                                }
                            }
                        }
                        for (var positions : potentialIndexes) {
                            if (positions.x < 0 || positions.x >= map.width || positions.y < 0 || positions.y >= map.height
                                    || map.mapRepresentation[(int) positions.x][(int) positions.y]) {
                                potentialIndexes.clear();
                                break;
                            }
                        }
                        showBlockShadow = true;
                    }
                    boolean[][] tempMap = new boolean[map.width][map.height];
                    for (int i = 0; i < map.mapRepresentation.length; i++) {
                        System.arraycopy(map.mapRepresentation[i], 0, tempMap[i], 0, map.mapRepresentation[i].length);
                    }
                    for (var position : potentialIndexes) {
                        tempMap[(int) position.x][(int) position.y] = true;
                    }
                    potentialToVanish = new Vector<>();
                    for (int i = 0; i < tempMap.length; i++) {
                        if (isFullRowInMap(i, tempMap)) {
                            potentialToVanish.add(new Vector2f(0, i));
                        }
                    }

                    for (int j = 0; j < tempMap[0].length; j++) {
                        if (isFullColumnInMap(j, tempMap)) {
                            potentialToVanish.add(new Vector2f(1, j));
                        }
                    }
                }
                repaint();
            }
        });
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (!gameOver) {
                    isMousePressed = true;
                    for (BlockChoosePanel panel : blockChoosePanels) {
                        if (panel.selected && movingBlock == null) {
                            panel.unselect();
                            panel.move();
                            movingBlock = panel.block;
                            movingBlock.setPixelSize(pixelSize);
                            movingBlock.position = new Vector2f(e.getX(), e.getY());
                        }
                    }
                }
                repaint();
            }
            public void mouseReleased(MouseEvent e) {
                if (!gameOver) {
                    isMousePressed = false;
                    showBlockShadow = false;
                    repaint();
                    for (BlockChoosePanel panel : blockChoosePanels) {
                        if (panel.moving && indexes != null && !potentialIndexes.isEmpty()) {
                            panel.drawRandomBlokc();
                            for (BlockChoosePanel checkingPanel : blockChoosePanels) {
                                if (panel.block == checkingPanel.block) {
                                    panel.drawRandomBlokc();
                                }
                            }
                        }
                        panel.moving = false;
                    }
                    for (var position : potentialIndexes) {
                        map.mapRepresentation[(int) position.x][(int) position.y] = true;
                    }

                    movingBlock = null;
                    Vector<Vector2f> toVanish = new Vector<>();
                    for (int i = 0; i < map.mapRepresentation.length; i++) {
                        if (isFullRow(i)) {
                            toVanish.add(new Vector2f(0, i));
                        }
                    }
                    for (int j = 0; j < map.mapRepresentation[0].length; j++) {
                        if (isFullColumn(j)) {
                            toVanish.add(new Vector2f(1, j));
                        }
                    }
                    if (!toVanish.isEmpty()) {
                        points += 10 * toVanish.size();
                        String text =  "0".repeat(Math.max(0, 6 - Integer.toString(points).length())) + points;
                        textsPanels.elementAt(1).changeText(text);
                    }
                    clearRowsAndColumns(toVanish);

                    if (!canPlaceAnyBlock()) {
                        gameOver = true;
                    }
                }
                if (gameOver) {
                    map.mapRepresentation = new boolean[map.width][map.height];
                    for (var panel : blockChoosePanels) {
                        panel.drawRandomBlokc();
                    }
                    points=0;
                }
                repaint();
            }
        });
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (debug) {
                    if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                        for (BlockChoosePanel panel : blockChoosePanels) {
                            panel.drawRandomBlokc();
                        }
                    }
                }
            }
        });
    }
    private boolean canPlaceAnyBlock() {
        for (BlockChoosePanel panel : blockChoosePanels) {
            Block block = panel.block;
            for (int x = 0; x < map.width; x++) {
                for (int y = 0; y < map.height; y++) {
                    Vector2f position = new Vector2f(x, y);
                    if (canPlaceBlockAtPosition(block, position)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    private boolean canPlaceBlockAtPosition(Block block, Vector2f position) {
        for (int i = 0; i < block.getShape().length; i++) {
            for (int j = 0; j < block.getShape()[i].length; j++) {
                if (block.getShape()[i][j]) {
                    int mapX = (int) position.x + i - block.getShape().length / 2;
                    int mapY = (int) position.y + j - block.getShape()[0].length / 2;
                    if (mapX < 0 || mapX >= map.width || mapY < 0 || mapY >= map.height || map.mapRepresentation[mapX][mapY]) {
                        return false;
                    }
                }
            }
        }
        return true;
    }
    private boolean isFullRowInMap(int row, boolean[][] tempMap) {
        for (int j = 0; j < tempMap[0].length; j++) {
            if (!tempMap[row][j]) return false;
        }
        return true;
    }
    private boolean isFullColumnInMap(int col, boolean[][] tempMap) {
        for (boolean[] booleans : tempMap) {
            if (!booleans[col]) return false;
        }
        return true;
    }
    public void setGradientColor(Color color) {
        this.gradientColor = color;
    }
    public void setBackgroundColor(Color color) {
        this.backgroundColor = color;
    }
    private boolean isFullRow(int row) {
        for (int j = 0; j < map.mapRepresentation[0].length; j++) {
            if (!map.mapRepresentation[row][j]) return false;
        }
        return true;
    }
    private boolean isFullColumn(int col) {
        for (int i = 0; i < map.mapRepresentation.length; i++) {
            if (!map.mapRepresentation[i][col]) return false;
        }
        return true;
    }
    private void clearRowsAndColumns(Vector<Vector2f> toVanish) {
        for (var toVanishItem : toVanish) {
            int index = (int) toVanishItem.y;
            if (toVanishItem.x == 0) {
                Arrays.fill(map.mapRepresentation[index], false);
            } else if (toVanishItem.x == 1) {
                for (int i = 0; i < map.mapRepresentation.length; i++) {
                    map.mapRepresentation[i][index] = false;
                }
            }
        }
        potentialToVanish.clear();
    }
    public void addPanel(BlockChoosePanel panel){
        blockChoosePanels.add(panel);
    }
    public void addTextPanel(TextPanel panel){
        textsPanels.add(panel);
    }
    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        //g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
      var oldStroke = g2.getStroke();
        if (gradientColor != null) {
            Graphics2D g2d = (Graphics2D) g;
            GradientPaint gradientPaint = new GradientPaint(0, 0, backgroundColor, 0, getHeight(), gradientColor);
            g2d.setPaint(gradientPaint);
            g2d.fillRect(0, 0, getWidth(), getHeight());
        }
        map.paint(g);
        for (var panels: textsPanels){
            panels.paint(g);
        }
        for (var panels : blockChoosePanels) {
                panels.paint(g);
        }
        if (!potentialToVanish.isEmpty()) {
            for (var indexes : potentialToVanish) {
                Color before = g.getColor();
                g.setColor(new Color(255, 177, 22, 119));
                if (indexes.x == 0) {
                    g2.fillRoundRect((int) (map.offset.x+pixelSize * indexes.y) , (int) (map.offset.y),   pixelSize, map.height*pixelSize,pixelSize/3,pixelSize/3);
                }
                if (indexes.x == 1) {
                    g2.fillRoundRect((int) (map.offset.x), (int) (map.offset.y + pixelSize * indexes.y), map.width * pixelSize, pixelSize,pixelSize/3,pixelSize/3);
                }
                g.setColor(before);
            }
        }
        if (showBlockShadow){
            for (var positions: potentialIndexes){
                Color c = g.getColor();
                g.setColor(Color.orange);
                g2.setStroke(new BasicStroke(2));
                g2.drawRect((int) (map.offset.x+pixelSize*positions.x), (int) (map.offset.y+pixelSize*positions.y), pixelSize,pixelSize);
                g.setColor(new Color(250, 202, 9, 58));
                g2.fillRect((int) (map.offset.x+pixelSize*positions.x), (int) (map.offset.y+pixelSize*positions.y), pixelSize,pixelSize);
                g2.setStroke(oldStroke);
                g.setColor(c);
            }
        }

        if (movingBlock != null && isMousePressed) {
            movingBlock.setPixelSize(pixelSize);
            Vector2f pivot = movingBlock.getBlockCenter();
            movingBlock.position = new Vector2f(mousePosition.x- pivot.x/2, mousePosition.y- pivot.y/2);
            movingBlock.paint(g);
        }
        repaint();
    }
}
