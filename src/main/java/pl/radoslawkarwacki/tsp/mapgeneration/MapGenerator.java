package pl.radoslawkarwacki.tsp.mapgeneration;

import pl.radoslawkarwacki.tsp.model.Point;

import java.util.List;

public interface MapGenerator {
    List<Point> generateMap();
}
