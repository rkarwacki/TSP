package pl.radoslawkarwacki.solver.impl;

import pl.radoslawkarwacki.model.Point;
import pl.radoslawkarwacki.solver.RecordableTSPSolver;
import pl.radoslawkarwacki.utils.TSPUtils;

import java.util.ArrayList;
import java.util.Random;

public class TwoOptSwapSolver extends RecordableTSPSolver {

    private int iterationsWithoutImprovement;
    private double currentBest;
    private int numberOfTrialsToImproveSolution;
    private ArrayList<Point> proposedSolution;

    public TwoOptSwapSolver(int noOfPoints, Random r, int rangeX, int rangeY, int numberOfTrialsToImproveSolution) {
        super(noOfPoints, r, rangeX, rangeY);
        this.numberOfTrialsToImproveSolution = numberOfTrialsToImproveSolution;
    }

    @Override
    public void solve() {
        currentBest = TSPUtils.getTotalTourCost(initialSetOfPoints);
        while (iterationsWithoutImprovement < numberOfTrialsToImproveSolution) {
            algorithmStep();
        }
    }

    public void algorithmStep() {
        proposedSolution = TSPUtils.swapTwoRandomEdges(initialSetOfPoints);
        double newTourCost = TSPUtils.getTotalTourCost(proposedSolution);
        if (newTourCost < currentBest) {
            recordStep(proposedSolution);
            iterationsWithoutImprovement = 0;
            initialSetOfPoints = proposedSolution;
            currentBest = newTourCost;
        } else {
            iterationsWithoutImprovement++;
        }
    }
}
