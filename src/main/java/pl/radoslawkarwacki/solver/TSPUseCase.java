package pl.radoslawkarwacki.solver;

import pl.radoslawkarwacki.model.Point;

import java.util.List;

public interface TSPUseCase {

    List<Point> getInitialPoints();

    void useImprovement(List<Point> points);

    boolean solutionCanBeImproved(int iterationsWithoutImprovement);

    boolean isABetterCandidate(double travelCostDifference);

}
