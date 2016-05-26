package pl.radoslawkarwacki.entry;


import pl.radoslawkarwacki.gui.TSPDrawer;

import javax.swing.*;
import java.awt.*;

public class MainFrame {

    public JFrame frame = new JFrame();
    public JPanel panel = new JPanel(new BorderLayout());
    private JButton button1 = new JButton("Start");

    private TSPDrawer tsp;

    public MainFrame() {
        tsp = new TSPDrawer();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(panel);
        panel.setBackground(Color.white);
        frame.add(button1, BorderLayout.NORTH);
        button1.addActionListener(e -> tsp.startSimulation());
        panel.add(tsp);
        frame.pack();
        frame.setVisible(true);
    }

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
}