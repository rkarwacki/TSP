package pl.radoslawkarwacki.tsp.gui;

import pl.radoslawkarwacki.tsp.config.AppConfig;
import pl.radoslawkarwacki.tsp.model.SolutionHistory;
import pl.radoslawkarwacki.tsp.solution.TSPSolutionRunner;

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
        gbc.insets = new Insets(4, 6, 4, 6);
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

        JTextField initialTemp = new JTextField(String.valueOf(defaults.getInitialTemperature()), 6);
        JTextField minimalTemp = new JTextField(String.valueOf(defaults.getMinimalTemperature()), 8);
        JTextField coolingCoeff = new JTextField(String.valueOf(defaults.getCoolingCoefficient()), 8);

        JTextField windowW = new JTextField(String.valueOf(defaults.getWindowSizeX()), 6);
        JTextField windowH = new JTextField(String.valueOf(defaults.getWindowSizeY()), 6);

        JButton start = new JButton("Start");

        int row = 0;
        addRow(panel, gbc, row++, "Algorithm:", algorithm);
        addRow(panel, gbc, row++, "Cities:", numberOfCities);
        addRow(panel, gbc, row++, "Trials:", numberOfTrials);
        addRow(panel, gbc, row++, "Random seed:", randomSeed);
        addRow(panel, gbc, row++, "Range X:", rangeX);
        addRow(panel, gbc, row++, "Range Y:", rangeY);
        addRow(panel, gbc, row++, "Delay (ms):", delayMs);
        addRow(panel, gbc, row++, "Frames between:", framesInBetween);
        addRow(panel, gbc, row++, "Initial temp:", initialTemp);
        addRow(panel, gbc, row++, "Minimal temp:", minimalTemp);
        addRow(panel, gbc, row++, "Cooling coeff.:", coolingCoeff);
        addRow(panel, gbc, row++, "Window width:", windowW);
        addRow(panel, gbc, row++, "Window height:", windowH);
        gbc.gridx = 1; gbc.gridy = row; gbc.gridwidth = 2;
        panel.add(drawChart, gbc);
        row++;
        gbc.gridx = 1; gbc.gridy = row; gbc.gridwidth = 1;
        panel.add(start, gbc);

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

                AppConfig config = new AppConfig(
                        annealing, nCities, nTrials, seed, initT, minT, cool, chart, dMs, fib, rx, ry, w, h
                );

                // Prepare a modal progress dialog with an indeterminate progress bar
                JDialog progress = new JDialog(frame, "Solving...", true);
                progress.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
                progress.setLayout(new BorderLayout(8, 8));
                progress.add(new JLabel("Running " + (annealing ? "Simulated Annealing" : "2-opt") + " ..."), BorderLayout.NORTH);
                JProgressBar bar = new JProgressBar();
                bar.setIndeterminate(true);
                progress.add(bar, BorderLayout.CENTER);
                JButton cancel = new JButton("Close");
                cancel.setEnabled(false);
                progress.add(cancel, BorderLayout.SOUTH);
                progress.pack();
                progress.setLocationRelativeTo(frame);

                // Run solving off the EDT
                start.setEnabled(false);
                SwingWorker<SolutionHistory, Void> worker = new SwingWorker<>() {
                    @Override
                    protected SolutionHistory doInBackground() {
                        return new TSPSolutionRunner(config).solveTSP();
                    }
                    @Override
                    protected void done() {
                        try {
                            SolutionHistory history = get();
                            progress.dispose();
                            showSimulation(history, config);
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

    private void addRow(JPanel panel, GridBagConstraints gbc, int row, String label, JComponent component) {
        gbc.gridx = 0; gbc.gridy = row; gbc.weightx = 0.0;
        panel.add(new JLabel(label), gbc);
        gbc.gridx = 1; gbc.gridy = row; gbc.weightx = 1.0;
        panel.add(component, gbc);
    }

    private void showSimulation(SolutionHistory history, AppConfig config) {
        if (tspDrawer != null) {
            centerPanel.remove(tspDrawer);
        }
        tspDrawer = new TSPDrawer(history, config.getDelayMs(), config.getFramesInBetween(),
                config.getWindowSizeX(), config.getWindowSizeY(), config.isDrawChart());
        centerPanel.add(tspDrawer, BorderLayout.CENTER);
        centerPanel.revalidate();
        frame.pack();
        tspDrawer.startSimulation();
    }
}
