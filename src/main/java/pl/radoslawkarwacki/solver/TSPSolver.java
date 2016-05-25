package pl.radoslawkarwacki.solver;

import pl.radoslawkarwacki.model.Point;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public abstract class TSPSolver {

    // TODO refactor ArrayList<Point> to a wrapper class
    public ArrayList<Point> solution = new ArrayList<>();

    public int iterations;

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

    public ArrayList<Point> getSolution() {
        return solution;
    }

    public double getTotalTourCost(ArrayList<Point> points) {
        double cost = 0;
        int i;
        if (points.size() > 2) {
            for (i = 0; i < points.size() - 1; i++) {
                cost += points.get(i).calculateDistanceToPoint(points.get(i + 1));
            }
            cost += points.get(i).calculateDistanceToPoint(points.get(0));
        }
        return cost;
    }

    public ArrayList<Point> swapTwoEdges(ArrayList<Point> points) {
        int range1 = 0, range2 = 0, min, max;
        int noOfPoints = points.size();
        Random random = new Random();
        ArrayList<Point> firstSub;
        ArrayList<Point> middleSub;
        ArrayList<Point> lastSub;
        ArrayList<Point> copy = new ArrayList<>(points);

        while (Math.abs(range1 - range2) < 2) {
            range1 = random.nextInt(noOfPoints);
            range2 = random.nextInt(noOfPoints);
        }
        min = Math.min(range1, range2);
        max = Math.max(range1, range2);

        firstSub = new ArrayList<>(copy.subList(0, min));
        middleSub = new ArrayList<>(copy.subList(min, max));
        Collections.reverse(middleSub);
        lastSub = new ArrayList<>(copy.subList(max, noOfPoints));
        ArrayList<Point> proposedSolution = new ArrayList<>();
        proposedSolution.addAll(firstSub);
        proposedSolution.addAll(middleSub);
        proposedSolution.addAll(lastSub);
        return proposedSolution;
    }
}

