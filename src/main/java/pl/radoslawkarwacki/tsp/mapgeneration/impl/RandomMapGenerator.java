package pl.radoslawkarwacki.tsp.mapgeneration.impl;

import pl.radoslawkarwacki.tsp.mapgeneration.MapGenerationConfiguration;
import pl.radoslawkarwacki.tsp.mapgeneration.MapGenerator;
import pl.radoslawkarwacki.tsp.model.Point;

import java.util.ArrayList;
import java.util.List;

public class RandomMapGenerator extends BaseMapGenerator implements MapGenerator {

    public RandomMapGenerator(MapGenerationConfiguration configuration) {
        super(configuration);
    }

    @Override
    public List<Point> generateMap() {
        List<Point> points = new ArrayList<>();
        for (int i = 0; i < numberOfPoints; i++){
            points.add(new Point(random, horizontalDimension, verticalDimension));
        }
        return points;
    }
}
