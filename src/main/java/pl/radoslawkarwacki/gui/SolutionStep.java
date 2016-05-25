package pl.radoslawkarwacki.gui;

import pl.radoslawkarwacki.model.Point;

import java.awt.*;
import java.awt.geom.Line2D;
import java.util.ArrayList;

public class SolutionStep{

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
        pl.radoslawkarwacki.model.Point first = step.get(0);
        pl.radoslawkarwacki.model.Point last = step.get(step.size() - 1);
        g2.draw(new Line2D.Double(first.getX(), first.getY(), last.getX(), last.getY()));
    }
}

