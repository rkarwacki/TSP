package pl.radoslawkarwacki.gui;

import pl.radoslawkarwacki.model.Point;
import pl.radoslawkarwacki.model.SolutionHistory;
import pl.radoslawkarwacki.solver.TSPSolver;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.util.List;

public class SolutionDrawer {

    private SolutionHistory solutionHistory;
    private SingleStepDrawer singleStepDrawer = new SingleStepDrawer();
    private int currentFrameToDraw;
    private List<Point> points;

    public SolutionDrawer(SolutionHistory solutionHistory){
        this.solutionHistory = solutionHistory;
        points = solutionHistory.getStep(0);
    }

    public double getCostAtFrame(int frameNumber){
        return TSPSolver.getTotalTourCost(solutionHistory.getStep(frameNumber));
    }

    public void setCurrentFrameToDraw(int currentFrameToDraw) {
        this.currentFrameToDraw = currentFrameToDraw;
    }

    public int getNoOfFrames(){
        return solutionHistory.getNumberOfFrames();
    }

    public void draw(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g.clearRect(0, 0, 1000, 600);
        for (Point point : points) {
            g2.fill(new Ellipse2D.Double(point.getX()-3, point.getY()-3, 6, 6));
        }
        singleStepDrawer.setStep(solutionHistory.getStep(currentFrameToDraw));
        singleStepDrawer.draw(g);
    }
}