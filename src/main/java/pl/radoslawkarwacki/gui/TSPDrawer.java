package pl.radoslawkarwacki.gui;

import pl.radoslawkarwacki.solver.RecordableTSPSolver;
import pl.radoslawkarwacki.solver.impl.AnnealingSolver;
import pl.radoslawkarwacki.solver.impl.TwoOptSwapSolver;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class TSPDrawer extends JPanel {

    private RecordableTSPSolver solver;

    int totalFrames;
    private int frameToDisplay;
    private Timer timer;
    public SolutionDrawer solution = null;
    private int replaySpeed = 10;
    private JLabel statusBar = new JLabel(" ");

    private int nextFrame;

    public TSPDrawer() {
        setLayout(new BorderLayout());
        add(statusBar, BorderLayout.SOUTH);
        setOpaque(false);
        int time_step = 1;
        timer = new Timer(time_step, e -> {
            solution = new SolutionDrawer(solver.getSolutionHistory());
            nextFrame = frameToDisplay+=replaySpeed;
            totalFrames = solution.getNoOfFrames();
            if (nextFrame < totalFrames) {
                solution.setCurrentFrameToDraw(nextFrame);
                repaint();
            }
            else {
                nextFrame = totalFrames - 1;
                solution.setCurrentFrameToDraw(nextFrame);
                repaint();
                stopSimulation();
            }
            getCurrentFrameAndCostData();
        });
        timer.setRepeats(true);
        timer.setCoalesce(true);
    }

    private void getCurrentFrameAndCostData() {
        statusBar.setText("Iteration: " + (nextFrame + 1) +"/"+ totalFrames + ", cost: " + solution.getCostAtFrame(nextFrame));
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(1000, 600);
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (solution!=null) {
            solution.draw(g);
        }
    }

    private void stopSimulation() {
        timer.stop();
    }

    public void startSimulation() {
        frameToDisplay = 0;
        initializeSolver();
        timer.start();
    }

    private void initializeSolver() {
        long seed = 1345342;
        Random r = new Random(seed);
        boolean anneal = true;
        if (anneal) {
            solver = new AnnealingSolver(10,r,100,0.1,100,0.97);
            solver.solve();
        }
        else {
            solver = new TwoOptSwapSolver(30,r,100);
            solver.solve();
        }
    }
}