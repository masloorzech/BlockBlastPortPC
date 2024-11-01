import java.awt.*;
import java.util.Random;

enum EasyShapes{
  QUBE(new boolean[][]{
    {true}
  }, new Color(30+new Random().nextInt(225),30+new Random().nextInt(225),30+new Random().nextInt(225))),
  SHORTI(new boolean[][]{
    {true},
    {true},
  }, new Color(30+new Random().nextInt(225),30+new Random().nextInt(225),30+new Random().nextInt(225))),
  IS(new boolean[][]{
    {true, true, true}
  }, Color.CYAN),
  I(new boolean[][]{
    {true, true, true, true}
  }, Color.CYAN),
  IL(new boolean[][]{
    {true, true, true, true,true}
  }, Color.CYAN),
  O(new boolean[][]{
    {true, true},
    {true, true},
  }, Color.YELLOW),
  OL(new boolean[][]{
    {true, true,true},
    {true, true,true},
  }, Color.YELLOW),
  SQ(new boolean[][]{
    {true, true, true},
    {true, true, true},
    {true, true, true}
  }, new Color(30+new Random().nextInt(225),30+new Random().nextInt(225),30+new Random().nextInt(225)));

  private final boolean[][] shape;
  private final Color color;

  EasyShapes(boolean[][] shape, Color color) {
    this.shape = shape;
    this.color = color;
  }
  boolean[][] getShape(){
    return shape;
  }
  Color getColor(){
    return color;
  }
}
enum MediumShapes{

  J(new boolean[][]{
    {true, false, false},
    {true, true, true},
  }, Color.BLUE),
  L(new boolean[][]{
    {false, false, true},
    {true, true, true},
  }, Color.ORANGE),
  S(new boolean[][]{
    {false, true, true},
    {true, true, false}
  }, Color.GREEN),
  T(new boolean[][]{
    {false, true, false},
    {true, true, true}
  }, Color.MAGENTA),
  Z(new boolean[][]{
    {true, true, false},
    {false, true, true}
  }, Color.RED),
  STAR(new boolean[][]{
    {false,true,false},
    {true,true,true},
    {false,true,false},
  }, new Color(30+new Random().nextInt(225),30+new Random().nextInt(225),30+new Random().nextInt(225)));
  private final boolean[][] shape;
  private final Color color;

  MediumShapes(boolean[][] shape, Color color) {
    this.shape = shape;
    this.color = color;
  }
  boolean[][] getShape(){
    return shape;
  }
  Color getColor(){
    return color;
  }
}
enum HardShapes{
  TU(new boolean[][]{
    {true, true, true},
    {false, true, false}
  }, Color.MAGENTA),

  U(new boolean[][]{
    {true, false, true},
    {true, true, true},
  },new Color(30+new Random().nextInt(225),30+new Random().nextInt(225),30+new Random().nextInt(225))),

  A(new boolean[][]{
    {true, true, true},
    {true, false, true},
  }, new Color(30+new Random().nextInt(225),30+new Random().nextInt(225),30+new Random().nextInt(225))),
  STAIRS(new boolean[][]{
    {true, false,false},
    {false, true,false},
  },new Color(30+new Random().nextInt(225),30+new Random().nextInt(225),30+new Random().nextInt(225))),

  LARGSTAIRS(new boolean[][]{
    {true, false,false},
    {false, true,false},
    {false,false,true}
  }, new Color(30+new Random().nextInt(225),30+new Random().nextInt(225),30+new Random().nextInt(225)));

  private final boolean[][] shape;
  private final Color color;

  HardShapes(boolean[][] shape, Color color) {
    this.shape = shape;
    this.color = color;
  }
  boolean[][] getShape(){
    return shape;
  }
  Color getColor(){
    return color;
  }

}

public class BlockTyper {

    private final boolean[][] shape;
    private final Color color;
    private static final Random rand = new Random();

    BlockTyper(boolean[][] shape, Color color) {
        this.shape = shape;
        this.color = color;
    }

  public static Block getRandomShapeWithRotation() {
    BlockTyper randomBlock;
    int chance = rand.nextInt(100);
    if (chance < 60) {
      EasyShapes[] easyShapes = EasyShapes.values();
      randomBlock = new BlockTyper(easyShapes[rand.nextInt(easyShapes.length)].getShape(),
        easyShapes[rand.nextInt(easyShapes.length)].getColor());

    } else if (chance < 90) {
      MediumShapes[] mediumShapes = MediumShapes.values();
      randomBlock = new BlockTyper(mediumShapes[rand.nextInt(mediumShapes.length)].getShape(),
        mediumShapes[rand.nextInt(mediumShapes.length)].getColor());

    } else {
      HardShapes[] hardShapes = HardShapes.values();
      randomBlock = new BlockTyper(hardShapes[rand.nextInt(hardShapes.length)].getShape(),
        hardShapes[rand.nextInt(hardShapes.length)].getColor());
    }
    boolean[][] selectedShape = randomBlock.getShape();
    if (rand.nextBoolean()) {
      selectedShape = rotateShape(selectedShape);
    }
    return new Block(selectedShape, randomBlock.getColor());
  }
    private static boolean[][] rotateShape(boolean[][] shape) {
        int rows = shape.length;
        int cols = shape[0].length;
        boolean[][] rotated = new boolean[cols][rows];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                rotated[j][rows - 1 - i] = shape[i][j];
            }
        }
        return rotated;
    }

    public boolean[][] getShape() {
        return shape;
    }

    public Color getColor() {
        return color;
    }
}
