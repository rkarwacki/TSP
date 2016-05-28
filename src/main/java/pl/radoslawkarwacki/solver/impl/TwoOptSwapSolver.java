package pl.radoslawkarwacki.solver.impl;

import pl.radoslawkarwacki.model.Point;
import pl.radoslawkarwacki.solver.TSPUseCase;

import java.util.List;

public class TwoOptSwapSolver implements TSPUseCase {

    private List<Point> initialPoints;
    private int maximumNumberOfTrials;

    public TwoOptSwapSolver(List<Point> initialPoints, int maximumNumberOfTrials) {
        this.initialPoints = initialPoints;
        this.maximumNumberOfTrials = maximumNumberOfTrials;
    }

    @Override
    public List<Point> getInitialPoints() {
        return this.initialPoints;
    }

    @Override
    public void useImprovement(List<Point> points) {

    }

    @Override
    public boolean solutionCanBeImproved(int iterationsWithoutImprovement) {
        return iterationsWithoutImprovement < maximumNumberOfTrials;
    }

    @Override
    public boolean isABetterCandidate(double travelCostDifference) {
        return travelCostDifference < 0;
    }
}
