package pl.radoslawkarwacki.tsp.solver.impl.annealing;



import lombok.Value;
import pl.radoslawkarwacki.tsp.model.Point;

import java.util.List;

@Value
public class AnnealingSolverConfiguration {

    private List<Point> initialPoints;
    private double initialTemperature;
    private double minimalTemperature;
    private int maximumNumberOfTrials;
    private double coolingCoefficient;

}
