package pl.radoslawkarwacki.entry;


import pl.radoslawkarwacki.gui.SolutionDrawer;
import pl.radoslawkarwacki.solver.RecordableTSPSolver;
import pl.radoslawkarwacki.solver.impl.AnnealingSolver;
import pl.radoslawkarwacki.solver.TSPSolver;
import pl.radoslawkarwacki.solver.impl.TwoOptSwapSolver;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class MainFrame {

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
                System.err.println("error during UI manager startup");
            }
            @SuppressWarnings("unused")
            MainFrame f = new MainFrame();
        });
    }
    public JFrame frame = new JFrame();
    public JPanel panel = new JPanel(new BorderLayout());
    private TSPDrawer tsp;
    public JLabel label1 = new JLabel(" ");

    public MainFrame() {
        tsp = new TSPDrawer();
        JButton button1 = new JButton("Start");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(panel);
        panel.setBackground(Color.white);
        frame.add(button1, BorderLayout.NORTH);
        frame.add(label1, BorderLayout.SOUTH);
        button1.addActionListener(e -> tsp.startSimulation(true));
        panel.add(tsp);
        frame.pack();
        frame.setVisible(true);
    }


    public class TSPDrawer extends JPanel {

        RecordableTSPSolver solver;

        int totalFrames;
        private int frameToDisplay = 0;
        private final int time_step = 10;
        private Timer timer;
        public SolutionDrawer solution = null;

        private boolean anneal = true;
        private long seed = 1345342;

        public TSPDrawer() {
            setOpaque(false);
            timer = new Timer(time_step, e -> {
                SolutionDrawer solutionDrawer = new SolutionDrawer(solver.getSolutionHistory());
                solutionDrawer.setCurrentFrameToDraw(frameToDisplay++);
                totalFrames = solutionDrawer.getNoOfFrames();
                if (frameToDisplay < totalFrames) {
//                    if(anneal)
//                        label1.setText("Iteration: " + displayNoOfSteps*recordWithStep +"/"+ TSPSolver.iterations + ", cost: " + Double.toString(TSPSolver.getTotalTourCost(SolutionDrawer.playbackSolution.get(displayNoOfSteps).step)));
//                    else
//                        label1.setText("Iteration: " + displayNoOfSteps + ", cost: " + Double.toString(TSPSolver.getTotalTourCost(SolutionDrawer.playbackSolution.get(displayNoOfSteps).step)));
//                    displayNoOfSteps++;
                    repaint();
                }
                else
                    startSimulation(false);
            });
            timer.setRepeats(true);
            timer.setCoalesce(true);
        }

        @Override
        public Dimension getPreferredSize() {
            return new Dimension(1000, 600);
        }

        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (!(solution==null)) {
                solution.draw(g);
            }
        }

        public void startSimulation(boolean run) {
            if (run){
                tsp.removeAll();
                Random r = new Random(seed);
                if (anneal) {
                    solver = new AnnealingSolver(30,r,100,0.1,100,0.9999);
                    solver.solve();
                }
                else {
                    solver = new TwoOptSwapSolver(30,r,100);
                    solver.solve();
                }
                timer.start();
            }
            else {
                timer.stop();
            }
        }
    }
}