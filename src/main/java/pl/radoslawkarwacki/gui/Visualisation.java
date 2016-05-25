package pl.radoslawkarwacki.gui;

import pl.radoslawkarwacki.solver.Solver;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class Visualisation extends JFrame {
    private DrawingPanel contentPane;
    private int noOfPoints = 500;
    private int delay_time = 30;
    public Visualisation() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 1100, 700);
        contentPane = new DrawingPanel();
        setContentPane(contentPane);
        JButton start = new JButton("Start");
        start.addActionListener(actionEvent -> {
            Solver s = new Solver(10, new Random(1234));
            s.solveUsingNN(0);
            new Timer(delay_time, e -> contentPane.repaint()).start();
            contentPane.repaint();
        });
        contentPane.add(start);
    }
}
