package pl.radoslawkarwacki.tsp.solver.impl.annealing;

import pl.radoslawkarwacki.tsp.model.Point;
import pl.radoslawkarwacki.tsp.solver.TSPUseCase;

import java.util.List;


public class AnnealingSolver implements TSPUseCase {

    private double currentTemperature;
    private double minimalTemperature;
    private int maximumNumberOfTrials;
    private double coolingCoefficient;
    private List<Point> initialPoints;

    private ProgressListener progressListener;
    private int expectedTotalLowerings;
    private int stepsSoFar;
    private int trialsAtTemperature;
    private final int epochLength;


    public AnnealingSolver(List<Point> initialPoints, double initialTemperature, double minimalTemperature, int maximumNumberOfTrials, double coolingCoefficient) {
        this.initialPoints = initialPoints;
        this.currentTemperature = initialTemperature;
        this.minimalTemperature = minimalTemperature;
        this.maximumNumberOfTrials = maximumNumberOfTrials;
        this.coolingCoefficient = coolingCoefficient;
        this.expectedTotalLowerings = computeExpectedLowerings(initialTemperature, minimalTemperature, coolingCoefficient);
        this.stepsSoFar = 0;
        this.epochLength = Math.max(1, initialPoints != null ? initialPoints.size() * 100 : 35000);
        this.trialsAtTemperature = 0;
    }

    public interface ProgressListener {
        void onStart(int totalSteps);
        void onProgress(int currentStep, int totalSteps);
    }

    public void setProgressListener(ProgressListener listener) {
        this.progressListener = listener;
        if (listener != null) {
            listener.onStart(expectedTotalLowerings);
        }
    }

    private static int computeExpectedLowerings(double initial, double minimal, double coeff) {
        if (coeff <= 0 || coeff >= 1 || minimal <= 0 || initial <= minimal) {
            return 1;
        }
        double k = Math.log(minimal / initial) / Math.log(coeff);
        int steps = (int) Math.ceil(k);
        return Math.max(steps, 1);
    }

    @Override
    public void useImprovement(List<Point> points) {
        // Cooling is handled per-epoch inside isABetterCandidate()
    }


    @Override
    public boolean solutionCanBeImproved(int iterationsWithoutImprovement) {
        return currentTemperature > minimalTemperature && iterationsWithoutImprovement < maximumNumberOfTrials;
    }


    private void lowerTemperature() {
        currentTemperature = coolingCoefficient * currentTemperature;
    }

    @Override
    public boolean isABetterCandidate(double travelCostDifference) {
        boolean accept = travelCostDifference < 0
                || (travelCostDifference > 0 && Math.exp(-travelCostDifference / currentTemperature) > Math.random());

        // Count every trial at the current temperature
        trialsAtTemperature++;

        // Cool down after a full epoch of trials
        if (trialsAtTemperature >= epochLength) {
            lowerTemperature();
            trialsAtTemperature = 0;
            stepsSoFar++;
            if (progressListener != null) {
                progressListener.onProgress(stepsSoFar, expectedTotalLowerings);
            }
        }

        return accept;
    }

    @Override
    public List<Point> getInitialPoints() {
        return this.initialPoints;
    }

    public double getCurrentTemperature() {
        return currentTemperature;
    }

    public double getMinimalTemperature() {
        return minimalTemperature;
    }

    public int getMaximumNumberOfTrials() {
        return maximumNumberOfTrials;
    }

    public int getStepsSoFar() {
        return stepsSoFar;
    }
}
