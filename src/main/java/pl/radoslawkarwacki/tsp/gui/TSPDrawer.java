package pl.radoslawkarwacki.tsp.gui;

import org.jfree.data.xy.XYSeries;
import pl.radoslawkarwacki.tsp.chart.ChartDataSet;
import pl.radoslawkarwacki.tsp.chart.LineChart;
import pl.radoslawkarwacki.tsp.model.SolutionHistory;

import javax.swing.*;
import java.awt.*;

import static pl.radoslawkarwacki.tsp.Main.DRAW_CHART;

public class TSPDrawer extends JPanel {

    private static int WINDOW_SIZE_X;
    private static int WINDOW_SIZE_Y;

    private int totalFramesCount;
    private int nextFrameNumber;
    private int currentFrameToDisplay;

    private SolutionDrawer solutionDrawer = null;
    private Timer timer;

    private JLabel statusBar = new JLabel(" ");
    private ChartDataSet chartDataSet = new ChartDataSet();
    private XYSeries series1 = new XYSeries("TSP");


    public TSPDrawer(SolutionHistory history, int delayMs, int replaySpeed, int windowSizeX, int windowSizeY) {
        initializeWindow(windowSizeX, windowSizeY);

        timer = new Timer(delayMs, e -> {
            initializeSolutionDrawer(history, replaySpeed);
            if (nextFrameNumber < totalFramesCount) {
                drawFrame();
            } else {
                drawLastFrame();
            }
            series1.add(nextFrameNumber, solutionDrawer.getCostAtFrame(nextFrameNumber));
            updateStatusBarWithCurrentFrameAndCostData();
        });
        initializeTimer();
    }

    private void initializeSolutionDrawer(SolutionHistory history, int replaySpeed) {
        solutionDrawer = new SolutionDrawer(history);
        nextFrameNumber = currentFrameToDisplay += replaySpeed;
        totalFramesCount = solutionDrawer.getNoOfFrames();
    }

    private void drawFrame() {
        solutionDrawer.setCurrentFrameToDraw(nextFrameNumber);
        repaint();
    }

    private void drawLastFrame() {
        nextFrameNumber = totalFramesCount - 1;
        drawFrame();
        stopSimulation();
    }

    private void initializeTimer() {
        timer.setRepeats(true);
        timer.setCoalesce(true);
    }

    private void initializeWindow(int windowSizeX, int windowSizeY) {
        setLayout(new BorderLayout());
        add(statusBar, BorderLayout.SOUTH);
        setOpaque(false);
        WINDOW_SIZE_X = windowSizeX;
        WINDOW_SIZE_Y = windowSizeY;
    }


    private void updateStatusBarWithCurrentFrameAndCostData() {
        statusBar.setText("Iteration: " + (nextFrameNumber + 1) + "/" + totalFramesCount + ", cost: " + solutionDrawer.getCostAtFrame(nextFrameNumber));
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(WINDOW_SIZE_X, WINDOW_SIZE_Y);
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (solutionDrawer != null) {
            solutionDrawer.draw(g);
        }
    }

    private void stopSimulation() {
        timer.stop();
        if (DRAW_CHART) {
            chartDataSet.addSeriesToCollection(new XYSeries("Result"));
            chartDataSet.addSeriesToCollection(series1);
            new LineChart("TSP", chartDataSet.getDataset()).showChart();
        }
    }

    public void startSimulation() {
        currentFrameToDisplay = 0;
        timer.start();
    }
}