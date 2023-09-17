package pl.radoslawkarwacki.tsp.solver.impl.annealing;

import pl.radoslawkarwacki.tsp.model.Point;
import pl.radoslawkarwacki.tsp.solver.TSPUseCase;

import java.util.List;


public class AnnealingSolver implements TSPUseCase {

    private double currentTemperature;
    private double minimalTemperature;
    private int maximumNumberOfTrials;
    private double coolingCoefficient;
    private List<Point> initialPoints;


    public AnnealingSolver(List<Point> initialPoints, double initialTemperature, double minimalTemperature, int maximumNumberOfTrials, double coolingCoefficient) {
        this.initialPoints = initialPoints;
        this.currentTemperature = initialTemperature;
        this.minimalTemperature = minimalTemperature;
        this.maximumNumberOfTrials = maximumNumberOfTrials;
        this.coolingCoefficient = coolingCoefficient;
    }

    @Override
    public void useImprovement(List<Point> points) {
        lowerTemperature();
    }


    @Override
    public boolean solutionCanBeImproved(int iterationsWithoutImprovement) {
        return currentTemperature > minimalTemperature && iterationsWithoutImprovement < maximumNumberOfTrials;
    }


    private void lowerTemperature() {
        currentTemperature = coolingCoefficient * currentTemperature;
    }

    @Override
    public boolean isABetterCandidate(double travelCostDifference) {
        return travelCostDifference < 0 || (travelCostDifference > 0 && Math.exp(-travelCostDifference / currentTemperature) > Math.random());
    }

    @Override
    public List<Point> getInitialPoints() {
        return this.initialPoints;
    }
}
