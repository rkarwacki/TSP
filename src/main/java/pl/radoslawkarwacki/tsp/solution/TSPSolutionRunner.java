package pl.radoslawkarwacki.tsp.solution;

import pl.radoslawkarwacki.tsp.config.AppConfig;
import pl.radoslawkarwacki.tsp.mapgeneration.MapGenerationConfiguration;
import pl.radoslawkarwacki.tsp.mapgeneration.MapGenerator;
import pl.radoslawkarwacki.tsp.mapgeneration.impl.RandomMapGenerator;
import pl.radoslawkarwacki.tsp.model.Point;
import pl.radoslawkarwacki.tsp.model.SolutionHistory;
import pl.radoslawkarwacki.tsp.solver.TSPSolver;
import pl.radoslawkarwacki.tsp.solver.TSPUseCase;
import pl.radoslawkarwacki.tsp.solver.impl.TSPRecorder;
import pl.radoslawkarwacki.tsp.solver.impl.annealing.AnnealingSolver;
import pl.radoslawkarwacki.tsp.solver.impl.twoopt.TwoOptSwapSolver;
import pl.radoslawkarwacki.tsp.solver.TSPUtil;

import java.util.List;

public class TSPSolutionRunner {

    private final AppConfig config;

    public TSPSolutionRunner(AppConfig config) {
        this.config = config;
    }

    public SolveResult solveTSP() {
        return solveTSP(null);
    }

    public SolveResult solveTSP(AnnealingSolver.ProgressListener progressListener) {
        MapGenerationConfiguration mapGenerationConfiguration =
                new MapGenerationConfiguration(config.getNumberOfCities(), config.getRandomSeed(), config.getRangeX(), config.getRangeY());
        MapGenerator mapGenerator = new RandomMapGenerator(mapGenerationConfiguration);
        List<Point> points = mapGenerator.generateMap();
        TSPUseCase tspAlgorithm;
        if (config.isAnnealing()) {
            AnnealingSolver annealing = new AnnealingSolver(points,
                    config.getInitialTemperature(),
                    config.getMinimalTemperature(),
                    config.getNumberOfTrials(),
                    config.getCoolingCoefficient());
            if (progressListener != null) {
                annealing.setProgressListener(progressListener);
            }
            tspAlgorithm = annealing;
        } else {
            tspAlgorithm = new TwoOptSwapSolver(points, config.getNumberOfTrials());
        }
        TSPSolver solver = new TSPSolver(tspAlgorithm);
        TSPRecorder recorder = new TSPRecorder();
        solver.addListener(recorder);
        solver.solve();
        RunStats stats;
        int frames = recorder.getSolutionHistory().getNumberOfFrames();
        double finalCost = frames > 0 ? TSPUtil.getTotalTravelCost(recorder.getSolutionHistory().getStep(frames - 1)) : 0.0;
        if (config.isAnnealing()) {
            AnnealingSolver annealingSolver = (AnnealingSolver) tspAlgorithm;
            double finalTemp = annealingSolver.getCurrentTemperature();
            double minTemp = annealingSolver.getMinimalTemperature();
            int steps = annealingSolver.getStepsSoFar();
            int maxTrials = annealingSolver.getMaximumNumberOfTrials();
            String stopReason = finalTemp <= minTemp ? "Reached minimal temperature" : "Exceeded iterations without improvement";
            stats = new RunStats(
                    "Annealing",
                    finalTemp,
                    minTemp,
                    maxTrials,
                    steps,
                    frames,
                    stopReason,
                    config.getNumberOfCities(),
                    config.getRandomSeed(),
                    config.getInitialTemperature(),
                    config.getCoolingCoefficient(),
                    config.getNumberOfTrials(),
                    finalCost
            );
        } else {
            stats = new RunStats(
                    "2-opt",
                    0.0,
                    0.0,
                    config.getNumberOfTrials(),
                    0,
                    frames,
                    "Exceeded iterations without improvement",
                    config.getNumberOfCities(),
                    config.getRandomSeed(),
                    config.getInitialTemperature(),
                    config.getCoolingCoefficient(),
                    config.getNumberOfTrials(),
                    finalCost
            );
        }
        return new SolveResult(recorder.getSolutionHistory(), stats);
    }
}
