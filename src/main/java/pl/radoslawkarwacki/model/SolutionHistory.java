package pl.radoslawkarwacki.model;

import java.util.ArrayList;

public class SolutionHistory {

    private ArrayList<ArrayList<Point>> steps = new ArrayList<>();

    public void addStep(ArrayList<Point> step){
        steps.add(step);
    }

    public ArrayList<Point> getStep(int stepNumber){
        return steps.get(stepNumber);
    }

    public int getNumberOfFrames(){
        return steps.size();
    }

}
