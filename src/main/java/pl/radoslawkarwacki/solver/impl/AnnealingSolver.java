package pl.radoslawkarwacki.solver.impl;

import pl.radoslawkarwacki.model.Point;
import pl.radoslawkarwacki.solver.RecordableTSPSolver;
import pl.radoslawkarwacki.utils.TSPUtils;

import java.util.ArrayList;
import java.util.Random;


public class AnnealingSolver extends RecordableTSPSolver {

    private double initialTemperature;
    private double minimalTemperature;
    private int numberOfTrials;
    private double coolingCoefficient;
    private int iterationsWithoutImprovement;

    private ArrayList<Point> prevSolution;
    private ArrayList<Point> newSolution;

    public AnnealingSolver(int noOfPoints, Random r, double initialTemperature, double minimalTemperature, int numberOfTrials, double coolingCoefficient) {
        super(noOfPoints, r);
        this.initialTemperature = initialTemperature;
        this.minimalTemperature = minimalTemperature;
        this.numberOfTrials = numberOfTrials;
        this.coolingCoefficient = coolingCoefficient;
    }

    @Override
    public void solve() {
        prevSolution = solution;
        newSolution = prevSolution;
        while (solutionCanBeImproved()) {
            algorithmStep();
        }
        solution = newSolution;
    }

    private boolean solutionCanBeImproved() {
        return initialTemperature > minimalTemperature && iterationsWithoutImprovement < numberOfTrials;
    }

    @Override
    public void algorithmStep() {
        newSolution = TSPUtils.swapTwoEdges(prevSolution);
        if (isABetterCandidate()) {
            recordStep();
            iterationsWithoutImprovement = 0;
            prevSolution = newSolution;
        } else {
            iterationsWithoutImprovement++;
        }
        performCooling();
    }

    @Override
    public void recordStep() {
        solutionHistory.addStep(newSolution);
    }

    private void performCooling() {
        initialTemperature = coolingCoefficient * initialTemperature;
    }

    private boolean isABetterCandidate() {
        double travelCostDifference = getTravelCostDifference();
        return travelCostDifference < 0 || (travelCostDifference > 0 && Math.exp(-travelCostDifference / initialTemperature) > Math.random());
    }

    private double getTravelCostDifference() {
        return TSPUtils.getTotalTourCost(newSolution) - TSPUtils.getTotalTourCost(prevSolution);
    }
}
