package pl.radoslawkarwacki.solver;

import pl.radoslawkarwacki.model.Point;

import java.util.ArrayList;
import java.util.Random;


public class AnnealingSolver extends TSPSolver {

    private double initialTemperature;
    private int minimalTemperature;
    private int numberOfTrials;
    private double coolingCoefficient;
    private int iterationsWithoutImprovement;

    private ArrayList<Point> prev_solution;
    private ArrayList<Point> newSolution;

    public AnnealingSolver(int noOfPoints, Random r, double initialTemperature, int minimalTemperature, int numberOfTrials, double coolingCoefficient) {
        super(noOfPoints, r);
        this.initialTemperature = initialTemperature;
        this.minimalTemperature = minimalTemperature;
        this.numberOfTrials = numberOfTrials;
        this.coolingCoefficient = coolingCoefficient;
    }

    @Override
    public void solve() {
        selectRandomTour();
        prev_solution = new ArrayList<>(solution);
        while (initialTemperature > minimalTemperature && iterationsWithoutImprovement < numberOfTrials) {
            algorithmStep();
        }
        solution = newSolution;
    }

    @Override
    public void algorithmStep() {
        ArrayList<Point> currentSolution = new ArrayList<>(prev_solution);
        newSolution = new ArrayList<>(swapTwoEdges(prev_solution));
        if (isABetterCandidate()) {
            iterationsWithoutImprovement = 0;
            prev_solution = new ArrayList<>(newSolution);
        } else {
            newSolution = currentSolution;
            iterationsWithoutImprovement++;
        }
        performCooling();
    }

    private void performCooling() {
        initialTemperature = coolingCoefficient * initialTemperature;
    }

    private boolean isABetterCandidate() {
        double travelCostDifference = getTotalTourCost(newSolution) - getTotalTourCost(prev_solution);
        return travelCostDifference < 0 || (travelCostDifference > 0 && Math.exp(-travelCostDifference / initialTemperature) > Math.random());
    }
}
