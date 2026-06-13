package pl.radoslawkarwacki.tsp.cli;

public class RunResultDto {
    private long durationMs;
    private String algorithm;
    private double finalCost;
    private String stopReason;
    private int totalFrames;
    private double finalTemperature;
    private int stepsLowered;
    private Inputs inputs;

    public static class Inputs {
        public boolean annealing;
        public int numberOfCities;
        public int numberOfTrials;
        public long randomSeed;
        public int initialTemperature;
        public double minimalTemperature;
        public double coolingCoefficient;
        public int rangeX;
        public int rangeY;

        public Inputs() {}
        public Inputs(boolean annealing, int numberOfCities, int numberOfTrials, long randomSeed,
                      int initialTemperature, double minimalTemperature, double coolingCoefficient,
                      int rangeX, int rangeY) {
            this.annealing = annealing;
            this.numberOfCities = numberOfCities;
            this.numberOfTrials = numberOfTrials;
            this.randomSeed = randomSeed;
            this.initialTemperature = initialTemperature;
            this.minimalTemperature = minimalTemperature;
            this.coolingCoefficient = coolingCoefficient;
            this.rangeX = rangeX;
            this.rangeY = rangeY;
        }
    }

    public RunResultDto() {}

    public RunResultDto(long durationMs, String algorithm, double finalCost, String stopReason,
                        int totalFrames, double finalTemperature, int stepsLowered, Inputs inputs) {
        this.durationMs = durationMs;
        this.algorithm = algorithm;
        this.finalCost = finalCost;
        this.stopReason = stopReason;
        this.totalFrames = totalFrames;
        this.finalTemperature = finalTemperature;
        this.stepsLowered = stepsLowered;
        this.inputs = inputs;
    }

    public long getDurationMs() { return durationMs; }
    public String getAlgorithm() { return algorithm; }
    public double getFinalCost() { return finalCost; }
    public String getStopReason() { return stopReason; }
    public int getTotalFrames() { return totalFrames; }
    public double getFinalTemperature() { return finalTemperature; }
    public int getStepsLowered() { return stepsLowered; }
    public Inputs getInputs() { return inputs; }

    public void setDurationMs(long durationMs) { this.durationMs = durationMs; }
    public void setAlgorithm(String algorithm) { this.algorithm = algorithm; }
    public void setFinalCost(double finalCost) { this.finalCost = finalCost; }
    public void setStopReason(String stopReason) { this.stopReason = stopReason; }
    public void setTotalFrames(int totalFrames) { this.totalFrames = totalFrames; }
    public void setFinalTemperature(double finalTemperature) { this.finalTemperature = finalTemperature; }
    public void setStepsLowered(int stepsLowered) { this.stepsLowered = stepsLowered; }
    public void setInputs(Inputs inputs) { this.inputs = inputs; }
}
