package pl.radoslawkarwacki.tsp.solver;

import pl.radoslawkarwacki.tsp.model.Point;

import java.util.List;

public interface TSPSolverListener {

    void onNewSolution(List<Point> points);

}
