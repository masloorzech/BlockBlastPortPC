import java.awt.*;
import java.util.Random;

public enum BlockTyper {

    IS(new boolean[][]{
      {true, true, true}
    }, Color.CYAN),

    IS1(new boolean[][]{
      {true, true, true}
    }, Color.CYAN),

    IS2(new boolean[][]{
      {true, true, true}
    }, Color.CYAN),

    I(new boolean[][]{
            {true, true, true, true}
    }, Color.CYAN),

    IL(new boolean[][]{
      {true, true, true, true,true}
    }, Color.CYAN),

    IL1(new boolean[][]{
      {true, true, true, true,true}
    }, Color.CYAN),

    IL2(new boolean[][]{
      {true, true, true, true,true}
    }, Color.CYAN),

    J(new boolean[][]{
            {true, false, false},
            {true, true, true},
    }, Color.BLUE),

    L(new boolean[][]{
            {false, false, true},
            {true, true, true},
    }, Color.ORANGE),

    O(new boolean[][]{
            {true, true},
            {true, true},
    }, Color.YELLOW),

  OL(new boolean[][]{
    {true, true,true},
    {true, true,true},
  }, Color.YELLOW),

    S(new boolean[][]{
            {false, true, true},
            {true, true, false}
    }, Color.GREEN),

    T(new boolean[][]{
            {false, true, false},
            {true, true, true}
    }, Color.MAGENTA),

    TU(new boolean[][]{
            {true, true, true},
            {false, true, false}
    }, Color.MAGENTA),

    Z(new boolean[][]{
            {true, true, false},
            {false, true, true}
    }, Color.RED),

    SQ(new boolean[][]{
            {true, true, true},
            {true, true, true},
            {true, true, true}
    }, new Color(30+new Random().nextInt(225),30+new Random().nextInt(225),30+new Random().nextInt(225))),

  U(new boolean[][]{
            {true, false, true},
            {true, true, true},
    },new Color(30+new Random().nextInt(225),30+new Random().nextInt(225),30+new Random().nextInt(225))),

  A(new boolean[][]{
        {true, true, true},
        {true, false, true},
    }, new Color(30+new Random().nextInt(225),30+new Random().nextInt(225),30+new Random().nextInt(225))),

  QUBE(new boolean[][]{
        {true}
    }, new Color(30+new Random().nextInt(225),30+new Random().nextInt(225),30+new Random().nextInt(225))),

  STAIRS(new boolean[][]{
        {true, false,false},
        {false, true,false},
    },new Color(30+new Random().nextInt(225),30+new Random().nextInt(225),30+new Random().nextInt(225))),

  LARGSTAIRS(new boolean[][]{
            {true, false,false},
            {false, true,false},
            {false,false,true}
    }, new Color(30+new Random().nextInt(225),30+new Random().nextInt(225),30+new Random().nextInt(225))),

  SHORTI(new boolean[][]{
            {true},
            {true},
    }, new Color(30+new Random().nextInt(225),30+new Random().nextInt(225),30+new Random().nextInt(225))),

  STAR(new boolean[][]{
        {false,true,false},
        {true,true,true},
            {false,true,false},
    }, new Color(30+new Random().nextInt(225),30+new Random().nextInt(225),30+new Random().nextInt(225)));

    private final boolean[][] shape;
    private final Color color;
    private static final Random rand = new Random();

    BlockTyper(boolean[][] shape, Color color) {
        this.shape = shape;
        this.color = color;
    }

    public static Block getRandomShapeWithRotation() {

        BlockTyper[] blocks = BlockTyper.values();
        BlockTyper randomBlock = blocks[rand.nextInt(blocks.length)];
        boolean[][] selectedShape = randomBlock.getShape();

        if (rand.nextBoolean()) {
            selectedShape = rotateShape(selectedShape);
        }
        return new Block(selectedShape, randomBlock.color);
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
