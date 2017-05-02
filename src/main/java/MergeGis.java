import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import constants.Paths;
import gis.GISCoordinate;
import gis.GisFeature;
import gis.Properties;
import org.opengis.referencing.FactoryException;
import org.opengis.referencing.operation.TransformException;
import util.GisFilesFinder;
import util.GisZipReader;
import util.Util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 * Main class to merge Gis conflicting/redundant data
 * Created by arka on 17/4/17.
 */
public class MergeGis {

    public static void main(String args[]) throws IOException, FactoryException, TransformException {
        List<File> gisFiles = new GisFilesFinder(Paths.WORKING_DIR_TEMP).getGisFiles();
        List<GisFeature> gisFeatures = getAllGisFeature(gisFiles);
    }

    private static List<GisFeature> getAllGisFeature(List<File> gisFiles) throws IOException, FactoryException, TransformException {
        List<GisFeature> gisFeatures = new ArrayList<GisFeature>();

        for(File file:gisFiles) {
            String fileName = file.getName().substring(0, file.getName().indexOf('.'));

            String source = getSourceFromFileName(fileName);
            String timestamp = getTimestampFromFileName(fileName);
            String destination = getDestinationFromFileName(fileName);
            String createLat = getLatFromFileName(fileName);
            String createLon = getLonFromFileName(fileName);

            JsonObject geoJson = GisZipReader.getGeoJsonFromZip(file);
            JsonArray features = geoJson.getAsJsonArray("features");

            for(JsonElement feature:features) {

                if(!feature.getAsJsonObject().get("type").getAsString().equals("Feature")) {
                    continue;
                }

                // get the geometry
                JsonObject geometry = feature.getAsJsonObject().get("geometry").getAsJsonObject();
                String geometryType = geometry.get("type").getAsString();

                // get the coordinates
                String coordinates = geometry.get("coordinates").toString();
                String[] c = coordinates.split(",");
                for(int i = 0; i < c.length; ++i) {
                    c[i] = Util.trimString(c[i], '[');
                    c[i] = Util.trimString(c[i], ']');
                }

                List<GISCoordinate> list = new ArrayList<GISCoordinate>();

                for(int i=0; i < c.length; i += 2) {
                    GISCoordinate coordinate = new GISCoordinate(coordinates);
                    coordinate.setCoordinateEPSG3857(Double.parseDouble(c[i]), Double.parseDouble(c[i + 1]));
//                    coordinate.convertEPSG3857toEPSG4326();
                    list.add(coordinate);
                }

                // get the properties
                JsonObject property = feature.getAsJsonObject().get("properties").getAsJsonObject();
                int fid = property.get("FID").getAsInt();
                String text = property.get("FID").getAsString();
                Properties properties = new Properties(fid, text);

                GisFeature gisFeature = new GisFeature(source, timestamp, destination, createLat, createLon,
                        geometryType, list, properties);
                gisFeatures.add(gisFeature);
            }
        }

        return gisFeatures;
    }

    private static String getLonFromFileName(String fileName) {
        try {
            return fileName.split("_")[6];
        } catch (Exception e) {
            System.out.println("Longitude not in Filename " + fileName);
            return null;
        }
    }

    private static String getLatFromFileName(String fileName) {
        try {
            return fileName.split("_")[5];
        } catch (Exception e) {
            System.out.println("Latitude not in Filename " + fileName);
            return null;
        }
    }

    private static String getDestinationFromFileName(String fileName) {
        try {
            return fileName.split("_")[4];
        } catch (Exception e) {
            System.out.println("Destination not in Filename " + fileName);
            return null;
        }
    }

    private static String getTimestampFromFileName(String fileName) {
        try {
            return fileName.split("_")[7];
        } catch (Exception e) {
            System.out.println("Timestamp not in Filename " + fileName);
            return null;
        }
    }

    private static String getSourceFromFileName(String fileName) {
        try {
            return fileName.split("_")[3];
        } catch (Exception e) {
            System.out.println("Source not in Filename " + fileName);
            return null;
        }
    }
}
