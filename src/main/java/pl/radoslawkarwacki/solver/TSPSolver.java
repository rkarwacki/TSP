package pl.radoslawkarwacki.solver;

import pl.radoslawkarwacki.model.Point;

import java.util.*;


public class TSPSolver {

    private int iterationsWithoutImprovement;

    private List<Point> currentSolution;
    private List<Point> newSolution;

    private TSPUseCase tspUseCase;

    private Set<TSPSolverListener> listeners;

    public TSPSolver(TSPUseCase tspUseCase) {
        this.tspUseCase = tspUseCase;
        this.listeners = new HashSet<>();
    }

    public static double getTotalTourCost(List<Point> points) {
        double cost = 0;
        if (points.size() > 2) {
            for (int i = 0; i < points.size() - 1; i++) {
                cost += points.get(i).calculateDistanceToPoint(points.get(i + 1));
            }
            cost += points.get(points.size()-1).calculateDistanceToPoint(points.get(0));
        }
        return cost;
    }

    private static ArrayList<Point> swapTwoRandomEdges(List<Point> points) {
        int range1 = 0, range2 = 0, min, max;
        int noOfPoints = points.size();
        ArrayList<Point> unchangedBeginning;
        ArrayList<Point> toBeReversed;
        ArrayList<Point> unchangedEnd;
        Random random = new Random();

        while (Math.abs(range1 - range2) < 2) {
            range1 = random.nextInt(noOfPoints);
            range2 = random.nextInt(noOfPoints);
        }
        min = Math.min(range1, range2);
        max = Math.max(range1, range2);

        unchangedBeginning = new ArrayList<>(points.subList(0, min));
        toBeReversed = new ArrayList<>(points.subList(min, max));
        Collections.reverse(toBeReversed);
        unchangedEnd = new ArrayList<>(points.subList(max, noOfPoints));

        ArrayList<Point> swapped = new ArrayList<>();
        swapped.addAll(unchangedBeginning);
        swapped.addAll(toBeReversed);
        swapped.addAll(unchangedEnd);
        return swapped;
    }


    protected double getTravelCostDifference() {
        return getTotalTourCost(newSolution) - getTotalTourCost(currentSolution);
    }


    public List<Point> solve() {
        currentSolution = tspUseCase.getInitialPoints();
        while (canBeImproved()) {
            improve();
            useImprovement();
        }
        return currentSolution;
    }

    protected void improve() {
        newSolution = swapTwoRandomEdges(currentSolution);
        if (isABetterCandidate()) {
            iterationsWithoutImprovement = 0;
            currentSolution = newSolution;
            notifyOnNewSolution();
        } else {
            iterationsWithoutImprovement++;
        }
    }


    private void notifyOnNewSolution() {
        for (TSPSolverListener listener : listeners) {
            listener.onNewSolution(currentSolution);
        }
    }

    protected int getIterationsWithoutImprovement() {
        return iterationsWithoutImprovement;
    }


    private boolean isABetterCandidate() {
        return this.tspUseCase.isABetterCandidate(getTravelCostDifference());
    }


    private boolean canBeImproved() {
        return this.tspUseCase.solutionCanBeImproved(getIterationsWithoutImprovement());
    }


    private void useImprovement() {
        this.tspUseCase.useImprovement(this.currentSolution);
    }


    public void addListener(TSPSolverListener listener) {
        listeners.add(listener);
    }
}

