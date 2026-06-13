package pl.radoslawkarwacki.tsp.gui;

import pl.radoslawkarwacki.tsp.config.AppConfig;
import pl.radoslawkarwacki.tsp.model.SolutionHistory;
import pl.radoslawkarwacki.tsp.solution.TSPSolutionRunner;
import pl.radoslawkarwacki.tsp.solution.SolveResult;
import pl.radoslawkarwacki.tsp.solver.impl.annealing.AnnealingSolver;

import javax.swing.*;
import java.awt.*;

public class Window {
    private TSPDrawer tspDrawer;
    private final JFrame frame = new JFrame("TSP");
    private final JPanel centerPanel = new JPanel(new BorderLayout());

    public Window() {
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        JPanel controlPanel = buildControlPanel();

        frame.add(controlPanel, BorderLayout.NORTH);
        frame.add(centerPanel, BorderLayout.CENTER);

        centerPanel.setBackground(Color.white);

        frame.pack();
        frame.setVisible(true);
    }

    private JPanel buildControlPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(2, 4, 2, 4);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        AppConfig defaults = AppConfig.defaults();

        JComboBox<String> algorithm = new JComboBox<>(new String[]{"Annealing", "2-opt"});
        algorithm.setSelectedIndex(defaults.isAnnealing() ? 0 : 1);

        JTextField numberOfCities = new JTextField(String.valueOf(defaults.getNumberOfCities()), 6);
        JTextField numberOfTrials = new JTextField(String.valueOf(defaults.getNumberOfTrials()), 6);
        JTextField randomSeed = new JTextField(String.valueOf(defaults.getRandomSeed()), 8);
        JTextField rangeX = new JTextField(String.valueOf(defaults.getRangeX()), 6);
        JTextField rangeY = new JTextField(String.valueOf(defaults.getRangeY()), 6);
        JTextField delayMs = new JTextField(String.valueOf(defaults.getDelayMs()), 4);
        JTextField framesInBetween = new JTextField(String.valueOf(defaults.getFramesInBetween()), 4);
        JCheckBox drawChart = new JCheckBox("Draw chart", defaults.isDrawChart());
        JCheckBox playAnimation = new JCheckBox("Play animation", false);

        JTextField initialTemp = new JTextField(String.valueOf(defaults.getInitialTemperature()), 6);
        JTextField minimalTemp = new JTextField(String.valueOf(defaults.getMinimalTemperature()), 8);
        JTextField coolingCoeff = new JTextField(String.valueOf(defaults.getCoolingCoefficient()), 8);

        JTextField windowW = new JTextField(String.valueOf(defaults.getWindowSizeX()), 6);
        JTextField windowH = new JTextField(String.valueOf(defaults.getWindowSizeY()), 6);

        JButton start = new JButton("Start");

        int row = 0;
        addCompactRow(panel, gbc, row++, new LabelField("Algorithm:", algorithm));
        addCompactRow(panel, gbc, row++,
                new LabelField("Cities:", numberOfCities),
                new LabelField("Trials:", numberOfTrials),
                new LabelField("Seed:", randomSeed));
        addCompactRow(panel, gbc, row++,
                new LabelField("Range X:", rangeX),
                new LabelField("Range Y:", rangeY),
                new LabelField("Delay (ms):", delayMs),
                new LabelField("Frames:", framesInBetween));
        addCompactRow(panel, gbc, row++,
                new LabelField("Initial temp:", initialTemp),
                new LabelField("Minimal temp:", minimalTemp),
                new LabelField("Cooling:", coolingCoeff));
        addCompactRow(panel, gbc, row++,
                new LabelField("Width:", windowW),
                new LabelField("Height:", windowH));
        addComponentRow(panel, gbc, row++, drawChart, playAnimation, start);

