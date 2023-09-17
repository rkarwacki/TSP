package pl.radoslawkarwacki.tsp.mapgeneration.impl;

import pl.radoslawkarwacki.tsp.mapgeneration.MapGenerationConfiguration;

import java.util.Random;

public class BaseMapGenerator {
    protected final int numberOfPoints;
    protected final Random random;
    protected final int horizontalDimension;
    protected final int verticalDimension;

    public BaseMapGenerator(MapGenerationConfiguration configuration) {
        this.numberOfPoints = configuration.getNumberOfPoints();
        this.random = new Random(configuration.getRandomSeed());
        this.horizontalDimension = configuration.getHorizontalDimension();
        this.verticalDimension = configuration.getVerticalDimension();
    }
}
