import java.awt.*;

public class BlockChoosePanel{
    Vector2f offset;
    Vector2f size;
    Vector2f blockPosition;
    Block block;
    int pixelSize;
    Color frameColor;
    Color backgroundColor;
    boolean selected = false;
    boolean moving = false;
    boolean showFrame=false;
    float blockScalingFactor=1;

    BlockChoosePanel(Vector2f offset,Vector2f size, int pixelSize){
        this.offset = offset;
        this.size = size;
        this.pixelSize = pixelSize;
    }

    public void setBlockScalingFactor(float blockScalingFactor){
        this.blockScalingFactor= blockScalingFactor;
    }
    public void showFrame(){
        showFrame=true;
    }
    public void hideFrame(){
        showFrame=false;
    }
    public void select(){
        selected = true;
    }
    public void unselect(){
        selected = false;
    }
    public void move(){
        moving = true;
    }
    public void stopMoving(){
        moving = false;
    }

    public int getPixelSize() {
        return pixelSize;
    }

    public void setFrameColor(Color frameColor){
        this.frameColor = frameColor;
    }
    public void setBackgroundColor(Color backgroundColor) {
        this.backgroundColor = backgroundColor;
    }
    public void drawRandomBlokc(){
        block = BlockTyper.getRandomShapeWithRotation();
    }
    private Vector2f getBlockCenter(){
        if (block==null){
            System.out.println("block is null");
            return new Vector2f(0,0);
        }
        boolean[][] blockBoolRepresentation = block.getShape();
        int newPixelSize = (int) (pixelSize * blockScalingFactor);
        return new Vector2f(blockBoolRepresentation.length*newPixelSize, blockBoolRepresentation[0].length*newPixelSize);
    }
    void paint(Graphics g){
        g.setColor(frameColor);
        if (showFrame) {
            g.drawRect((int) offset.x, (int) offset.y, (int) size.x * pixelSize, (int) size.y * pixelSize);
        }
        if (block == null){
            drawRandomBlokc();
        }
        Vector2f pivot = new Vector2f(offset.x+size.x*pixelSize/2,offset.y+size.y*pixelSize/2);
        Vector2f blockCenter = getBlockCenter();
        pivot.x -= blockCenter.x/2;
        pivot.y -= blockCenter.y/2;

        blockPosition = pivot;
        if (selected){
            block.select();
        }else{
            block.unselect();
        }
        block.setPosition(blockPosition);
        block.setPixelSize((int) (pixelSize*blockScalingFactor));
        if (!moving){
            block.paint(g);
        }
    }
}
