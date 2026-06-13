package pl.radoslawkarwacki.tsp.model;

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

    public Point(Random r, int rangeX, int rangeY) {
        this.x = r.nextInt(rangeX);
        this.y = r.nextInt(rangeY);
    }

    public double calculateDistanceToPoint(Point p) {
        return Math.sqrt((this.x-p.x)*(this.x-p.x) + (this.y-p.y)*(this.y-p.y));
    }
}
