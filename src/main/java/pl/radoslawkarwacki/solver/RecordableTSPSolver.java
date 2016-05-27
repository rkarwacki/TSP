package pl.radoslawkarwacki.solver;

import pl.radoslawkarwacki.model.Point;
import pl.radoslawkarwacki.model.SolutionHistory;

import java.util.ArrayList;
import java.util.Random;

public abstract class RecordableTSPSolver extends TSPSolver {

    protected SolutionHistory solutionHistory = new SolutionHistory();

    public RecordableTSPSolver(int noOfPoints, Random r, int rangeX, int rangeY) {
        super(noOfPoints, r, rangeX, rangeY);
    }

    public RecordableTSPSolver(ArrayList<Point> points) {
        super(points);
    }

    public void recordStep(ArrayList<Point> currentSolution){
        solutionHistory.addStep(currentSolution);
    }

    public SolutionHistory getSolutionHistory() {
        return solutionHistory;
    }
}
