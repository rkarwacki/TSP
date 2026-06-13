package pl.radoslawkarwacki.tsp.mapgeneration.impl;

import lombok.Value;
import pl.radoslawkarwacki.tsp.mapgeneration.MapGenerationConfiguration;
import pl.radoslawkarwacki.tsp.mapgeneration.MapGenerator;
import pl.radoslawkarwacki.tsp.model.Point;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MultipleClustersMapGenerator extends BaseMapGenerator implements MapGenerator {

    public MultipleClustersMapGenerator(MapGenerationConfiguration configuration) {
        super(configuration);
    }

    @Override
    public List<Point> generateMap() {
        List<Point> points = new ArrayList<>();

        points.addAll(new Cluster(200, 200, 50, 10).clusterMap());
        points.addAll(new Cluster(350, 600, 100, 25).clusterMap());
        points.addAll(new Cluster(1000, 500, 200, 60).clusterMap());
        points.addAll(new Cluster(1400, 200, 25, 7).clusterMap());
        return points;
    }

    class Cluster {
        private int centerX;
        private int centerY;
        private int radius;
        private final int numberOfPoints1;

        public Cluster(int centerX, int centerY, int radius, int numberOfPoints) {
            this.centerX = centerX;
            this.centerY = centerY;
            this.radius = radius;
            numberOfPoints1 = numberOfPoints;
        }

        List<Point> clusterMap() {
            List<Point> points = new ArrayList<>();
            for (int i = 0; i < numberOfPoints1; i++) {
                double angle = 2 * Math.PI * random.nextDouble();
                double distance = radius * Math.sqrt(random.nextDouble());
                double x = centerX + distance * Math.cos(angle);
                double y = centerY + distance * Math.sin(angle);
                points.add(new Point(x, y));
            }
            return points;
        }
    }
}
