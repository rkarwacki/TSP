package pl.radoslawkarwacki.tsp.cli;

import java.util.HashMap;
import java.util.Map;

public class CliArgs {
    private String configPath;
    private String outputPath;

    private Boolean annealing;
    private Integer numberOfCities;
    private Integer numberOfTrials;
    private Long randomSeed;
    private Integer initialTemperature;
    private Double minimalTemperature;
    private Double coolingCoefficient;
    private Integer rangeX;
    private Integer rangeY;

    public static CliArgs parse(String[] args) {
        CliArgs parsed = new CliArgs();
        if (args == null) return parsed;

        Map<String, String> expectsValue = new HashMap<>();
        expectsValue.put("--config", "path");
        expectsValue.put("--output", "path");
        expectsValue.put("--annealing", "bool");
        expectsValue.put("--cities", "int");
        expectsValue.put("--trials", "int");
        expectsValue.put("--seed", "long");
        expectsValue.put("--initial-temperature", "int");
        expectsValue.put("--minimal-temperature", "double");
        expectsValue.put("--cooling-coefficient", "double");
        expectsValue.put("--range-x", "int");
        expectsValue.put("--range-y", "int");

        for (int i = 0; i < args.length; i++) {
            String flag = args[i];
            if (!expectsValue.containsKey(flag)) {
                throw new IllegalArgumentException("Unknown flag: " + flag);
            }
            if (i + 1 >= args.length) {
                throw new IllegalArgumentException("Missing value for flag: " + flag);
            }
            String value = args[++i];
            switch (flag) {
                case "--config" -> parsed.configPath = value;
                case "--output" -> parsed.outputPath = value;
                case "--annealing" -> parsed.annealing = Boolean.parseBoolean(value);
                case "--cities" -> parsed.numberOfCities = Integer.parseInt(value);
                case "--trials" -> parsed.numberOfTrials = Integer.parseInt(value);
                case "--seed" -> parsed.randomSeed = Long.parseLong(value);
                case "--initial-temperature" -> parsed.initialTemperature = Integer.parseInt(value);
                case "--minimal-temperature" -> parsed.minimalTemperature = Double.parseDouble(value);
                case "--cooling-coefficient" -> parsed.coolingCoefficient = Double.parseDouble(value);
                case "--range-x" -> parsed.rangeX = Integer.parseInt(value);
                case "--range-y" -> parsed.rangeY = Integer.parseInt(value);
                default -> throw new IllegalArgumentException("Unknown flag: " + flag);
            }
        }
        return parsed;
    }

    public String getConfigPath() { return configPath; }
    public String getOutputPath() { return outputPath; }
    public Boolean getAnnealing() { return annealing; }
    public Integer getNumberOfCities() { return numberOfCities; }
    public Integer getNumberOfTrials() { return numberOfTrials; }
    public Long getRandomSeed() { return randomSeed; }
    public Integer getInitialTemperature() { return initialTemperature; }
    public Double getMinimalTemperature() { return minimalTemperature; }
    public Double getCoolingCoefficient() { return coolingCoefficient; }
    public Integer getRangeX() { return rangeX; }
    public Integer getRangeY() { return rangeY; }
}
