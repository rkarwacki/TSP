package pl.radoslawkarwacki.solver;

import pl.radoslawkarwacki.model.Point;

import java.util.ArrayList;
import java.util.Random;

public abstract class TSPSolver {

    protected ArrayList<Point> initialSetOfPoints = new ArrayList<>();

    public abstract void solve();

    public TSPSolver(int noOfPoints, Random r, int rangeX, int rangeY) {
        for (int i = 0; i < noOfPoints; i++) {
            addPoint(new Point(r, rangeX, rangeY));
        }
    }

    public TSPSolver(ArrayList<Point> points) {
        initialSetOfPoints = points;
    }

    public void addPoint(Point p) {
        initialSetOfPoints.add(p);
    }
}

