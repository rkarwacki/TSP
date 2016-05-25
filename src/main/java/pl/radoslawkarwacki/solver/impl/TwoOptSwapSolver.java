package pl.radoslawkarwacki.solver.impl;

import pl.radoslawkarwacki.model.Point;
import pl.radoslawkarwacki.solver.RecordableTSPSolver;

import java.util.ArrayList;
import java.util.Random;

public class TwoOptSwapSolver extends RecordableTSPSolver {

    private int iterationsWithoutImprovement;
    private double currentBest;
    private int numberOfTrialsToImproveSolution;
    private ArrayList<Point> proposedSolution;

    public TwoOptSwapSolver(int noOfPoints, Random r, int numberOfTrialsToImproveSolution) {
        super(noOfPoints, r);
        this.numberOfTrialsToImproveSolution = numberOfTrialsToImproveSolution;
    }

    @Override
    public void solve() {
        currentBest = getTotalTourCost(solution);
        while (iterationsWithoutImprovement < numberOfTrialsToImproveSolution) {
            algorithmStep();
        }
    }

    @Override
    public void algorithmStep() {
        proposedSolution = swapTwoEdges(solution);
        recordStep();
        double newTourCost = getTotalTourCost(proposedSolution);
        if (newTourCost < currentBest) {
            iterationsWithoutImprovement = 0;
            solution = proposedSolution;
            currentBest = newTourCost;
        } else {
            iterationsWithoutImprovement++;
        }
    }

    @Override
    public void recordStep() {
        solutionHistory.addStep(proposedSolution);
    }
}
