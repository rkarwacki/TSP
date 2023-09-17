package pl.radoslawkarwacki.tsp.solver.impl;

import pl.radoslawkarwacki.tsp.model.Point;
import pl.radoslawkarwacki.tsp.model.SolutionHistory;
import pl.radoslawkarwacki.tsp.solver.TSPSolverListener;

import java.util.List;

public class TSPRecorder implements TSPSolverListener {

    protected SolutionHistory solutionHistory = new SolutionHistory();

    public SolutionHistory getSolutionHistory() {
        return solutionHistory;
    }

    @Override
    public void onNewSolution(List<Point> points) {
        solutionHistory.addStep(points);
    }
}
