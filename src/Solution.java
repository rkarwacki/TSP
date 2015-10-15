import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

/**
 * Created by radek on 19.06.15.
 */
class SolutionStep{
    public ArrayList<Point> step = new ArrayList<>();
    public SolutionStep(){}
    public SolutionStep(ArrayList<Point> points){
        this.step = points;
    }

    public void addPoint(Point p){
        step.add(p);
    }

    public void draw(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
            for (int i = 0; i < step.size()-1; i++) {
                g2.draw(new Line2D.Double(step.get(i).getX(), step.get(i).getY(), step.get(i + 1).getX(), step.get(i + 1).getY()));
        }
        Point first = step.get(0);
        Point last = step.get(step.size()-1);
        g2.draw(new Line2D.Double(first.getX(), first.getY(), last.getX(), last.getY()));
    }
}

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
    /*
    public void draw(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        if (noOfFrames < framesTotal) {
            for (int i = 0; i < lastFrameToDisplay; i++) {
                g.clearRect(0, 0, 1000, 600);
                for (Point point : pointsToVisit) {
                    g2.fill(new Rectangle2D.Double(point.getX(), point.getY(), 4, 4));
                }
                solutionSteps.getStep(i).draw(g);
            }
        }
    }*/
}