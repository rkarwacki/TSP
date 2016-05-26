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

    public TwoOptSwapSolver(int noOfPoints, Random r, int numberOfTrialsToImproveSolution) {
        super(noOfPoints, r);
        this.numberOfTrialsToImproveSolution = numberOfTrialsToImproveSolution;
    }

    @Override
    public void solve() {
        currentBest = TSPUtils.getTotalTourCost(solution);
        while (iterationsWithoutImprovement < numberOfTrialsToImproveSolution) {
            algorithmStep();
        }
    }

    @Override
    public void algorithmStep() {
        proposedSolution = TSPUtils.swapTwoEdges(solution);
        double newTourCost = TSPUtils.getTotalTourCost(proposedSolution);
        if (newTourCost < currentBest) {
            recordStep();
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