        start.addActionListener(e -> {
            try {
                boolean annealing = algorithm.getSelectedIndex() == 0;
                int nCities = Integer.parseInt(numberOfCities.getText().trim());
                int nTrials = Integer.parseInt(numberOfTrials.getText().trim());
                long seed = Long.parseLong(randomSeed.getText().trim());
                int rx = Integer.parseInt(rangeX.getText().trim());
                int ry = Integer.parseInt(rangeY.getText().trim());
                int dMs = Integer.parseInt(delayMs.getText().trim());
                int fib = Integer.parseInt(framesInBetween.getText().trim());
                int initT = Integer.parseInt(initialTemp.getText().trim());
                double minT = Double.parseDouble(minimalTemp.getText().trim());
                double cool = Double.parseDouble(coolingCoeff.getText().trim());
                int w = Integer.parseInt(windowW.getText().trim());
                int h = Integer.parseInt(windowH.getText().trim());
                boolean chart = drawChart.isSelected();
                boolean play = playAnimation.isSelected();

                AppConfig config = new AppConfig(
                        annealing, nCities, nTrials, seed, initT, minT, cool, chart, dMs, fib, rx, ry, w, h
                );

                // Prepare a modal progress dialog with a progress bar
                JDialog progress = new JDialog(frame, "Solving...", true);
                progress.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
                progress.setLayout(new BorderLayout(8, 8));
                progress.add(new JLabel("Running " + (annealing ? "Simulated Annealing" : "2-opt") + " ..."), BorderLayout.NORTH);
                JProgressBar bar = new JProgressBar();
                if (annealing) {
                    bar.setIndeterminate(false);
                    bar.setStringPainted(true);
                } else {
                    bar.setIndeterminate(true);
                    bar.setStringPainted(false);
                }
                progress.add(bar, BorderLayout.CENTER);
                JButton cancel = new JButton("Close");
                cancel.setEnabled(false);
                progress.add(cancel, BorderLayout.SOUTH);
                progress.pack();
                progress.setLocationRelativeTo(frame);

                // Build a progress listener for annealing
                AnnealingSolver.ProgressListener listener = null;
                if (annealing) {
                    listener = new AnnealingSolver.ProgressListener() {
                        @Override
                        public void onStart(int totalSteps) {
                            SwingUtilities.invokeLater(() -> {
                                bar.setMinimum(0);
                                bar.setMaximum(Math.max(1, totalSteps));
                                bar.setValue(0);
                            });
                        }

                        @Override
                        public void onProgress(int currentStep, int totalSteps) {
                            SwingUtilities.invokeLater(() -> {
                                if (bar.getMaximum() != Math.max(1, totalSteps)) {
                                    bar.setMaximum(Math.max(1, totalSteps));
                                }
                                bar.setValue(Math.min(currentStep, bar.getMaximum()));
                            });
                        }
                    };
                }

                // Run solving off the EDT
                start.setEnabled(false);
                AnnealingSolver.ProgressListener finalListener = listener;
                SwingWorker<SolveResult, Void> worker = new SwingWorker<>() {
                    @Override
                    protected SolveResult doInBackground() {
                        return new TSPSolutionRunner(config).solveTSP(finalListener);
                    }
                    @Override
                    protected void done() {
                        try {
                            SolveResult result = get();
                            // Ensure bar shows completion for annealing
                            if (annealing) {
                                SwingUtilities.invokeLater(() -> {
                                    bar.setIndeterminate(false);
                                    bar.setValue(bar.getMaximum());
                                });
                            }
                            progress.dispose();
                            showSimulation(result, config, play);
                        } catch (Exception ex) {
                            progress.dispose();
                            JOptionPane.showMessageDialog(frame, "Error during solving:\n" + ex.getMessage(),
                                    "Error", JOptionPane.ERROR_MESSAGE);
                        } finally {
                            start.setEnabled(true);
                        }
                    }
                };
                worker.execute();
                progress.setVisible(true);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Please enter valid numeric values.\n" + ex.getMessage(),
                        "Invalid input", JOptionPane.ERROR_MESSAGE);
            }
        });

        return panel;
    }

    private record LabelField(String label, JComponent component) {}

    private void addCompactRow(JPanel panel, GridBagConstraints gbc, int row, LabelField... fields) {
        JPanel rowPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        for (LabelField field : fields) {
            rowPanel.add(new JLabel(field.label()));
            rowPanel.add(field.component());
        }
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(rowPanel, gbc);
        gbc.gridwidth = 1;
        gbc.weightx = 0.0;
    }

    private void addComponentRow(JPanel panel, GridBagConstraints gbc, int row, JComponent... components) {
        JPanel rowPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        for (JComponent component : components) {
            rowPanel.add(component);
        }
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(rowPanel, gbc);
        gbc.gridwidth = 1;
        gbc.weightx = 0.0;
    }

    private void showSimulation(SolveResult result, AppConfig config, boolean playAnimation) {
        if (tspDrawer != null) {
            centerPanel.remove(tspDrawer);
        }
        tspDrawer = new TSPDrawer(result.getHistory(), config.getDelayMs(), config.getFramesInBetween(),
                config.getWindowSizeX(), config.getWindowSizeY(), config.isDrawChart(), playAnimation, result.getStats());
        centerPanel.add(tspDrawer, BorderLayout.CENTER);
        centerPanel.revalidate();
        frame.pack();
        tspDrawer.startSimulation();
    }
}
