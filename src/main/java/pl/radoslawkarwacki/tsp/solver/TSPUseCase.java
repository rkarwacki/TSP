package pl.radoslawkarwacki.tsp.solver;

import pl.radoslawkarwacki.tsp.model.Point;

import java.util.List;

public interface TSPUseCase {

    List<Point> getInitialPoints();

    void useImprovement(List<Point> points);

    boolean solutionCanBeImproved(int iterationsWithoutImprovement);

    boolean isABetterCandidate(double travelCostDifference);

}
