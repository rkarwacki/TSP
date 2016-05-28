package pl.radoslawkarwacki.entry;


import pl.radoslawkarwacki.gui.TSPDrawer;
import pl.radoslawkarwacki.model.SolutionHistory;
import pl.radoslawkarwacki.solver.TSPSolver;
import pl.radoslawkarwacki.solver.TSPUseCase;
import pl.radoslawkarwacki.solver.impl.AnnealingSolver;
import pl.radoslawkarwacki.solver.impl.TSPRecorder;
import pl.radoslawkarwacki.solver.impl.TwoOptSwapSolver;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class MainFrame {

    private JFrame frame = new JFrame();
    public JPanel panel = new JPanel(new BorderLayout());

    private TSPDrawer tsp;

    public MainFrame(SolutionHistory pointList) {
        tsp = new TSPDrawer(pointList);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.add(panel);
        panel.setBackground(Color.white);
        JButton button1 = new JButton("Start");
        frame.add(button1, BorderLayout.NORTH);
        button1.addActionListener(e -> tsp.startSimulation());
        panel.add(tsp);
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
                System.err.println("error during UI manager startup");
            }

            TSPUseCase annealingSolver;
            TSPUseCase twoOptSwapSolver;
            TSPRecorder recorder;
            TSPSolver solver;

            long seed = 1345342;
            Random r = new Random(seed);
            java.util.List<pl.radoslawkarwacki.model.Point> points = new ArrayList<>();
            for (int i = 0; i < 100; i++){
                points.add(new pl.radoslawkarwacki.model.Point(r,1000,600));
            }
            annealingSolver = new AnnealingSolver(points,300,0.00001,10000,0.99999);
            solver = new TSPSolver(annealingSolver);
            twoOptSwapSolver = new TwoOptSwapSolver(points, 10000);
            solver = new TSPSolver(twoOptSwapSolver);
            recorder = new TSPRecorder();
            solver.addListener(recorder);
            solver.solve();
            SolutionHistory solutionHistory = recorder.getSolutionHistory();

            @SuppressWarnings("unused")
            MainFrame f = new MainFrame(solutionHistory);
        });
    }
}