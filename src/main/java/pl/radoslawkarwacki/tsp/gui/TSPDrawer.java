package pl.radoslawkarwacki.tsp.gui;

import org.jfree.data.xy.XYSeries;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.radoslawkarwacki.tsp.chart.ChartDataSet;
import pl.radoslawkarwacki.tsp.model.SolutionHistory;
import pl.radoslawkarwacki.tsp.solution.RunStats;

import javax.swing.*;
import java.awt.*;



public class TSPDrawer extends JPanel {

    private static final Logger logger = LoggerFactory.getLogger(TSPDrawer.class);

    private static int WINDOW_SIZE_X;
    private static int WINDOW_SIZE_Y;

    private int totalFramesCount;
    private int nextFrameNumber;
    private int currentFrameToDisplay;

    private SolutionDrawer solutionDrawer = null;
    private Timer timer;
    private final boolean drawChart;
    private final boolean playAnimation;
    private final SolutionHistory history;
    private final int replaySpeed;
    private final RunStats runStats;

    private JLabel statusBar = new JLabel(" ");
    private JSlider frameSlider;
    private ChartDataSet chartDataSet = new ChartDataSet();
    private XYSeries series1 = new XYSeries("TSP");
    private boolean chartSeriesInitialized = false;


    public TSPDrawer(SolutionHistory history, int delayMs, int replaySpeed, int windowSizeX, int windowSizeY, boolean drawChart, boolean playAnimation, RunStats runStats) {
        initializeWindow(windowSizeX, windowSizeY);
        this.drawChart = drawChart;
        this.playAnimation = playAnimation;
        this.history = history;
        this.replaySpeed = replaySpeed;
        this.runStats = runStats;

        timer = new Timer(delayMs, e -> {
            initializeSolutionDrawer(this.history, this.replaySpeed);
            if (nextFrameNumber < totalFramesCount) {
                drawFrame();
            } else {
                drawLastFrame();
            }
            if (!chartSeriesInitialized) {
                series1.add(nextFrameNumber, solutionDrawer.getCostAtFrame(nextFrameNumber));
            }
            updateStatusBarWithCurrentFrameAndCostData();
        });
        initializeTimer();
    }

    private void initializeSolutionDrawer(SolutionHistory history, int replaySpeed) {
        solutionDrawer = new SolutionDrawer(history);
        nextFrameNumber = currentFrameToDisplay += replaySpeed;
        totalFramesCount = solutionDrawer.getNoOfFrames();
        int max = Math.max(0, totalFramesCount - 1);
        if (frameSlider.getMaximum() != max) {
            frameSlider.setMaximum(max);
        }
        if (!frameSlider.getValueIsAdjusting()) {
            frameSlider.setValue(Math.min(nextFrameNumber, max));
        }
        if (drawChart && !chartSeriesInitialized && solutionDrawer != null) {
            for (int i = 0; i < totalFramesCount; i++) {
                series1.add(i, solutionDrawer.getCostAtFrame(i));
            }
            chartSeriesInitialized = true;
        }
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

        JPanel bottom = new JPanel(new BorderLayout());
        bottom.add(statusBar, BorderLayout.WEST);

        frameSlider = new JSlider(0, 0, 0);
        frameSlider.setEnabled(false);
        frameSlider.addChangeListener(e -> {
            if (!frameSlider.isEnabled()) {
                return;
            }
            int v = frameSlider.getValue();
            nextFrameNumber = Math.min(Math.max(v, 0), Math.max(0, totalFramesCount - 1));
            if (solutionDrawer != null) {
                solutionDrawer.setCurrentFrameToDraw(nextFrameNumber);
                repaint();
                updateStatusBarWithCurrentFrameAndCostData();
            }
        });
        bottom.add(frameSlider, BorderLayout.CENTER);

        add(bottom, BorderLayout.SOUTH);

        setOpaque(false);
        WINDOW_SIZE_X = windowSizeX;
        WINDOW_SIZE_Y = windowSizeY;
    }


