package pl.radoslawkarwacki.tsp.solver;

import pl.radoslawkarwacki.tsp.model.Point;

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
        return TSPUtil.getTotalTravelCost(newSolution) - TSPUtil.getTotalTravelCost(currentSolution);
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
        return tspUseCase.isABetterCandidate(getTravelCostDifference());
    }


    private boolean canBeImproved() {
        return tspUseCase.solutionCanBeImproved(getIterationsWithoutImprovement());
    }


    private void useImprovement() {
        tspUseCase.useImprovement(currentSolution);
    }


    public void addListener(TSPSolverListener listener) {
        listeners.add(listener);
    }
}

