import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Created by radek on 19.06.15.
 *//*
class Visualisation extends JFrame {
    private DrawingPanel contentPane;
    private int noOfPoints = 500;
    private int delay_time = 30;
    public Visualisation() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 1100, 700);
        contentPane = new DrawingPanel();
        setContentPane(contentPane);
        JButton start = new JButton("Start");
        start.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Solver s = new Solver(10);
                s.solveUsingNN(0);
                new javax.swing.Timer(delay_time, new ActionListener(){
                    @Override
                    public void actionPerformed(ActionEvent e){
                            contentPane.repaint();
                    }
                }).start();
                contentPane.repaint();
            }
        });
        contentPane.add(start);
    }
}


class DrawingPanel extends JPanel{
    public int displayNoOfSteps = 1;

    DrawingPanel(){}
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Solution sol = new Solution(Solution.getPlayback(),displayNoOfSteps);
        sol.draw(g);

        if (displayNoOfSteps< Solution.getPlayback().size())
        displayNoOfSteps++;
        }
    }
*/