    private void updateStatusBarWithCurrentFrameAndCostData() {
        statusBar.setText("Iteration: " + (nextFrameNumber + 1) + "/" + totalFramesCount + ", cost: " + solutionDrawer.getCostAtFrame(nextFrameNumber));
        statusBar.setHorizontalAlignment(JLabel.CENTER);
        statusBar.setVerticalAlignment(JLabel.CENTER);
        statusBar.setHorizontalTextPosition(JLabel.CENTER);
        statusBar.setVerticalTextPosition(JLabel.CENTER);
        statusBar.setHorizontalAlignment(JLabel.CENTER);
        statusBar.setVerticalAlignment(JLabel.CENTER);
        statusBar.setHorizontalTextPosition(JLabel.CENTER);
        statusBar.setVerticalTextPosition(JLabel.CENTER);
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
        if (frameSlider != null) {
            frameSlider.setEnabled(true);
            int max = Math.max(0, totalFramesCount - 1);
            frameSlider.setMaximum(max);
            frameSlider.setValue(Math.min(nextFrameNumber, max));
        }
        if (drawChart) {
            chartDataSet.addSeriesToCollection(new XYSeries("Result"));
            chartDataSet.addSeriesToCollection(series1);
            org.jfree.chart.JFreeChart chart = org.jfree.chart.ChartFactory.createXYLineChart(
                    "TSP",
                    "Iteration",
                    "Cost",
                    chartDataSet.getDataset(),
                    org.jfree.chart.plot.PlotOrientation.VERTICAL,
                    true,
                    true,
                    false
            );
            org.jfree.chart.ChartPanel chartPanel = new org.jfree.chart.ChartPanel(chart);
            javax.swing.JFrame chartFrame = new javax.swing.JFrame("TSP Cost");
            chartFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            javax.swing.JPanel container = new javax.swing.JPanel(new java.awt.BorderLayout());
            container.add(chartPanel, java.awt.BorderLayout.CENTER);
            if (runStats != null) {
                String details = formatRunStatsDetails(runStats);
                logger.info("Run stats: {} — {}, {}", runStats.getAlgorithm(), runStats.getStopReason(), details);
                String info = "<html><b>" + runStats.getAlgorithm() + "</b> — " + runStats.getStopReason()
                        + "<br/>" + details
                        + "</html>";
                javax.swing.JLabel infoLabel = new javax.swing.JLabel(info);
                infoLabel.setBorder(javax.swing.BorderFactory.createEmptyBorder(6, 10, 6, 10));
                container.add(infoLabel, java.awt.BorderLayout.SOUTH);
            }
            chartFrame.setContentPane(container);
            chartFrame.setSize(2000, 1000);
            java.awt.Window parent = SwingUtilities.getWindowAncestor(this);
            if (parent != null) {
                chartFrame.setLocationRelativeTo(parent);
            }
            chartFrame.setVisible(true);
        }
    }

    private static String formatRunStatsDetails(RunStats runStats) {
        return "Frames: " + runStats.getTotalFrames()
                + ("Annealing".equals(runStats.getAlgorithm())
                    ? String.format(", final T=%.9f, minimal T=%.9f, temp lowerings=%d, max trials w/o improvement=%d",
                        runStats.getFinalTemperature(), runStats.getMinimalTemperature(), runStats.getStepsLowered(), runStats.getMaxTrials())
                    : String.format(", max trials w/o improvement=%d", runStats.getMaxTrials()));
    }

    public void startSimulation() {
        currentFrameToDisplay = 0;
        if (playAnimation) {
            timer.start();
        } else {
            initializeSolutionDrawer(history, replaySpeed);
            nextFrameNumber = totalFramesCount - 1;
            solutionDrawer.setCurrentFrameToDraw(nextFrameNumber);
            repaint();
            if (!chartSeriesInitialized) {
                series1.add(nextFrameNumber, solutionDrawer.getCostAtFrame(nextFrameNumber));
            }
            updateStatusBarWithCurrentFrameAndCostData();
            stopSimulation();
        }
    }
}
