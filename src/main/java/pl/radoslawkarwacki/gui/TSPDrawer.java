package pl.radoslawkarwacki.gui;

import org.jfree.data.xy.XYSeries;
import pl.radoslawkarwacki.chart.ChartDataSet;
import pl.radoslawkarwacki.chart.LineChart;
import pl.radoslawkarwacki.solver.RecordableTSPSolver;
import pl.radoslawkarwacki.solver.impl.AnnealingSolver;
import pl.radoslawkarwacki.solver.impl.TwoOptSwapSolver;

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
    private ChartDataSet chartDataSet = new ChartDataSet();
    private  XYSeries series1 = new XYSeries("TSP");

    public TSPDrawer() {
        setLayout(new BorderLayout());
        add(statusBar, BorderLayout.SOUTH);
        setOpaque(false);
        int timerTickMs = 1;
        setupTimer(timerTickMs);
    }

    private void setupTimer(int timeStep) {
        timer = new Timer(timeStep, e -> displayNextSimulationStep(5));
        timer.setRepeats(true);
        timer.setCoalesce(true);
    }

    private void displayNextSimulationStep(int omitFrames) {
        nextFrame += omitFrames;
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
        series1.add(nextFrame,solution.getCostAtFrame(nextFrame));
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
        chartDataSet.addSeriesToCollection(new XYSeries("Dummy"));
        chartDataSet.addSeriesToCollection(series1);
        new LineChart("TSP", chartDataSet.getDataset()).showChart();
    }

    public void startSimulation() {
        nextFrame = 0;
        initializeSolver();
        timer.start();
    }

    private void initializeSolver() {
        long seed = 1353;
        int numberOfCities = 100;
        Random random = new Random(seed);
        int pointsRangeX = 1000;
        int pointsRangeY = 600;
        int initialTemperature = 100;
        double minimalTemperature = 0.0001;
        int numberOfTrials = 10000;
        double coolingCoefficient = 0.999;

        solver = new AnnealingSolver(   numberOfCities,
                                        random,
                                        pointsRangeX,
                                        pointsRangeY,
                                        initialTemperature,
                                        minimalTemperature,
                                        numberOfTrials,
                                        coolingCoefficient);
        solver.solve();
        solution = new SolutionDrawer(solver.getSolutionHistory());
        totalFrames = solution.getNoOfFrames();
    }
}