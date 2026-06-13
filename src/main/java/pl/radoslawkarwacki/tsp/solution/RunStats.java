package pl.radoslawkarwacki.tsp.solution;

public class RunStats {
    private final String algorithm;
    private final double finalTemperature;
    private final double minimalTemperature;
    private final int maxTrials;
    private final int stepsLowered;
    private final int totalFrames;
    private final String stopReason;

    // Added initial conditions and final cost
    private final int numberOfCities;
    private final long randomSeed;
    private final int initialTemperature;
    private final double coolingCoefficient;
    private final int numberOfTrials;
    private final double finalCost;

    public RunStats(String algorithm,
                    double finalTemperature,
                    double minimalTemperature,
                    int maxTrials,
                    int stepsLowered,
                    int totalFrames,
                    String stopReason,
                    int numberOfCities,
                    long randomSeed,
                    int initialTemperature,
                    double coolingCoefficient,
                    int numberOfTrials,
                    double finalCost) {
        this.algorithm = algorithm;
        this.finalTemperature = finalTemperature;
        this.minimalTemperature = minimalTemperature;
        this.maxTrials = maxTrials;
        this.stepsLowered = stepsLowered;
        this.totalFrames = totalFrames;
        this.stopReason = stopReason;
        this.numberOfCities = numberOfCities;
        this.randomSeed = randomSeed;
        this.initialTemperature = initialTemperature;
        this.coolingCoefficient = coolingCoefficient;
        this.numberOfTrials = numberOfTrials;
        this.finalCost = finalCost;
    }

    public String getAlgorithm() { return algorithm; }
    public double getFinalTemperature() { return finalTemperature; }
    public double getMinimalTemperature() { return minimalTemperature; }
    public int getMaxTrials() { return maxTrials; }
    public int getStepsLowered() { return stepsLowered; }
    public int getTotalFrames() { return totalFrames; }
    public String getStopReason() { return stopReason; }

    public int getNumberOfCities() { return numberOfCities; }
    public long getRandomSeed() { return randomSeed; }
    public int getInitialTemperature() { return initialTemperature; }
    public double getCoolingCoefficient() { return coolingCoefficient; }
    public int getNumberOfTrials() { return numberOfTrials; }
    public double getFinalCost() { return finalCost; }
}
