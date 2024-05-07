/**
 * @author Team30
 * @version 1.0
 * 
 * Class to search POI from list of POI objects
 */

import java.util.*;
import java.util.stream.Collectors;

public class SearchPOI {
    private List<PointOfInterest> pointsOfInterest;

    /**
     * Create list of POI objects
     * 
     * @param filePath 
     */
    public SearchPOI(String filePath) {
        pointsOfInterest = POIfilereader.readPOIsFromFile(filePath);
    }

    /**
     * Search for POI objects with name containing query string
     * 
     * @param query query to search for
     * @return List of POI objects with query in name
     */
    public List<PointOfInterest> searchForPOI(String query) {
        return pointsOfInterest.stream()
                .filter(poi -> poi.getName().toLowerCase().contains(query.toLowerCase()))
                .collect(Collectors.toList());
    }
    
    /**
     * Print list of POI objects
     * 
     * @return String representation of list of POI objects
     */
    public String toString() {
        return pointsOfInterest + " ";
    }
    
    /**
     * Get list of all POI objects
     * 
     * @return list of all POI objects
     */
    public List<PointOfInterest> getAllPOIs() {
        return pointsOfInterest;
    }
    
    /**
     * Get all POIs in given building and floor
     * 
     * @param building building to search POIs in
     * @param floor floor withing building to search POIs in
     * @return 
     */
    public List<PointOfInterest> getPOIsByBuildingAndFloor(String building, int floor) {
        List<PointOfInterest> filteredPOIs = new ArrayList<>();

        for (PointOfInterest poi : pointsOfInterest) {
            if (poi.getBuilding().equals(building) && poi.getFloor() == floor) {
                filteredPOIs.add(poi);
            }
        }
        
        return filteredPOIs;
    }
    
    /**
     * Search for POI whose name, type, and description match given search term
     * 
     * @param searchTerm value to look for in JSon file
     * @return List of POIs whose name, type, and description match search term
     */
    public List<PointOfInterest> searchPOIs(String searchTerm) {
        SearchPOI searchPOI = new SearchPOI("src\\poi.json");
        List<PointOfInterest> pois = searchPOI.getAllPOIs();
        List<PointOfInterest> searchResults = new ArrayList<>();
        for (PointOfInterest poi : pois) {
            if (poi.getName().toLowerCase().contains(searchTerm.toLowerCase()) ||
                poi.getType().toLowerCase().contains(searchTerm.toLowerCase()) || 
                poi.getDescription().contains(searchTerm.toLowerCase()) || 
                (poi.getRoom_number() != null && poi.getRoom_number().toLowerCase().contains(searchTerm.toLowerCase()))) {

                searchResults.add(poi);
            }
        }
        return searchResults;
    }
}
