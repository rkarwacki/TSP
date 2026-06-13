package pl.radoslawkarwacki.tsp.solution;

import pl.radoslawkarwacki.tsp.config.AppConfig;
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

public class TSPSolutionRunner {

    private final AppConfig config;

    public TSPSolutionRunner(AppConfig config) {
        this.config = config;
    }

    public SolutionHistory solveTSP() {
        MapGenerationConfiguration mapGenerationConfiguration =
                new MapGenerationConfiguration(config.getNumberOfCities(), config.getRandomSeed(), config.getRangeX(), config.getRangeY());
        MapGenerator mapGenerator = new MultipleClustersMapGenerator(mapGenerationConfiguration);
        List<Point> points = mapGenerator.generateMap();
        TSPUseCase tspAlgorithm;
        if (config.isAnnealing()) {
            tspAlgorithm = new AnnealingSolver(points,
                    config.getInitialTemperature(),
                    config.getMinimalTemperature(),
                    config.getNumberOfTrials(),
                    config.getCoolingCoefficient());
        } else {
            tspAlgorithm = new TwoOptSwapSolver(points, config.getNumberOfTrials());
        }
        TSPSolver solver = new TSPSolver(tspAlgorithm);
        TSPRecorder recorder = new TSPRecorder();
        solver.addListener(recorder);
        solver.solve();
        return recorder.getSolutionHistory();
    }
}
