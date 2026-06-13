package pl.radoslawkarwacki.tsp.cli;

import pl.radoslawkarwacki.tsp.config.AppConfig;
import pl.radoslawkarwacki.tsp.solution.SolveResult;
import pl.radoslawkarwacki.tsp.solution.TSPSolutionRunner;

public class HeadlessRunner {

    public static int run(String[] args) {
        try {
            CliArgs cli = CliArgs.parse(args);
            AppConfig defaults = AppConfig.defaults();
            AppConfig config = new ConfigLoader().load(defaults, cli.getConfigPath(), cli);

            long t0 = System.nanoTime();
            SolveResult solveResult = new TSPSolutionRunner(config).solveTSP(null);
            long t1 = System.nanoTime();
            long durationMs = (t1 - t0) / 1_000_000L;

            RunResultDto.Inputs inputs = new RunResultDto.Inputs(
                    config.isAnnealing(),
                    config.getNumberOfCities(),
                    config.getNumberOfTrials(),
                    config.getRandomSeed(),
                    config.getInitialTemperature(),
                    config.getMinimalTemperature(),
                    config.getCoolingCoefficient(),
                    config.getRangeX(),
                    config.getRangeY()
            );

            RunResultDto dto = new RunResultDto(
                    durationMs,
                    solveResult.getStats().getAlgorithm(),
                    solveResult.getStats().getFinalCost(),
                    solveResult.getStats().getStopReason(),
                    solveResult.getStats().getTotalFrames(),
                    solveResult.getStats().getFinalTemperature(),
                    solveResult.getStats().getStepsLowered(),
                    inputs
            );

            new ResultWriter().write(dto, cli.getOutputPath());
            return 0;
        } catch (IllegalArgumentException iae) {
            System.err.println("Invalid arguments: " + iae.getMessage());
            printUsage();
            return 1;
        } catch (Exception ex) {
            System.err.println("Headless run failed: " + ex.getMessage());
            ex.printStackTrace(System.err);
            return 2;
        }
    }

    private static void printUsage() {
        System.err.println("Usage: java -jar TSP.jar --headless [--config path.json] [--output result.json] [overrides...]");
        System.err.println("Overrides: --annealing <true|false> --cities <N> --trials <N> --seed <N> --initial-temperature <N> --minimal-temperature <D> --cooling-coefficient <D> --range-x <N> --range-y <N>");
    }
}
