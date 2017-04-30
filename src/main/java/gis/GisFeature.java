package gis;

import java.util.List;

/**
 * Class to describe a GIS feature
 * Created by arka on 17/4/17.
 */
public class GisFeature {
    private String source;
    private String timestamp;
    private String destination;
    private String createLat;
    private String createLon;

    private String geometryType;
    private List<Coordinate> coordinates;
    private Properties properties;

    public GisFeature(String source, String timestamp, String destination, String createLat, String createLon,
                      String geometryType, List<Coordinate> coordinates, Properties properties) {
        this.source = source;
        this.timestamp = timestamp;
        this.destination = destination;
        this.createLat = createLat;
        this.createLon = createLon;

        this.geometryType = geometryType;
        this.coordinates = coordinates;
        this.properties = properties;
    }

    public GisFeature(String source, String timestamp, String destination, String createLat, String createLon) {
        this.source = source;
        this.timestamp = timestamp;
        this.destination = destination;
        this.createLat = createLat;
        this.createLon = createLon;
    }
}
