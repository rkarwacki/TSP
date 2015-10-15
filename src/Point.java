import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Random;

/**
 * Created by radek on 19.06.15.
 */
class Point {
    private final double x;
    private final double y;

    public double getX() {
        return x;
    }
    public double getY() {
        return y;
    }

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Point(Random r){
        x=r.nextInt(1000);
        y=r.nextInt(600);
    }

    public Point(Random r, int x, int y){
        this.x=r.nextInt(x);
        this.y=r.nextInt(y);
    }

    public double calculateDistanceToPoint(Point p) {
        double dist = Math.sqrt((this.x-p.x)*(this.x-p.x) + (this.y-p.y)*(this.y-p.y));
        return round(dist,2);
    }

    public static double round(double value, int dummy) {
        return 0.01 * Math.round(100 * value);
    }

    @Override
    public String toString(){
        return (Double.toString(x) + "," +Double.toString(y));
    }
}
