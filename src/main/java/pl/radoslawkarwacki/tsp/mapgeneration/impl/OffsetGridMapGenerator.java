package pl.radoslawkarwacki.tsp.mapgeneration.impl;

import pl.radoslawkarwacki.tsp.mapgeneration.MapGenerationConfiguration;
import pl.radoslawkarwacki.tsp.mapgeneration.MapGenerator;
import pl.radoslawkarwacki.tsp.model.Point;

import java.util.ArrayList;
import java.util.List;

public class OffsetGridMapGenerator extends BaseMapGenerator implements MapGenerator {

    public OffsetGridMapGenerator(MapGenerationConfiguration configuration) {
        super(configuration);
    }

    @Override
    public List<Point> generateMap() {
        int margin = 40;
        double maxRandomShift = 80;
        List<Point> points = new ArrayList<>();
        // Calculate the side length of each square
        // Calculate the side length of each square
        double sideLength = Math.min(horizontalDimension, verticalDimension) / Math.sqrt(numberOfPoints);

        // Calculate the number of rows and columns
        int rows = (int) (verticalDimension / sideLength);
        int cols = (int) (horizontalDimension / sideLength);

        // Calculate horizontal and vertical spacing
        double dx = horizontalDimension / (double) cols;
        double dy = verticalDimension / (double) rows;

        // Place the points on the canvas
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                double xOffset = (random.nextDouble() * maxRandomShift) - maxRandomShift/2;
                double yOffset = random.nextDouble() * maxRandomShift - maxRandomShift/2;
                double xCoordinate = (j * dx) + margin + xOffset;
                double yCoordinate = (i * dy) + margin + yOffset;
                points.add(new Point(xCoordinate, yCoordinate));
            }
        }
        return points;
    }
}
