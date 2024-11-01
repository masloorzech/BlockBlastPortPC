import java.awt.*;
import java.util.Objects;

public class TextPanel{
    Vector2f offset;
    Vector2f size;
    boolean showFrame;
    Vector2f textOffset;
    String text;
    int pixelSize =0;
    Color textColor =Color.white;
    Font font;
    String textPosition="";
    int fontSize =12;

    public TextPanel(String text , Vector2f offset, Vector2f size){
        this.text = text;
        this.offset = offset;
        this.size = size;
        this.textOffset = new Vector2f(offset);
        this.font = new Font("Arial", Font.PLAIN, fontSize);
    }
    public void changeText(String text){
        this.text = text;
    }

    public void setFont(String fontName) {
        this.font = new Font(fontName, Font.PLAIN, (int) fontSize);
    }

    public void setTextSize(int fontSize) {
        this.fontSize = fontSize;
        updateFontSize();
    }

    private void updateFontSize() {
        font = font.deriveFont((float) fontSize);
    }
    public void setTextPosition(String position){
        this.textPosition = position;
    }
    private void centerText(Graphics g) {
        FontMetrics fontMetrics = g.getFontMetrics(font);
        int textWidth = fontMetrics.stringWidth(text);
        int textHeight = fontMetrics.getAscent() - fontMetrics.getDescent();

        textOffset.x = offset.x + (size.x - textWidth) / 2;
        textOffset.y = offset.y + (size.y + textHeight) / 2;
    }
    public void changeTextColor(Color color){
        textColor = color;
    }
    public void setPixelSize(int pixelSize){
        this.pixelSize = pixelSize;
    }
    public void showFrame(){
        this.showFrame = true;
    }
    public void hideFrame(){
        this.showFrame = false;
    }

    public void paint(Graphics g){
        Color previous = g.getColor();
        Font previousFont = g.getFont();
        g.setFont(font);
        if (showFrame){
            g.setColor(Color.WHITE);
            g.drawRect((int) offset.x, (int) offset.y, (int) size.x, (int) size.x);
        }

        if (Objects.equals(textPosition, "CENTER")){
            centerText(g);
        }
        g.setFont(font);
        g.setColor(textColor);
        g.drawString(text, (int) textOffset.x, (int) textOffset.y);
        g.setColor(previous);
        g.setFont(previousFont);
    }

}
