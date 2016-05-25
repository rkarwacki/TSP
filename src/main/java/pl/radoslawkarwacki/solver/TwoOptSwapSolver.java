package pl.radoslawkarwacki.solver;

import pl.radoslawkarwacki.model.Point;

import java.util.ArrayList;
import java.util.Random;

public class TwoOptSwapSolver extends TSPSolver {

    private int iterationsWithoutImprovement;
    private double currentBest;
    private int numberOfTrialsToImproveSolution;

    public TwoOptSwapSolver(int noOfPoints, Random r, int numberOfTrialsToImproveSolution) {
        super(noOfPoints, r);
        this.numberOfTrialsToImproveSolution = numberOfTrialsToImproveSolution;
    }

    @Override
    public void solve() {
        selectRandomTour();
        currentBest = getTotalTourCost(solution);
        while (iterationsWithoutImprovement < numberOfTrialsToImproveSolution) {
            algorithmStep();
        }
    }

    @Override
    public void algorithmStep() {
        ArrayList<Point> proposedSolution;
        proposedSolution = swapTwoEdges(solution);
        double newTourCost = getTotalTourCost(proposedSolution);
        if (newTourCost < currentBest) {
            iterationsWithoutImprovement = 0;
            solution = proposedSolution;
            currentBest = newTourCost;
        } else {
            iterationsWithoutImprovement++;
        }
    }
}
