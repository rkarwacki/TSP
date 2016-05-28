package pl.radoslawkarwacki.model;

import java.util.ArrayList;
import java.util.List;

public class SolutionHistory {

    private ArrayList<List<Point>> steps = new ArrayList<>();

    public void addStep(List<Point> step){
        steps.add(step);
    }

    public List<Point> getStep(int stepNumber){
        return steps.get(stepNumber);
    }

    public int getNumberOfFrames(){
        return steps.size();
    }

}
