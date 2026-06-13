package pl.radoslawkarwacki.tsp.cli;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SolverConfigDto {
    private Boolean annealing;
    private Integer numberOfCities;
    private Integer numberOfTrials;
    private Long randomSeed;
    private Integer initialTemperature;
    private Double minimalTemperature;
    private Double coolingCoefficient;
    private Integer rangeX;
    private Integer rangeY;

    public Boolean getAnnealing() { return annealing; }
    public void setAnnealing(Boolean annealing) { this.annealing = annealing; }
    public Integer getNumberOfCities() { return numberOfCities; }
    public void setNumberOfCities(Integer numberOfCities) { this.numberOfCities = numberOfCities; }
    public Integer getNumberOfTrials() { return numberOfTrials; }
    public void setNumberOfTrials(Integer numberOfTrials) { this.numberOfTrials = numberOfTrials; }
    public Long getRandomSeed() { return randomSeed; }
    public void setRandomSeed(Long randomSeed) { this.randomSeed = randomSeed; }
    public Integer getInitialTemperature() { return initialTemperature; }
    public void setInitialTemperature(Integer initialTemperature) { this.initialTemperature = initialTemperature; }
    public Double getMinimalTemperature() { return minimalTemperature; }
    public void setMinimalTemperature(Double minimalTemperature) { this.minimalTemperature = minimalTemperature; }
    public Double getCoolingCoefficient() { return coolingCoefficient; }
    public void setCoolingCoefficient(Double coolingCoefficient) { this.coolingCoefficient = coolingCoefficient; }
    public Integer getRangeX() { return rangeX; }
    public void setRangeX(Integer rangeX) { this.rangeX = rangeX; }
    public Integer getRangeY() { return rangeY; }
    public void setRangeY(Integer rangeY) { this.rangeY = rangeY; }
}
