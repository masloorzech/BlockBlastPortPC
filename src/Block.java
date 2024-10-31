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
    int divieder = 2*pixelSize/10;
    int offset = pixelSize/divieder*2;
    Color brighterColor1 = new Color(color.getRed()/2 , color.getGreen()/2 , color.getBlue()/2);
    Color brighterColor2 = new Color(color.getRed()/3 , color.getGreen()/3 , color.getBlue()/3);
    Color brighterColor3 = new Color(color.getRed()/4 , color.getGreen()/4 , color.getBlue()/4);
    int[] xPoints = {x ,x+pixelSize,x+pixelSize/divieder+ pixelSize-offset,x+pixelSize/divieder};
    int[] yPoints = {y, y, y+pixelSize/divieder, y+pixelSize/divieder};
    g.setColor(brighterColor1);
    g.fillPolygon(xPoints,yPoints,4);
    xPoints = new int[]{x, x,             x + pixelSize / divieder, x + pixelSize/divieder};
    yPoints = new int[]{y, y + pixelSize, y + pixelSize / divieder + pixelSize-offset, y + pixelSize / divieder};
    g.setColor(brighterColor2);
    g.fillPolygon(xPoints,yPoints,4);
    xPoints = new int[]{x+pixelSize, x+pixelSize, x + pixelSize - pixelSize/divieder, x + pixelSize - pixelSize/divieder};
    yPoints = new int[]{y, y + pixelSize, y + pixelSize -pixelSize/divieder, y + pixelSize / divieder};
    g.setColor(brighterColor3);
    g.fillPolygon(xPoints,yPoints,4);
    g.setColor(color.darker());
    g.fillRect(x+ pixelSize /divieder,y+pixelSize/divieder,pixelSize-offset,pixelSize-offset);
  }

  public void paint(Graphics g){
      var g2d = (Graphics2D) g;
      var previousStroke = g2d.getStroke();
      g2d.setStroke(new BasicStroke(1));
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
        g2d.setStroke(previousStroke);
    }
}
