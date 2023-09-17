package pl.radoslawkarwacki.tsp.gui;

import pl.radoslawkarwacki.tsp.model.SolutionHistory;

import javax.swing.*;
import java.awt.*;

import static pl.radoslawkarwacki.tsp.Main.DELAY_MS;
import static pl.radoslawkarwacki.tsp.Main.FRAMES_IN_BETWEEN;

public class Window {
    private TSPDrawer tspDrawer;

    public Window(SolutionHistory pointList, int windowSizeX, int windowSizeY) {
        tspDrawer = new TSPDrawer(pointList, DELAY_MS, FRAMES_IN_BETWEEN, windowSizeX, windowSizeY);
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        JPanel panel = new JPanel(new BorderLayout());
        frame.add(panel);
        panel.setBackground(Color.white);
        JButton button1 = new JButton("Start");
        frame.add(button1, BorderLayout.NORTH);
        button1.addActionListener(e -> tspDrawer.startSimulation());
        panel.add(tspDrawer);
        frame.pack();
        frame.setVisible(true);
    }
}
