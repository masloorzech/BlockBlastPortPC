import java.awt.*;
import java.util.Arrays;
import java.util.Random;

import static java.lang.Math.ceil;
import static java.lang.Math.floor;

public class Map{
    Vector2f offset;
    int width;
    int height;
    boolean[][] mapRepresentation;
    Vector2f[][] mapCellsCenterCoordinates;
    int pixelSize;
    Color frameColor;
    Color blockColor = new Color(new Random().nextInt(255),new Random().nextInt(255),new Random().nextInt(255));

    public Map(Vector2f offset,int w,int h,int pixelSize){
        this.offset = offset;
        width = w;
        height = h;
        this.pixelSize = pixelSize;
        mapRepresentation = new boolean[width][height];
        mapCellsCenterCoordinates= new Vector2f[width][height];
        Vector2f coordinates = new Vector2f();

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                mapCellsCenterCoordinates[i][j] = new Vector2f(
                        offset.x + i * pixelSize / 2,
                        offset.y + j * pixelSize / 2
                );
            }
        }
    }
    void setFrameColor(Color frameColor){
        this.frameColor = frameColor;
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
    }

}
