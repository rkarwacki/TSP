package pl.radoslawkarwacki.gui;

import pl.radoslawkarwacki.model.Point;
import pl.radoslawkarwacki.solver.Solver;


import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;

public class Solution {
    private ArrayList<Point> points = new ArrayList<>();
    public static ArrayList<SolutionStep> playbackSolution = new ArrayList<>();
    private int noOfFrames;
    private static int framesTotal;

    public Solution(ArrayList<SolutionStep> listOfSteps, int noOfFrames){
        this.noOfFrames=noOfFrames;
        playbackSolution=listOfSteps;
        framesTotal = playbackSolution.size();
        points = Solver.getPoints();
    }

    public static int getNoOfFrames(){
        return framesTotal;
    }

    public Solution(){}

    public static ArrayList<SolutionStep> getPlayback(){
        return playbackSolution;
    }
    public void clear(){
        playbackSolution.clear();
    }

    public void addStep(SolutionStep solutionStep){
        playbackSolution.add(solutionStep);
    }

    public void draw(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g.clearRect(0, 0, 1000, 600);
        for (Point point : points) {
            g2.fill(new Ellipse2D.Double(point.getX()-3, point.getY()-3, 6, 6));
        }
        playbackSolution.get(noOfFrames).draw(g);
    }
}