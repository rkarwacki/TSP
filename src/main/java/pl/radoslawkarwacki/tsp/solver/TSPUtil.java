package pl.radoslawkarwacki.tsp.solver;

import pl.radoslawkarwacki.tsp.model.Point;

import java.util.List;

public class TSPUtil {

    private TSPUtil() {}

    public static double getTotalTravelCost(List<Point> points) {
        double cost = 0;
        if (points.size() > 2) {
            for (int i = 0; i < points.size() - 1; i++) {
                cost += points.get(i).calculateDistanceToPoint(points.get(i + 1));
            }
            cost += points.get(points.size()-1).calculateDistanceToPoint(points.get(0));
        }
        return cost;
    }
}
