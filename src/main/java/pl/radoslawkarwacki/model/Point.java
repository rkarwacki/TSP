package pl.radoslawkarwacki.model;

import java.util.Random;

public class Point {

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

    public Point(Random r, int RangeX, int RangeY){
        this.x=r.nextInt(RangeX);
        this.y=r.nextInt(RangeY);
    }

    public double calculateDistanceToPoint(Point p) {
        return Math.sqrt((this.x-p.x)*(this.x-p.x) + (this.y-p.y)*(this.y-p.y));
    }
}
