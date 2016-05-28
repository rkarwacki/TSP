package pl.radoslawkarwacki.solver;

import pl.radoslawkarwacki.model.Point;

import java.util.List;

public interface TSPSolverListener {

    void onNewSolution(List<Point> points);

}
