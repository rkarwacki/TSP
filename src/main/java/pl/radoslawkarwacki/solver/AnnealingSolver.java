package pl.radoslawkarwacki.solver;

import pl.radoslawkarwacki.model.Point;

import java.util.ArrayList;
import java.util.Random;


public class AnnealingSolver extends TSPSolver {

    private double initialTemperature;
    private int minimalTemperature;
    private int numberOfTrials;
    private double coolingCoefficient;

    public AnnealingSolver(int noOfPoints, Random r, double initialTemperature, int minimalTemperature, int numberOfTrials, double coolingCoefficient) {
        super(noOfPoints, r);
        this.initialTemperature = initialTemperature;
        this.minimalTemperature = minimalTemperature;
        this.numberOfTrials = numberOfTrials;
        this.coolingCoefficient = coolingCoefficient;
    }

    @Override
    public void solve() {

        int iterationsWithoutImprovement = 0;
        ArrayList<Point> prev_solution;
        ArrayList<Point> newSolution = null;
        ArrayList<Point> currentSolution;

        selectRandomTour();
        int current_iteration = 0;
        prev_solution = new ArrayList<>(solution);
        while (initialTemperature > minimalTemperature && iterationsWithoutImprovement < numberOfTrials) {
            current_iteration++;
            currentSolution = new ArrayList<>(prev_solution);
            newSolution = new ArrayList<>(swapTwoEdges(prev_solution));
            double travelCostDifference = getTotalTourCost(newSolution) - getTotalTourCost(prev_solution);
            if (travelCostDifference < 0 || (travelCostDifference > 0 && Math.exp(-travelCostDifference / initialTemperature) > Math.random())) {
                iterationsWithoutImprovement = 0;
                prev_solution = new ArrayList<>(newSolution);
            } else {
                newSolution = currentSolution;
                iterationsWithoutImprovement++;
            }
            initialTemperature = coolingCoefficient * initialTemperature;
            iterations = current_iteration;
        }
        solution = newSolution;
    }

    @Override
    public void iterationStep() {

    }
}
