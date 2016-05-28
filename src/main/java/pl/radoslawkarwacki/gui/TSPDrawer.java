package pl.radoslawkarwacki.gui;

import pl.radoslawkarwacki.model.SolutionHistory;

import javax.swing.*;
import java.awt.*;

public class TSPDrawer extends JPanel {

    private int totalFrames;
    private int frameToDisplay;
    private Timer timer;
    private SolutionDrawer solution = null;
    private int replaySpeed = 10;
    private JLabel statusBar = new JLabel(" ");

    private int nextFrame;

    public TSPDrawer(SolutionHistory history) {
        setLayout(new BorderLayout());
        add(statusBar, BorderLayout.SOUTH);
        setOpaque(false);
        int time_step = 1;
        timer = new Timer(time_step, e -> {
            solution = new SolutionDrawer(history);
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
        frameToDisplay = 0;
        timer.start();
    }
}