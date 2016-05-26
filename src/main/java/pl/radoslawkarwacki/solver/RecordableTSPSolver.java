package pl.radoslawkarwacki.solver;

import pl.radoslawkarwacki.model.SolutionHistory;

import java.util.Random;

public abstract class RecordableTSPSolver extends TSPSolver implements Recordable {

    protected SolutionHistory solutionHistory = new SolutionHistory();

    public RecordableTSPSolver(int noOfPoints, Random r) {
        super(noOfPoints, r);
    }

    public SolutionHistory getSolutionHistory() {
        return solutionHistory;
    }
}
