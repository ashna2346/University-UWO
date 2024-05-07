/**
 * @author Team30
 * @version 1.0
 * 
 * Marks POI on map
 */

import java.awt.Color;
import java.awt.Graphics;

public class POIMarker {
    private int x;
    private int y;
    private int radius;
    private Color color;

    /**
     * Constructor for POI marker
     * 
     * @param x X-coordinate of POI
     * @param y Y-coordinate of POI
     * @param radius radius of marker
     * @param color color of marker
     */
    public POIMarker(int x, int y, int radius, Color color) {
        this.x = x;
        this.y = y;
        this.radius = radius;
        this.color = color;
    }
    
    /**
     * Draw POI marker on map
     * 
     * @param g graphics on which to draw marker
     */
    public void draw(Graphics g) {
        g.setColor(color);
        g.fillOval(x - radius, y - radius, 2 * radius, 2 * radius);
    }
    
    /**
     * Check if point clicked by user is in the POI marker
     * 
     * @param x X-coordinate of point clicked by cursor
     * @param y Y-coordinate of point clicked by cursor
     * @return True if user clicked within POI marker
     */
    public boolean contains(int x, int y) {
        int dx = this.x - x;
        int dy = this.y - y;
        return dx * dx + dy * dy <= radius * radius;
    }
}
