import static java.lang.Math.sqrt;

public class Vector2f{
    public float x;
    public float y;
    public Vector2f(){
        x=0;
        y=0;
    }
    public Vector2f(float x, float y){
        this.x=x;
        this.y=y;
    }

    public Vector2f(Vector2f v) {
        this.x=v.x;
        this.y=v.y;
    }

    public static boolean equals(Vector2f v1, Vector2f v2){
        if (v1.x==v2.x && v1.y==v2.y){
            return true;
        }
        return false;
    }
    public static double distanceBetweenTwoPoints(Vector2f v1, Vector2f v2){
        return sqrt((v2.x-v1.x)*(v2.x-v1.x) + (v2.y-v1.y)*(v2.y-v1.y));
    }
    public static Vector2f add(Vector2f v1, Vector2f v2){
        return new Vector2f(v1.x+v2.x,v1.y+v2.y);
    }
    public Vector2f add(float x, float y){
        return new Vector2f(this.x+x,this.y+y);
    }
    public Vector2f subtract(Vector2f other) {
        return new Vector2f(this.x - other.x, this.y - other.y);
    }
    public Vector2f scale(float scalar) {
        return new Vector2f(this.x * scalar, this.y * scalar);
    }
    public float length() {
        return (float) sqrt(x * x + y * y);
    }
    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }
}
