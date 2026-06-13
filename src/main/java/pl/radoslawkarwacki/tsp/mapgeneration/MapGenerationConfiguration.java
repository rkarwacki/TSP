package pl.radoslawkarwacki.tsp.mapgeneration;

import lombok.Value;

@Value
public class MapGenerationConfiguration {
    private int numberOfPoints;
    private long randomSeed;
    private int horizontalDimension;
    private int verticalDimension;
}
