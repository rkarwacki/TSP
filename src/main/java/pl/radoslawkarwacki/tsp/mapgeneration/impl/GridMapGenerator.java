package pl.radoslawkarwacki.tsp.mapgeneration.impl;

import pl.radoslawkarwacki.tsp.mapgeneration.MapGenerationConfiguration;
import pl.radoslawkarwacki.tsp.mapgeneration.MapGenerator;
import pl.radoslawkarwacki.tsp.model.Point;

import java.util.ArrayList;
import java.util.List;

public class GridMapGenerator extends BaseMapGenerator implements MapGenerator {

    public GridMapGenerator(MapGenerationConfiguration configuration) {
        super(configuration);
    }

    @Override
    public List<Point> generateMap() {
        int margin = 40;
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
                int xCoordinate = (int) (j * dx) + margin;
                int yCoordinate = (int) (i * dy) + margin;
                points.add(new Point(xCoordinate, yCoordinate));
            }
        }
        return points;
    }
}
