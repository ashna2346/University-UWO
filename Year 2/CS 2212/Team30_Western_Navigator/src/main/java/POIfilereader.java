/**
 * @author Team30
 * @version 1.0
 * 
 * Class to read POI object data from JSon file
 * JSon should contain a list of PointOfInterest objects
 * Each PointOfInterest object represented as a JSon object
 */

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class POIfilereader {
    /**
     * Read POI object data from JSon file
     * 
     * @param filePath path for JSon file for current user
     * @return List of POI objects parsed from JSon file
     */
    public static List<PointOfInterest> readPOIsFromFile(String filePath) {
        ObjectMapper objectMapper = new ObjectMapper();
        List<PointOfInterest> pointsOfInterest = new ArrayList<>();

        try {
            File file = new File(filePath);
            JsonNode rootNode = objectMapper.readTree(file);
            JsonNode pointsOfInterestNode = rootNode.get("points_of_interest");

            if (pointsOfInterestNode.isArray()) {
                for (JsonNode poiNode : pointsOfInterestNode) {
                    PointOfInterest poi = objectMapper.treeToValue(poiNode, PointOfInterest.class);
                    pointsOfInterest.add(poi);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return pointsOfInterest;
    }
}