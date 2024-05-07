/**
 * @author Team30
 * @version 1.0
 * 
 * Creates PointOfInterest object 
 */

import java.util.Objects;

public class PointOfInterest {
    private int id;
    private String name;
    private String room_number;
    private String building;
    private int x_coordinate;
    private int y_coordinate;
    private String description;
    private String type;
    private int floor;
    private boolean favorite;
    private boolean userCreated;
    
    /**
     * default constructor
     */
    public PointOfInterest() {
        this.id = this.x_coordinate = this.y_coordinate = this.floor = 0;
        this.name = this.room_number = this.building =  this.description = this.type = null;
        this.favorite = this.userCreated = false;
    }
    
    /**
     * constructor for PointOfInterest object
     * 
     * @param id POI ID
     * @param name name of POI
     * @param room_number room number if POI is in a building
     * @param building name of building POI is located in
     * @param x_coordinate 
     * @param y_coordinate
     * @param description
     * @param type
     * @param floor
     * @param favorite whether POI is marked favorite
     * @param userCreated whether POI was created by a user or admin
     */
    public PointOfInterest(int id, String name, String room_number, String  building, int x_coordinate, int y_coordinate, String description, String type, int floor, boolean favorite, boolean userCreated)
    {
        this.id= id;
        this.name = name;
        this.room_number = room_number;
        this.building = building;
        this.x_coordinate = x_coordinate;
        this.y_coordinate = y_coordinate;
        this.description = description;
        this.type = type;
        this.floor = floor;
        this.favorite = favorite;
        this.userCreated = userCreated;
    }
    
    /**
     * Gets ID of POI object
     * 
     * @return POI ID 
     */
    public int getId() {
        return id;
    }
    
    /**
     * Set ID of POI object
     * @param id 
     */
    public void setId(int id) {
        this.id = id;
    }
    
    /**
     * Gets name of POI object
     * 
     * @return POI name 
     */
    public String getName() {
        return name;
    }

    /**
     * Set name of POI
     * 
     * @param name 
     */
    public void setName(String name) {
        this.name = name;
    }
    
    /**
     * Gets room number of POI object
     * 
     * @return POI room number 
     */
    public String getRoom_number() {
        return room_number;
    }

    /**
     * Set room number of POI object
     * 
     * @param room_number 
     */
    public void setRoom_number(String room_number) {
        this.room_number = room_number;
    }

    /**
     * Gets building of POI object
     * 
     * @return Building name 
     */
    public String getBuilding() {
        return building;
    }

    /**
     * Set building of POI object
     * 
     * @param building 
     */
    public void setBuilding(String building) {
        this.building = building;
    }

    /**
     * Gets X-coordinate of POI object
     * 
     * @return POI X-coordinate
     */
    public int getX_coordinate() {
        return x_coordinate;
    }

    /**
     * Set X-coordinate of POI object
     * 
     * @param x_coordinate 
     */
    public void setX_coordinate(int x_coordinate) {
        this.x_coordinate = x_coordinate;
    }

    /**
     * Gets Y-coordinate of POI object
     * 
     * @return POI y_coordinate 
     */
    public int getY_coordinate() {
        return y_coordinate;
    }

    /**
     * Set Y-coordinate of POI object
     * 
     * @param y_coordinate
     */
    public void setY_coordinate(int y_coordinate) {
        this.y_coordinate = y_coordinate;
    }

    /**
     * Gets description of POI object
     * 
     * @return POI description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Set description of POI object
     * 
     * @param description 
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets type of POI object
     * 
     * @return POI type 
     */
    public String getType() {
        return type;
    }

    /**
     * Set type of POI object
     * 
     * @param type 
     */
    public void setType(String type) {
        this.type = type;
    }
    
    /**
     * Gets floor of POI object
     * 
     * @return POI floor 
     */
    public int getFloor() {
        return floor;
    }
    
    /**
     * Set floor of POI object
     * 
     * @param floor 
     */
    public void setFloor(int floor) {
        this.floor = floor;
    }
    
    /**
     * Gets favorite status of POI object
     * 
     * @return True if POI is favorite
     */
    public boolean getFavorite(){
        return favorite;
    }
    
    /**
     * Set favorite status of POI object
     * 
     * @param favorite 
     */
    public void setFavorite(boolean favorite){
        this.favorite = favorite;
    }
    
    /**
     * Gets creator of POI object
     * 
     * @return True if user created 
     */
    public boolean getUserCreated() {
         return userCreated;
    }
    
    /**
     * Set creator of POI object
     * 
     * @param userCreated
     */
    public void setUserCreated(boolean userCreated){
        this.userCreated = userCreated;
    }
    
    /**
     * Prints all information of POI object
     * 
     * @return String with all information of POI object
     */
    @Override
    public String toString() {
        return "PointOfInterest{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", room_number='" + room_number + '\'' +
                ", building='" + building + '\'' +
                ", floor=" + floor +
                ", x_coordinate=" + x_coordinate +
                ", y_coordinate=" + y_coordinate +
                ", description='" + description + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
    
    /**
     * Compares objects to check if equal
     * 
     * @param obj
     * @return True if objects are same
     */
    public boolean equals(Object obj)
    {
        if (this == obj) {
            return true;
        }
        
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        
        PointOfInterest other = (PointOfInterest) obj;
        return id == other.id &&
                floor == other.floor &&
                x_coordinate == other.x_coordinate &&
                y_coordinate == other.y_coordinate &&
                Objects.equals(name, other.name) &&
                Objects.equals(room_number, other.room_number) &&
                Objects.equals(building, other.building) &&
                Objects.equals(description, other.description) &&
                Objects.equals(type, other.type);
    }
    
}
