package pl.radoslawkarwacki.solver;

import pl.radoslawkarwacki.gui.Solution;
import pl.radoslawkarwacki.gui.SolutionStep;
import pl.radoslawkarwacki.model.Point;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by radek on 25.05.16.
 */
public class TwoOptSwapSolver extends TSPSolver {

    public TwoOptSwapSolver(int noOfPoints, Random r) {
        super(noOfPoints, r);
    }

    @Override
    public void solve() {

        int numberOfFailed = 0;
        int numberOfTrials = 10000;
        selectRandomTour();
        double currentBest = getTotalTourCost(solution);
        ArrayList<Point> dataSet = solution;
        SolutionStep ss = new SolutionStep(dataSet);
        int noOfPoints = points.size();
        System.out.println(noOfPoints);

        while (numberOfFailed < numberOfTrials) {
            ArrayList<Point> proposedSolution;
            proposedSolution = swapTwoEdges(solution);
            if (getTotalTourCost(proposedSolution) < currentBest) {
                numberOfFailed = 0;
                solution = proposedSolution;
                currentBest = getTotalTourCost(proposedSolution);
            } else {
                numberOfFailed++;

            }
        }
    }

    @Override
    public void iterationStep() {

    }
}
