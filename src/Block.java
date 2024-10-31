import java.awt.*;

public class Block{
    private final boolean[][] shape;
    private final Color color;
    Vector2f position;
    boolean selected = false;
    int pixelSize;

    public Block(boolean[][] shape, Color color) {
        this.shape = shape;
        this.color = color;
    }

    public void setPosition(Vector2f position) {
        this.position = position;
    }

    public void setPixelSize(int pixelSize) {
        this.pixelSize = pixelSize;
    }

    public void select(){
        selected = true;
    }
    public void unselect(){
        selected = false;
    }

    public boolean[][] getShape() {
        return shape;
    }
    public Vector2f getBlockCenter(){
        boolean[][] blockBoolRepresentation = shape;
        return new Vector2f(blockBoolRepresentation.length*pixelSize, blockBoolRepresentation[0].length*pixelSize);
    }

    public Color getColor() {
        return color;
    }
    private void paintInsideOfBlock(Graphics g, int x, int y,Color color){
        Color brighterColor1 = new Color(color.getRed()/2 , color.getGreen()/2 , color.getBlue()/2);
        Color brighterColor2 = new Color(color.getRed()/3 , color.getGreen()/3 , color.getBlue()/3);
        Color brighterColor3 = new Color(color.getRed()/4 , color.getGreen()/4 , color.getBlue()/4);
        int[] xPoints = {x ,x+pixelSize,x+pixelSize/6+ 2*pixelSize/3,x+pixelSize/6};
        int[] yPoints = {y, y, y+pixelSize/6, y+pixelSize/6};
        g.setColor(brighterColor1);
        g.fillPolygon(xPoints,yPoints,4);
        xPoints = new int[]{x, x,             x + pixelSize / 6, x + pixelSize/6};
        yPoints = new int[]{y, y + pixelSize, y + pixelSize / 6 + 2*pixelSize/3, y + pixelSize / 6};
        g.setColor(brighterColor2);
        g.fillPolygon(xPoints,yPoints,4);
        xPoints = new int[]{x+pixelSize, x+pixelSize, x + pixelSize - pixelSize/6, x + pixelSize - pixelSize/6};
        yPoints = new int[]{y, y + pixelSize, y + pixelSize -pixelSize/6, y + pixelSize / 6};
        g.setColor(brighterColor3);
        g.fillPolygon(xPoints,yPoints,4);
        g.setColor(color.darker());
        g.fillRect(x+pixelSize/6,y+pixelSize/6,2*pixelSize/3,2*pixelSize/3);
    }

    public void paint(Graphics g){
        for (int y =0 ; y < shape.length ; y++){
            for (int x =0 ; x < shape[0].length ; x++){
                if (shape[y][x]){
                    g.setColor(color);
                    g.fillRect((int) (position.x + y * pixelSize), (int) (position.y + x * pixelSize), pixelSize, pixelSize);
                    paintInsideOfBlock(g,(int) (position.x + y * pixelSize), (int) (position.y + x * pixelSize),color);
                    if (selected){
                        g.setColor(Color.YELLOW);
                        g.drawRect((int) (position.x + y * pixelSize)-1, (int) (position.y  + x * pixelSize)-1, pixelSize+2, pixelSize+2);
                        g.setColor(Color.ORANGE);
                        g.drawRect((int) (position.x + y * pixelSize), (int) (position.y  + x * pixelSize), pixelSize, pixelSize);
                    }
                }
            }
            }
    }
}
