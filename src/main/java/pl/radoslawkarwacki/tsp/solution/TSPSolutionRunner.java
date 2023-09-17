package pl.radoslawkarwacki.tsp.solution;

import pl.radoslawkarwacki.tsp.mapgeneration.MapGenerationConfiguration;
import pl.radoslawkarwacki.tsp.mapgeneration.MapGenerator;
import pl.radoslawkarwacki.tsp.mapgeneration.impl.MultipleClustersMapGenerator;
import pl.radoslawkarwacki.tsp.mapgeneration.impl.OffsetGridMapGenerator;
import pl.radoslawkarwacki.tsp.model.Point;
import pl.radoslawkarwacki.tsp.model.SolutionHistory;
import pl.radoslawkarwacki.tsp.solver.TSPSolver;
import pl.radoslawkarwacki.tsp.solver.TSPUseCase;
import pl.radoslawkarwacki.tsp.solver.impl.TSPRecorder;
import pl.radoslawkarwacki.tsp.solver.impl.annealing.AnnealingSolver;
import pl.radoslawkarwacki.tsp.solver.impl.twoopt.TwoOptSwapSolver;

import java.util.List;

import static pl.radoslawkarwacki.tsp.Main.*;

public class TSPSolutionRunner {

    public SolutionHistory solveTSP() {
        MapGenerationConfiguration mapGenerationConfiguration = new MapGenerationConfiguration(NUMBER_OF_CITIES, RANDOM_SEED, RANGE_X, RANGE_Y);
        MapGenerator mapGenerator = new MultipleClustersMapGenerator(mapGenerationConfiguration);
        List<Point> points = mapGenerator.generateMap();
        TSPUseCase tspAlgorithm;
        if (ANNEALING) {
            tspAlgorithm = new AnnealingSolver(points, INITIAL_TEMPERATURE, MINIMAL_TEMPERATURE, NUMBER_OF_TRIALS, COOLING_COEFFICIENT);
        } else {
            tspAlgorithm = new TwoOptSwapSolver(points, NUMBER_OF_TRIALS);
        }
        TSPSolver solver = new TSPSolver(tspAlgorithm);
        TSPRecorder recorder = new TSPRecorder();
        solver.addListener(recorder);
        solver.solve();
        return recorder.getSolutionHistory();
    }
}
