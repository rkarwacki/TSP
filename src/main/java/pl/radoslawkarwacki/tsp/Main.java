package pl.radoslawkarwacki.tsp;


import pl.radoslawkarwacki.tsp.gui.Window;
import pl.radoslawkarwacki.tsp.mapgeneration.MapGenerationConfiguration;
import pl.radoslawkarwacki.tsp.mapgeneration.MapGenerator;
import pl.radoslawkarwacki.tsp.mapgeneration.impl.OffsetGridMapGenerator;
import pl.radoslawkarwacki.tsp.model.Point;
import pl.radoslawkarwacki.tsp.model.SolutionHistory;
import pl.radoslawkarwacki.tsp.solution.TSPSolutionRunner;
import pl.radoslawkarwacki.tsp.solver.TSPSolver;
import pl.radoslawkarwacki.tsp.solver.TSPUseCase;
import pl.radoslawkarwacki.tsp.solver.TSPUtil;
import pl.radoslawkarwacki.tsp.solver.impl.TSPRecorder;
import pl.radoslawkarwacki.tsp.solver.impl.annealing.AnnealingSolver;
import pl.radoslawkarwacki.tsp.solver.impl.twoopt.TwoOptSwapSolver;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class Main {

    //TODO externalise configuration
    public static final boolean ANNEALING = true; // false for 2-opt

    // 2-opt swap parameters (required for annealing also)
    public static final int NUMBER_OF_CITIES = 20;
    public static final int NUMBER_OF_TRIALS = 5000;
    public static final long RANDOM_SEED = 124531;

    // additional parameters required for annealing
    public static final int INITIAL_TEMPERATURE = 200;
    public static final double MINIMAL_TEMPERATURE = 0.00001;
    public static final double COOLING_COEFFICIENT = 0.99999;

    // delay between frames
    public static final boolean DRAW_CHART = true;
    public static final int DELAY_MS = 1;
    public static final int FRAMES_IN_BETWEEN = 100;
    public static final int RANGE_X = 1600;
    public static final int RANGE_Y = 900;

    public static void main(String[] args) {

        //TODO use JavaFX
        EventQueue.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
                System.err.println("error during UI manager startup");
            }

            @SuppressWarnings("unused")
            Window window = new Window(new TSPSolutionRunner().solveTSP(), RANGE_X, RANGE_Y);
        });
    }
}