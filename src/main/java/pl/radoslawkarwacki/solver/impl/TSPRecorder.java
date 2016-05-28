package pl.radoslawkarwacki.solver.impl;

import pl.radoslawkarwacki.model.Point;
import pl.radoslawkarwacki.model.SolutionHistory;
import pl.radoslawkarwacki.solver.TSPSolverListener;

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
