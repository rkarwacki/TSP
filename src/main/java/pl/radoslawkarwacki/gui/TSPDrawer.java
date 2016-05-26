package pl.radoslawkarwacki.gui;

import pl.radoslawkarwacki.solver.RecordableTSPSolver;
import pl.radoslawkarwacki.solver.impl.AnnealingSolver;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class TSPDrawer extends JPanel {

    private RecordableTSPSolver solver;
    private int totalFrames;
    private Timer timer;
    private SolutionDrawer solution = null;
    private JLabel statusBar = new JLabel(" ");
    private int nextFrame;

    public TSPDrawer() {
        setLayout(new BorderLayout());
        add(statusBar, BorderLayout.SOUTH);
        setOpaque(false);
        int playbackTimeStep = 1;
        setupTimer(playbackTimeStep);
    }

    private void setupTimer(int time_step) {
        timer = new Timer(time_step, e -> displayNextSimulationStep());
        timer.setRepeats(true);
        timer.setCoalesce(true);
    }

    private void displayNextSimulationStep() {
        int replaySpeed = 10;
        nextFrame += replaySpeed;
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
        updateStatusBarWithCurrentFrameAndCostData();
    }

    private void updateStatusBarWithCurrentFrameAndCostData() {
        statusBar.setText("Iteration: " + (nextFrame + 1) + "/" + totalFrames + ", cost: " + solution.getCostAtFrame(nextFrame));
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
        nextFrame = 0;
        initializeSolver();
        timer.start();
    }

    private void initializeSolver() {
        long seed = 1345342;
        Random r = new Random(seed);
        solver = new AnnealingSolver(10,r,100,0.1,100,0.97);
        solver.solve();
        solution = new SolutionDrawer(solver.getSolutionHistory());
        totalFrames = solution.getNoOfFrames();
    }
}