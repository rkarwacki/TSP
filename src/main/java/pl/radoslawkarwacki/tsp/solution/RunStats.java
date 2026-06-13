package pl.radoslawkarwacki.tsp.solution;

public class RunStats {
    private final String algorithm;
    private final double finalTemperature;
    private final double minimalTemperature;
    private final int maxTrials;
    private final int stepsLowered;
    private final int totalFrames;
    private final String stopReason;

    public RunStats(String algorithm,
                    double finalTemperature,
                    double minimalTemperature,
                    int maxTrials,
                    int stepsLowered,
                    int totalFrames,
                    String stopReason) {
        this.algorithm = algorithm;
        this.finalTemperature = finalTemperature;
        this.minimalTemperature = minimalTemperature;
        this.maxTrials = maxTrials;
        this.stepsLowered = stepsLowered;
        this.totalFrames = totalFrames;
        this.stopReason = stopReason;
    }

    public String getAlgorithm() { return algorithm; }
    public double getFinalTemperature() { return finalTemperature; }
    public double getMinimalTemperature() { return minimalTemperature; }
    public int getMaxTrials() { return maxTrials; }
    public int getStepsLowered() { return stepsLowered; }
    public int getTotalFrames() { return totalFrames; }
    public String getStopReason() { return stopReason; }
}
