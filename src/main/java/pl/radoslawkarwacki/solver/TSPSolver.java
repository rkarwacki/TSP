package pl.radoslawkarwacki.solver;

import pl.radoslawkarwacki.model.Point;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public abstract class TSPSolver {

    // TODO refactor ArrayList<Point> to a wrapper class
    public ArrayList<Point> solution = new ArrayList<>();

    public TSPSolver(int noOfPoints, Random r) {
        for (int i = 0; i < noOfPoints; i++) {
            addPoint(new Point(r));
        }
    }

    public abstract void solve();

    public abstract void algorithmStep();

    public void addPoint(Point p) {
        solution.add(p);
    }

}

