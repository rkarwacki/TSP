package pl.radoslawkarwacki.utils;

import pl.radoslawkarwacki.model.Point;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class TSPUtils {

    private TSPUtils(){}

    public static double getTotalTourCost(ArrayList<Point> points) {
        double cost = 0;
        if (points.size() > 2) {
            for (int i = 0; i < points.size() - 1; i++) {
                cost += points.get(i).calculateDistanceToPoint(points.get(i + 1));
            }
            cost += points.get(points.size()-1).calculateDistanceToPoint(points.get(0));
        }
        return cost;
    }

    public static ArrayList<Point> swapTwoRandomEdges(ArrayList<Point> points) {
        int range1 = 0, range2 = 0, min, max;
        int noOfPoints = points.size();
        ArrayList<Point> unchangedBeginning;
        ArrayList<Point> toBeReversed;
        ArrayList<Point> unchangedEnd;
        Random random = new Random();

        while (Math.abs(range1 - range2) < 2) {
            range1 = random.nextInt(noOfPoints);
            range2 = random.nextInt(noOfPoints);
        }
        min = Math.min(range1, range2);
        max = Math.max(range1, range2);

        unchangedBeginning = new ArrayList<>(points.subList(0, min));
        toBeReversed = new ArrayList<>(points.subList(min, max));
        Collections.reverse(toBeReversed);
        unchangedEnd = new ArrayList<>(points.subList(max, noOfPoints));

        ArrayList<Point> swapped = new ArrayList<>();
        swapped.addAll(unchangedBeginning);
        swapped.addAll(toBeReversed);
        swapped.addAll(unchangedEnd);
        return swapped;
    }
}
