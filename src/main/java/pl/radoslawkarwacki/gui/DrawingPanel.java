package pl.radoslawkarwacki.gui;

import javax.swing.*;
import java.awt.*;



public class DrawingPanel extends JPanel{
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