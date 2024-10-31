import java.awt.*;
import java.util.Random;

public class Map{
    Vector2f offset;
    int width;
    int height;
    boolean[][] mapRepresentation;
    int pixelSize;
    Color frameColor;
    int frameStroke = 1;
    Color insideFrameColor;
    int insideFrameStroke = 1;
    private Color backgroundColor = new Color(255, 255, 255, 0);
    Color blockColor = new Color(new Random().nextInt(255),new Random().nextInt(255),new Random().nextInt(255));
    boolean firstStart = false;

    public Map(Vector2f offset,int w,int h,int pixelSize){
        this.offset = offset;
        width = w;
        height = h;
        this.pixelSize = pixelSize;
        mapRepresentation = new boolean[width][height];
    }

    void setBackgroundColor(Color color){
      backgroundColor = color;
    }
    void setFrameColors(Color frameColor,Color insideFrameColor){
        this.frameColor = frameColor;
        this.insideFrameColor = insideFrameColor;
    }
    void setStrokes(int frameStroke, int insideFrameStroke){
      this.frameStroke = frameStroke;
      this.insideFrameStroke = insideFrameStroke;
    }
    public Vector2f getDrawingSize(){
        return new Vector2f(width*pixelSize,height*pixelSize);
    }

    private void paintInsideOfBlock(Graphics g, int x, int y,Color color){
        int divieder = 2*pixelSize/10 ;
        Color brighterColor1 = new Color(color.getRed()/2 , color.getGreen()/2 , color.getBlue()/2);
        Color brighterColor2 = new Color(color.getRed()/3 , color.getGreen()/3 , color.getBlue()/3);
        Color brighterColor3 = new Color(color.getRed()/4 , color.getGreen()/4 , color.getBlue()/4);
        int[] xPoints = {x ,x+pixelSize,x+pixelSize/divieder+ pixelSize-10,x+pixelSize/divieder};
        int[] yPoints = {y, y, y+pixelSize/divieder, y+pixelSize/divieder};
        g.setColor(brighterColor1);
        g.fillPolygon(xPoints,yPoints,4);
        xPoints = new int[]{x, x,             x + pixelSize / divieder, x + pixelSize/divieder};
        yPoints = new int[]{y, y + pixelSize, y + pixelSize / divieder + pixelSize-10, y + pixelSize / divieder};
        g.setColor(brighterColor2);
        g.fillPolygon(xPoints,yPoints,4);
        xPoints = new int[]{x+pixelSize, x+pixelSize, x + pixelSize - pixelSize/divieder, x + pixelSize - pixelSize/divieder};
        yPoints = new int[]{y, y + pixelSize, y + pixelSize -pixelSize/divieder, y + pixelSize / divieder};
        g.setColor(brighterColor3);
        g.fillPolygon(xPoints,yPoints,4);
        g.setColor(color.darker());
        g.fillRect(x+ pixelSize /divieder,y+pixelSize/divieder,pixelSize-10,pixelSize-10);
    }


    void paint(Graphics g){


        Graphics2D g2d = (Graphics2D) g;

        if (firstStart){
          g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
          firstStart = true;
        }
        var previousColor = g2d.getColor();
        var previousStroke = g2d.getStroke();
        g2d.setColor(frameColor);
        g2d.setStroke(new BasicStroke(this.frameStroke));
        g2d.drawRoundRect((int) (offset.x-this.frameStroke), (int) (offset.y-this.frameStroke),width*pixelSize+this.frameStroke*2,height*pixelSize+this.frameStroke*2,pixelSize/5,pixelSize/5);
        g2d.setStroke(previousStroke);
        g2d.setColor(backgroundColor);
        g2d.fillRect((int) offset.x, (int) offset.y,width*pixelSize,height*pixelSize);
        g2d.setColor(previousColor);
        for (int i=0; i<width; i++){
            for (int j=0; j<height; j++){
                if (mapRepresentation[i][j]){
                    g.setColor(blockColor);
                    g.fillRect((int)offset.x+ i*pixelSize, (int)offset.y +j*pixelSize,pixelSize,pixelSize);
                    paintInsideOfBlock(g, (int)offset.x + i*pixelSize, (int)offset.y +j*pixelSize, blockColor);
                }
                g.setColor(frameColor);
                g.drawRoundRect((int)offset.x + i*pixelSize, (int)offset.y +j*pixelSize,pixelSize,pixelSize,pixelSize/5,pixelSize/5);

            }
        }

        g2d.setColor(insideFrameColor);
        g2d.setStroke(new BasicStroke(this.insideFrameStroke));
        g2d.drawRoundRect((int) (offset.x), (int) (offset.y),width*pixelSize+this.insideFrameStroke,height*pixelSize+this.insideFrameStroke,pixelSize/5,pixelSize/5);
        g2d.setColor(previousColor);
    }

}
