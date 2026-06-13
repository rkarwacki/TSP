package pl.radoslawkarwacki.tsp.cli;

import com.fasterxml.jackson.databind.ObjectMapper;
import pl.radoslawkarwacki.tsp.config.AppConfig;

import java.io.File;

public class ConfigLoader {

    public AppConfig load(AppConfig defaults, String jsonPath, CliArgs overrides) throws Exception {
        SolverConfigDto dto = null;
        if (jsonPath != null && !jsonPath.isBlank()) {
            ObjectMapper mapper = new ObjectMapper();
            dto = mapper.readValue(new File(jsonPath), SolverConfigDto.class);
        }
        // Start with defaults
        boolean annealing = defaults.isAnnealing();
        int numberOfCities = defaults.getNumberOfCities();
        int numberOfTrials = defaults.getNumberOfTrials();
        long randomSeed = defaults.getRandomSeed();
        int initialTemperature = defaults.getInitialTemperature();
        double minimalTemperature = defaults.getMinimalTemperature();
        double coolingCoefficient = defaults.getCoolingCoefficient();
        int rangeX = defaults.getRangeX();
        int rangeY = defaults.getRangeY();

        // Apply JSON
        if (dto != null) {
            if (dto.getAnnealing() != null) annealing = dto.getAnnealing();
            if (dto.getNumberOfCities() != null) numberOfCities = dto.getNumberOfCities();
            if (dto.getNumberOfTrials() != null) numberOfTrials = dto.getNumberOfTrials();
            if (dto.getRandomSeed() != null) randomSeed = dto.getRandomSeed();
            if (dto.getInitialTemperature() != null) initialTemperature = dto.getInitialTemperature();
            if (dto.getMinimalTemperature() != null) minimalTemperature = dto.getMinimalTemperature();
            if (dto.getCoolingCoefficient() != null) coolingCoefficient = dto.getCoolingCoefficient();
            if (dto.getRangeX() != null) rangeX = dto.getRangeX();
            if (dto.getRangeY() != null) rangeY = dto.getRangeY();
        }

        // Apply CLI overrides
        if (overrides != null) {
            if (overrides.getAnnealing() != null) annealing = overrides.getAnnealing();
            if (overrides.getNumberOfCities() != null) numberOfCities = overrides.getNumberOfCities();
            if (overrides.getNumberOfTrials() != null) numberOfTrials = overrides.getNumberOfTrials();
            if (overrides.getRandomSeed() != null) randomSeed = overrides.getRandomSeed();
            if (overrides.getInitialTemperature() != null) initialTemperature = overrides.getInitialTemperature();
            if (overrides.getMinimalTemperature() != null) minimalTemperature = overrides.getMinimalTemperature();
            if (overrides.getCoolingCoefficient() != null) coolingCoefficient = overrides.getCoolingCoefficient();
            if (overrides.getRangeX() != null) rangeX = overrides.getRangeX();
            if (overrides.getRangeY() != null) rangeY = overrides.getRangeY();
        }

        // Keep UI-only fields from defaults
        return new AppConfig(
                annealing,
                numberOfCities,
                numberOfTrials,
                randomSeed,
                initialTemperature,
                minimalTemperature,
                coolingCoefficient,
                defaults.isDrawChart(),
                defaults.getDelayMs(),
                defaults.getFramesInBetween(),
                rangeX,
                rangeY,
                defaults.getWindowSizeX(),
                defaults.getWindowSizeY()
        );
    }
}
