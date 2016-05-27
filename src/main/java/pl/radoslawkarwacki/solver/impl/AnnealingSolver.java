package pl.radoslawkarwacki.solver.impl;

import pl.radoslawkarwacki.model.Point;
import pl.radoslawkarwacki.solver.RecordableTSPSolver;
import pl.radoslawkarwacki.utils.TSPUtils;

import java.util.ArrayList;
import java.util.Random;


public class AnnealingSolver extends RecordableTSPSolver {

    private ArrayList<Point> currentSolution;
    private ArrayList<Point> newSolution;
    private ArrayList<Point> finalSolution;

    private double initialTemperature;
    private double minimalTemperature;
    private int maximumNumberOfTrials;
    private double coolingCoefficient;
    private int iterationsWithoutImprovement;

    public AnnealingSolver(int noOfPoints, Random r, int rangeX, int rangeY, double initialTemperature, double minimalTemperature, int maximumNumberOfTrials, double coolingCoefficient) {
        super(noOfPoints, r, rangeX, rangeY);
        this.initialTemperature = initialTemperature;
        this.minimalTemperature = minimalTemperature;
        this.maximumNumberOfTrials = maximumNumberOfTrials;
        this.coolingCoefficient = coolingCoefficient;
    }

    public AnnealingSolver(ArrayList<Point> points, double initialTemperature, double minimalTemperature, int maximumNumberOfTrials, double coolingCoefficient){
        super(points);
        this.initialTemperature = initialTemperature;
        this.minimalTemperature = minimalTemperature;
        this.maximumNumberOfTrials = maximumNumberOfTrials;
        this.coolingCoefficient = coolingCoefficient;
    }

    @Override
    public void solve() {
        currentSolution = initialSetOfPoints;
        while (solutionCanBeImproved()) {
            algorithmStep();
        }
        finalSolution = currentSolution;
    }

    public void algorithmStep() {
        newSolution = TSPUtils.swapTwoRandomEdges(currentSolution);
        if (isABetterCandidate()) {
            recordStep(newSolution);
            iterationsWithoutImprovement = 0;
            currentSolution = newSolution;
        } else {
            iterationsWithoutImprovement++;
        }
        lowerTemperature();
    }

    private boolean solutionCanBeImproved() {
        return initialTemperature > minimalTemperature && iterationsWithoutImprovement < maximumNumberOfTrials;
    }

    private void lowerTemperature() {
        initialTemperature = coolingCoefficient * initialTemperature;
    }

    private boolean isABetterCandidate() {
        double travelCostDifference = getTravelCostDifference();
        return travelCostDifference < 0 || (travelCostDifference > 0 && Math.exp(-travelCostDifference / initialTemperature) > Math.random());
    }

    private double getTravelCostDifference() {
        return TSPUtils.getTotalTourCost(newSolution) - TSPUtils.getTotalTourCost(currentSolution);
    }

    public ArrayList<Point> getFinalSolution() {
        return finalSolution;
    }
}
