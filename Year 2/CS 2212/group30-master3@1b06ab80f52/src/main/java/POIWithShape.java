/**
 * @author Team30
 * @version 1.0
 * 
 * Assigns POI object with shape to display on map
 */

import java.awt.*;

public class POIWithShape {
    private PointOfInterest poi;
    private Shape shape;
    
    /**
     * Constructor
     * 
     * @param poi POI object
     * @param shape shape to assign to POI
     */
    public POIWithShape(PointOfInterest poi, Shape shape) {
        this.poi = poi;
        this.shape = shape;
    }
    
    /**
     * Get POI object
     * 
     * @return POI object
     */
    public PointOfInterest getPOI() {
        return poi;
    }
    
    /**
     * Get shape assigned to POI object
     * 
     * @return shape assigned to POI object
     */
    public Shape getShape() {
        return shape;
    }
}
