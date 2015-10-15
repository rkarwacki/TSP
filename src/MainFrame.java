import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import javax.swing.*;

public class MainFrame {

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
                }

                @SuppressWarnings("unused")
                MainFrame f = new MainFrame();
            }
        });
    }
    public JFrame frame = new JFrame();
    public JPanel panel = new JPanel(new BorderLayout());
    private TSPDrawer tsp;
    public static JLabel label1 = new JLabel(" ");

    public MainFrame() {
        tsp = new TSPDrawer();
        JButton button1 = new JButton("Start");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(panel);
        panel.setBackground(Color.white);
        frame.add(button1, BorderLayout.NORTH);
        frame.add(label1,BorderLayout.SOUTH);
        button1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                tsp.startSimulation(true);
            }
        });
        panel.add(tsp);
        frame.pack();
        frame.setVisible(true);
    }


    public class TSPDrawer extends JPanel {
        private final int time_step = 10;
        private Timer timer;
        private int displayNoOfSteps = 0;
        public Solution solution = null;
        private int noOfFrames = 0;

        private int noOfPoints = 200;
        private boolean anneal = true;
        private long seed = 12;
        private double temp_start=100;
        private double t_min = 0.0001;
        private double lambda = 0.99999;
        private int trials = 10000;
        private int recordWithStep = 70;

        public TSPDrawer() {
            setOpaque(false);
            timer = new Timer(time_step, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    solution = new Solution(Solution.getPlayback(),displayNoOfSteps);
                    noOfFrames = Solution.getNoOfFrames();
                    if (displayNoOfSteps<noOfFrames) {
                        if(anneal)
                        MainFrame.label1.setText("Iteration: " + displayNoOfSteps*recordWithStep +"/"+Solver.iterations + ", cost: " + Double.toString(Solver.getTotalTourCost(Solution.playbackSolution.get(displayNoOfSteps).step)));
                        else
                            MainFrame.label1.setText("Iteration: " + displayNoOfSteps + ", cost: " + Double.toString(Solver.getTotalTourCost(Solution.playbackSolution.get(displayNoOfSteps).step)));
                        displayNoOfSteps++;
                        repaint();
                    }
                    else
                        startSimulation(false);
                }
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
                displayNoOfSteps=0;
                tsp.removeAll();
                Random r = new Random(seed);
                Solver s = new Solver(noOfPoints, r);
                if (anneal)
                s.solveUsingSimulatedAnnealing(temp_start,t_min,lambda,trials,recordWithStep);
                else
                s.solveUsing2Opt();
                timer.start();

            }
            else {
                timer.stop();
            }
        }
    }
}