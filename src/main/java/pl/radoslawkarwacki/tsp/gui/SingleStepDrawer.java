package pl.radoslawkarwacki.tsp.gui;

import pl.radoslawkarwacki.tsp.model.Point;

import java.awt.*;
import java.awt.geom.Line2D;
import java.util.*;
import java.util.List;

public class SingleStepDrawer {

    private List<Point> step = new ArrayList<>();

    public void setStep(java.util.List<Point> step) {
        this.step = step;
    }

    public void draw(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        for (int i = 0; i < step.size()-1; i++) {
            g2.draw(new Line2D.Double(step.get(i).getX(), step.get(i).getY(), step.get(i + 1).getX(), step.get(i + 1).getY()));
        }
        Point first = step.get(0);
        Point last = step.get(step.size() - 1);
        g2.draw(new Line2D.Double(first.getX(), first.getY(), last.getX(), last.getY()));
    }
}
