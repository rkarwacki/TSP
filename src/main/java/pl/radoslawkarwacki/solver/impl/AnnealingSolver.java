package pl.radoslawkarwacki.solver.impl;

import pl.radoslawkarwacki.model.Point;
import pl.radoslawkarwacki.solver.TSPUseCase;

import java.util.List;


public class AnnealingSolver implements TSPUseCase {

    private double initialTemperature;
    private double minimalTemperature;
    private int maximumNumberOfTrials;
    private double coolingCoefficient;
    private List<Point> initialPoints;


    public AnnealingSolver(List<Point> initialPoints, double initialTemperature, double minimalTemperature, int maximumNumberOfTrials, double coolingCoefficient) {
        this.initialPoints = initialPoints;
        this.initialTemperature = initialTemperature;
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
        return initialTemperature > minimalTemperature && iterationsWithoutImprovement < maximumNumberOfTrials;
    }


    private void lowerTemperature() {
        initialTemperature = coolingCoefficient * initialTemperature;
    }

    @Override
    public boolean isABetterCandidate(double travelCostDifference) {
        return travelCostDifference < 0 || (travelCostDifference > 0 && Math.exp(-travelCostDifference / initialTemperature) > Math.random());
    }

    @Override
    public List<Point> getInitialPoints() {
        return this.initialPoints;
    }
}
