package pl.radoslawkarwacki.tsp.config;

public class AppConfig {
    private final boolean annealing;
    private final int numberOfCities;
    private final int numberOfTrials;
    private final long randomSeed;
    private final int initialTemperature;
    private final double minimalTemperature;
    private final double coolingCoefficient;
    private final boolean drawChart;
    private final int delayMs;
    private final int framesInBetween;
    private final int rangeX;
    private final int rangeY;
    private final int windowSizeX;
    private final int windowSizeY;

    public AppConfig(boolean annealing,
                     int numberOfCities,
                     int numberOfTrials,
                     long randomSeed,
                     int initialTemperature,
                     double minimalTemperature,
                     double coolingCoefficient,
                     boolean drawChart,
                     int delayMs,
                     int framesInBetween,
                     int rangeX,
                     int rangeY,
                     int windowSizeX,
                     int windowSizeY) {
        this.annealing = annealing;
        this.numberOfCities = numberOfCities;
        this.numberOfTrials = numberOfTrials;
        this.randomSeed = randomSeed;
        this.initialTemperature = initialTemperature;
        this.minimalTemperature = minimalTemperature;
        this.coolingCoefficient = coolingCoefficient;
        this.drawChart = drawChart;
        this.delayMs = delayMs;
        this.framesInBetween = framesInBetween;
        this.rangeX = rangeX;
        this.rangeY = rangeY;
        this.windowSizeX = windowSizeX;
        this.windowSizeY = windowSizeY;
    }

    public static AppConfig defaults() {
        return new AppConfig(
                true,       // annealing
                350,        // numberOfCities
                50000,      // numberOfTrials
                124531L,    // randomSeed
                500,        // initialTemperature
                0.00001,    // minimalTemperature
                0.99995,    // coolingCoefficient
                true,       // drawChart
                1,          // delayMs
                1,          // framesInBetween
                1600,       // rangeX
                850,        // rangeY
                1600,       // windowSizeX
                900         // windowSizeY
        );
    }

    public boolean isAnnealing() { return annealing; }
    public int getNumberOfCities() { return numberOfCities; }
    public int getNumberOfTrials() { return numberOfTrials; }
    public long getRandomSeed() { return randomSeed; }
    public int getInitialTemperature() { return initialTemperature; }
    public double getMinimalTemperature() { return minimalTemperature; }
    public double getCoolingCoefficient() { return coolingCoefficient; }
    public boolean isDrawChart() { return drawChart; }
    public int getDelayMs() { return delayMs; }
    public int getFramesInBetween() { return framesInBetween; }
    public int getRangeX() { return rangeX; }
    public int getRangeY() { return rangeY; }
    public int getWindowSizeX() { return windowSizeX; }
    public int getWindowSizeY() { return windowSizeY; }
}
