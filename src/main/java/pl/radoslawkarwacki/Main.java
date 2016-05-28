package pl.radoslawkarwacki;


import pl.radoslawkarwacki.gui.TSPDrawer;
import pl.radoslawkarwacki.model.Point;
import pl.radoslawkarwacki.model.SolutionHistory;
import pl.radoslawkarwacki.solver.TSPSolver;
import pl.radoslawkarwacki.solver.TSPUseCase;
import pl.radoslawkarwacki.solver.impl.AnnealingSolver;
import pl.radoslawkarwacki.solver.impl.TSPRecorder;
import pl.radoslawkarwacki.solver.impl.TwoOptSwapSolver;

import javax.swing.*;

import java.awt.EventQueue;
import java.awt.BorderLayout;
import java.awt.Color;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main {

    public static final boolean ANNEALING = true; // false for 2-opt

    // 2-opt swap parameters (required for annealing also)
    public static final int NUMBER_OF_CITIES = 200;
    public static final int NUMBER_OF_TRIALS = 10000;
    public static final long RANDOM_SEED = 124531;

    // additional parameters required for annealing
    public static final int INITIAL_TEMPERATURE = 200;
    public static final double MINIMAL_TEMPERATURE = 0.00001;
    public static final double COOLING_COEFFICIENT = 0.99999;

    // delay between frames
    public static final int DELAY_MS = 1;

    //
    public static final int FRAMES_IN_BETWEEN = 10;

    public static void main(String[] args) {
        TSPUseCase annealingSolver;
        TSPUseCase twoOptSwapSolver;
        TSPRecorder recorder;
        TSPSolver solver;

        Random r = new Random(RANDOM_SEED);

        List<Point> points = new ArrayList<>();
        for (int i = 0; i < NUMBER_OF_CITIES; i++){
            points.add(new Point(r,1000,600));
        }
        if (ANNEALING) {
            annealingSolver = new AnnealingSolver(points, INITIAL_TEMPERATURE, MINIMAL_TEMPERATURE, NUMBER_OF_TRIALS, COOLING_COEFFICIENT);
            solver = new TSPSolver(annealingSolver);
        }
        else {
            twoOptSwapSolver = new TwoOptSwapSolver(points, NUMBER_OF_TRIALS);
            solver = new TSPSolver(twoOptSwapSolver);
        }
        recorder = new TSPRecorder();
        solver.addListener(recorder);
        solver.solve();
        SolutionHistory solutionHistory = recorder.getSolutionHistory();

        EventQueue.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
                System.err.println("error during UI manager startup");
            }

            @SuppressWarnings("unused")
            Main f = new Main(solutionHistory);
        });
    }


    private TSPDrawer tspDrawer;

    public Main(SolutionHistory pointList) {
        tspDrawer = new TSPDrawer(pointList, DELAY_MS, FRAMES_IN_BETWEEN);
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        JPanel panel = new JPanel(new BorderLayout());
        frame.add(panel);
        panel.setBackground(Color.white);
        JButton button1 = new JButton("Start");
        frame.add(button1, BorderLayout.NORTH);
        button1.addActionListener(e -> tspDrawer.startSimulation());
        panel.add(tspDrawer);
        frame.pack();
        frame.setVisible(true);
    }
}