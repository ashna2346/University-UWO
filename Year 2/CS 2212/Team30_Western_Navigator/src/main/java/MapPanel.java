/**
 * Creates a JPanel to display map on
 * 
 * @author Team30
 * @version 1.0
 */

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.*;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.JCheckBox;
import javax.swing.SwingConstants;
import javax.swing.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.node.ArrayNode;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.awt.event.*;
import com.fasterxml.jackson.databind.node.ObjectNode;

class MapPanel extends JPanel implements MouseWheelListener{
    private BufferedImage mapImage;
    public int offsetX = 0;
    public int offsetY = 0;
    private double scale = 0.75;
    private Point initialClick;
    private int selectedPOIX = -1;
    private int selectedPOIY = -1;
    private PointOfInterest selectedPOI;
    public boolean isPOISelected = false;
    private Shape selectedPOICircle;
    private List<PointOfInterest> visiblePOIs = new ArrayList<>();
    private List<PointOfInterest> accessiblePOIs = new ArrayList<>();
    private List<Shape> clickableCircles = new ArrayList<>();
    private List<POIWithShape> clickableCirclesWithPOIs = new ArrayList<>();
    private Map parent;
    private String mapBuilding;
    private int mapFloor = 0;
    private boolean showFavorites;
    private boolean userCreated;
    final private String code = "admin@123";
    private String poiFile;

    /**
     * A panel to display a map and Points of Interest (POIs) on the map
     * Allows users to drag the map to pan around, create new POIs, and select POIs to display information about them
     * Implements various mouse listeners to handle user input
     * 
     * @param parent parent component to contain map panel
     */
    public MapPanel(Map parent) {
        MapDisplay mapDisplay = new MapDisplay();
        setPOIjson(mapDisplay.getPOIFile());
        System.out.println("map panel !!! " + mapDisplay.getPOIFile());
        
        try {
            // load the map image from a file
            mapImage = ImageIO.read(new File("src\\images\\map_image.png"));
        } 
        catch (IOException e) {
            e.printStackTrace();
        }
        
        this.parent = parent;

        // add a mouse listener to track the initial mouse click
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                initialClick = e.getPoint();
            }
        });

        // add a mouse listener to track mouse dragging
        addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                // update the offset based on the distance the mouse has moved
                offsetX += e.getX() - initialClick.x;
                offsetY += e.getY() - initialClick.y;
                initialClick = e.getPoint(); // update the initial click position
                repaint(); // repaint the panel to show the updated map position
            }
        });

        //add a mouse listener to handle right-clicks for creating new POI panels
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON3) {      //check if right button is clicked
                    //calculate the mouse position relative to the scaled and offset map
                    Point mousePoint = e.getPoint();
                    int x = (int) (mousePoint.getX() / scale - offsetX);
                    int y = (int) (mousePoint.getY() / scale - offsetY);
                    System.out.println("Mouse clicked at (" + x + ", " + y + ")");
                    showNewPOIPanel(mousePoint);        //display a new POI panel at the mouse position
                } 
                else {
                    initialClick = e.getPoint();        //update the initial click position
                }
            }
        });

        //add a mouse listener to handle clicks on POI circles
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Point mousePoint = e.getPoint();
                for (POIWithShape poiWithShape : clickableCirclesWithPOIs) {        //iterate over clickable POI circles
                    Shape circle = poiWithShape.getShape();
                    if (circle.contains(mousePoint) && isPOISelected == false) {        //check if the mouse is inside a circle
                        PointOfInterest poi = poiWithShape.getPOI();        //get the POI associated with the clicked circle
                        centerPOI(poi);         //center the map on the POI
                        displayPOIPanel(poi);       //display a panel with POI information
                        System.out.println("Clicked inside the circle");
                        break;      //stop iterating over circles
                    }
                }
                initialClick = e.getPoint();        //update the initial click position
            }
        });

        //add a mouse listener to handle clicks on selected POI circles
        addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (isPOISelected && selectedPOICircle != null && selectedPOI != null) {
                    if (selectedPOICircle.contains(e.getPoint())) {         //check if the mouse is inside the selected circle
                        isPOISelected = false;      //deselect the POI
                        selectedPOI = null;
                        selectedPOICircle = null;

                        repaint();      //repaint the panel to remove the selection circle
                    }
                }
            }
        });

        //add a mouse wheel listener to the MapPanel
        addMouseWheelListener(this);
    }
    
    /**
     * Set JSon file for current user
     * 
     * @param file 
     */
    public void setPOIjson(String file) {
        this.poiFile = "src\\poi.json";
    }
    
    public double getScale() {
        return scale;
    }
    
    /**
     * Overrides paintComponent method of JPanel class to paint over map
     * 
     * @param g Graphics context in which to paint
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        Graphics2D g3d = (Graphics2D) g;

        // Fill the entire panel with a solid color
        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, getWidth(), getHeight());

        // Draw the map image
        g2d.scale(scale, scale);
        g2d.drawImage(mapImage, offsetX, offsetY, null);
        
        int circleDiameter = 125;
        g2d.setColor(new Color(255, 0, 0, 128));
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));

        clickableCirclesWithPOIs.clear();

        for (PointOfInterest poi : visiblePOIs) {
            int circleX = (int) (poi.getX_coordinate() + offsetX - circleDiameter / (2 * scale));
            int circleY = (int) (poi.getY_coordinate() + offsetY - circleDiameter / (2 * scale));
            if (showFavorites && poi.getFavorite()) {
                g2d.setColor(Color.YELLOW);
            } 
            else if (poi.getUserCreated()){
                g2d.setColor(Color.GREEN);
            }
            else if(showFavorites && poi.getFavorite() && userCreated && poi.getUserCreated()){
                g2d.setColor(Color.ORANGE);
            }
            else {
                g2d.setColor(Color.RED);
            }
            g2d.fillOval(circleX, circleY, (int) (circleDiameter / scale), (int) (circleDiameter / scale));
            
            int circleX2 = (int) ((poi.getX_coordinate() + offsetX) * scale - circleDiameter / 2);
            int circleY2 = (int) ((poi.getY_coordinate() + offsetY) * scale - circleDiameter / 2);
            int circleWidth = (int) (circleDiameter * scale);
            int circleHeight = (int) (circleDiameter * scale);
            
            Shape circle = new Ellipse2D.Double(circleX2, circleY2, circleWidth, circleWidth);
            clickableCirclesWithPOIs.add(new POIWithShape(poi, circle));
        }
        
        for (PointOfInterest poi : accessiblePOIs) {
            int circleX = (int) (poi.getX_coordinate() + offsetX - circleDiameter / (2 * scale));
            int circleY = (int) (poi.getY_coordinate() + offsetY - circleDiameter / (2 * scale));
            if (showFavorites && poi.getFavorite()) {
                g2d.setColor(Color.YELLOW);
            } 
            else if (userCreated && poi.getUserCreated()){
                g2d.setColor(Color.GREEN);
                }
            else if(showFavorites && poi.getFavorite() && userCreated && poi.getUserCreated()){
                g2d.setColor(Color.GREEN);
            }
            else {
                g2d.setColor(Color.RED);
            }
            g2d.fillOval(circleX, circleY, (int) (circleDiameter / scale), (int) (circleDiameter / scale));
            
            int circleX2 = (int) ((poi.getX_coordinate() + offsetX) * scale - circleDiameter / 2);
            int circleY2 = (int) ((poi.getY_coordinate() + offsetY) * scale - circleDiameter / 2);
            int circleWidth = (int) (circleDiameter * scale);
            int circleHeight = (int) (circleDiameter * scale);
            
            Shape circle = new Ellipse2D.Double(circleX2, circleY2, circleWidth, circleWidth);
            clickableCirclesWithPOIs.add(new POIWithShape(poi, circle));
        }
        


        // Draw the translucent circle over the selected POI
        // Draw a translucent circle over the selected POI
        if (isPOISelected) {
            int circleX = (int) (selectedPOI.getX_coordinate() + offsetX - circleDiameter / (2 * scale));
            int circleY = (int) (selectedPOI.getY_coordinate() + offsetY - circleDiameter / (2 * scale));
            g2d.setColor(new Color(0, 0, 255, 128));
            g2d.fillOval(circleX, circleY, (int) (circleDiameter / scale), (int) (circleDiameter / scale));

            int circleX2 = (int) ((selectedPOI.getX_coordinate() + offsetX) * scale - circleDiameter / 2);
            int circleY2 = (int) ((selectedPOI.getY_coordinate() + offsetY) * scale - circleDiameter / 2);
            int circleWidth = (int) (circleDiameter * scale);
            int circleHeight = (int) (circleDiameter * scale);

            selectedPOICircle = new Ellipse2D.Double(circleX2, circleY2, circleWidth, circleWidth);
        }

        // Reset the transformation to draw the text
        g2d.setTransform(new AffineTransform());

        // Draw the building and floor information
        String buildingName = ((Map) getParent()).getBuildingAndFloorName();
        g2d.setFont(new Font("Arial", Font.BOLD, 24));
        g2d.setColor(Color.BLACK);
        g2d.drawString(buildingName, 10, 30);
        
    }
    
    /**
     * Overrides mouseWheelMoved method in MouseWheelListener interface to handle mouse wheel event
     * 
     * @param e MouseWheelEvent object
     */
    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        int rotation = e.getWheelRotation();        //Get the direction and magnitude of the wheel rotation.
        double zoomFactor = 1.1;
        // Remember the current scale factor.
        double prevScale = scale;
        double minScale = 0.35;
        double maxScale = 2.0;
        
        if (rotation > 0 && (scale / zoomFactor) >= minScale) {     //if forward rotation
            scale /= zoomFactor;        //zoom out
        }
        else if (rotation < 0 && (scale * zoomFactor) <= maxScale){      //if backward rotation, zoom in
            scale *= zoomFactor;        
        }

        /*
        Calculate the amount that the image needs to be translated so that the point under the
        mouse stays in the same place. This calculation takes into account the previous and
        current scale factors, as well as the position of the mouse cursor.
        */
            
        offsetX += e.getX() * (1 - prevScale / scale);
        offsetY += e.getY() * (1 - prevScale / scale);
        
        repaint();      //redraw the component to reflect the new scale factor and offset.
    }
    
    /**
     * Set floor plan image to display on map panel
     * 
     * @param floorPlanImage buffered image to be displayed on map panel
     */
    public void setFloorPlanImage(BufferedImage floorPlanImage) {
        mapImage = floorPlanImage;
        
        //reset the map position and scale to default
        offsetX = 0;
        offsetY = 0;
        scale = 0.75;
        
        //center the map image
        int centerX = getWidth() / 2;
        int centerY = getHeight() / 2;
        int imageWidth = (int) (mapImage.getWidth() * scale);
        int imageHeight = (int) (mapImage.getHeight() * scale);
        offsetX = centerX - imageWidth / 2;
        offsetY = centerY - imageHeight / 2;
        
        //repaint the panel with the new image and position
        scale = 1;
        revalidate();
        repaint();
    }

    /**
     * Get floor plan image to display on map panel
     * 
     * @return floor plan image of type buffered image
     */
    public BufferedImage getFloorPlanImage() {
        return mapImage;
    }
    
    /**
     * Center given POI on screen
     * 
     * @param poi POI object of current POI to be centered
     */
    public void centerPOI(PointOfInterest poi) {
        double zoomFactor = 1.25;       //set the zoom factor for centering around the POI.

        scale = zoomFactor;     //set the scale to the zoom factor so that the POI is centered in the view.
        int x = poi.getX_coordinate();      //get the coordinates of the POI.
        int y = poi.getY_coordinate();

        //calculate the new offset values based on the POI coordinates and the current width and height of the view.
        offsetX = (int) (getWidth() / (2 * scale)) - x;
        offsetY = (int) (getHeight() / (2 * scale)) - y;

        //set the selected POI and mark it as selected.
        selectedPOIX = x;
        selectedPOIY = y;
        selectedPOI = poi;
        isPOISelected = true;

        //revalidate and repaint the view to update the display.
        revalidate();
        repaint();
    }
    
    /**
     * Set current selected POI
     * 
     * @param x
     * @param y 
     */
    public void setSelectedPOI(int x, int y) {      //highlight current POI
        isPOISelected = true;
        selectedPOIX = x;
        selectedPOIY = y;
        repaint();
    }
    
    /**
     * Clear current selected POI
     */
    public void clearSelectedPOI() {        //de-highlight POI
        isPOISelected = false;
        selectedPOIX = -1;
        selectedPOIY = -1;
        repaint();
    }
    
    /**
     * Creates a panel with information of selected POI
     * Panel displays Name, Room Number, Building, Floor, Description, Creator, and Favorite checkbox
     * Panel displays option to edit and delete POI
     * If POI is built-in field for admin code is displayed
     * Authentication with admin code required to edit or delete built-in POI
     * 
     * @param poi POI object of current POI
     */
    public void displayPOIPanel(PointOfInterest poi) {
        //get POI ID
        int poiId = poi.getId();
        
        //initialize Swing elements
        JFrame frame = new JFrame(poi.getName());
        JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(frame);
        JPanel panel = new JPanel(new GridLayout(0, 1));
        JPanel lowerPanel = new JPanel(new GridLayout(1,2));
        JPanel buttonPanel = new JPanel(new GridLayout(1,2));
        JLabel nameLabel = new JLabel("Name: " + poi.getName());
        JLabel roomNumberLabel = new JLabel("Room Number: " + poi.getRoom_number());
        JLabel buildingLabel = new JLabel("Building: " + poi.getBuilding());
        JLabel floorLabel = new JLabel("Floor: " + poi.getFloor());
        JLabel userCreated;
        JCheckBox favoriteCheckbox = new JCheckBox("Favorite");
        JLabel admin = new JLabel("Admin Code: ");
        JTextArea descriptionArea = new JTextArea(poi.getDescription());
        JTextField adminCodeField = new JTextField();
        JButton edit = new JButton("Edit");
        JButton delete = new JButton("Delete");
        
        frame.setAlwaysOnTop(true);
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        //create panel to align buttons
        buttonPanel.add(edit);
        buttonPanel.add(delete);
        
        edit.setSize(60, 30);
        delete.setSize(60,30);
        
        descriptionArea.setLineWrap(true);
        descriptionArea.setWrapStyleWord(true);
        descriptionArea.setEditable(false);
        
        //display type of POI
        if (poi.getUserCreated()) {
            userCreated = new JLabel("User Created POI");
        }
        else {
            userCreated = new JLabel("Built-in POI");
        }
        
        //create panel to align elements
        lowerPanel.add(userCreated);
        lowerPanel.add(favoriteCheckbox);
        
        favoriteCheckbox.setSelected(poi.getFavorite());
        favoriteCheckbox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                poi.setFavorite(e.getStateChange() == ItemEvent.SELECTED);
                        try {
                            writePOIToJSON(poi);
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
            }
        });
        
        //add elements to panel
        panel.add(nameLabel);
        panel.add(roomNumberLabel);
        panel.add(buildingLabel);
        panel.add(floorLabel);
        panel.add(descriptionArea);
        panel.add(lowerPanel);
        if (!poi.getUserCreated()) {        //display field to use admin code
            panel.add(admin);
            panel.add(adminCodeField);
        }
        panel.add(buttonPanel);
        
        
        edit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                if ((poi.getUserCreated()) || !poi.getUserCreated() && adminCodeField.getText().equals(code)) {     //if user-created, or built-in with valid adminCode
                    //initialize Swing elements
                    JPanel editPanel = new JPanel(new GridLayout(0,1));
                    JTextField poiNameField = new JTextField(15);
                    JTextField roomNumberField = new JTextField(15);
                    JTextField descriptionField = new JTextField(15);
                    JCheckBox checkAdminEdit = new JCheckBox();
                    checkAdminEdit.setHorizontalTextPosition(SwingConstants.LEFT);
                    int result;
                    
                    //add elements to panel
                    editPanel.add(new JLabel("Name:"));
                    editPanel.add(poiNameField);
                    editPanel.add(new JLabel("Room Number:"));
                    editPanel.add(roomNumberField);
                    editPanel.add(new JLabel("Description:"));
                    editPanel.add(descriptionField);
                    
                    if (poi.getUserCreated()) {
                        result = JOptionPane.showConfirmDialog(null, editPanel, "Edit POI", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
                    }
                    else {
                        result = JOptionPane.showConfirmDialog(null, editPanel, "ADMIN: Edit POI", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
                    }
                    
                    if (result == JOptionPane.OK_OPTION) {
                        String poiName = poiNameField.getText();
                        String roomNumber = roomNumberField.getText();
                        String description = descriptionField.getText();

                        poi.setName(poiName);
                        poi.setRoom_number(roomNumber);
                        poi.setDescription(description);
                    }
                    
                    try {
                        //delete object
                        DeletePOIObject instance = new DeletePOIObject(parentFrame, poiId, "src\\poi.json");
                        
                        //write updated object to Json file
                        ObjectMapper objectMapper = new ObjectMapper();
                        File jsonFile = new File("src\\poi.json");
                        JsonNode rootNode = objectMapper.readTree(jsonFile);
                        ((ArrayNode) rootNode.get("points_of_interest")).add(objectMapper.valueToTree(poi));
                        objectMapper.writeValue(jsonFile, rootNode);
                        clearSelectedPOI();
                        parent.refreshScreen(mapBuilding, mapFloor);

                        System.out.println("POI edit successful, userCreated = " + poi.getUserCreated());
                    } 
                    catch (IOException e) {
                        e.printStackTrace();
                        JOptionPane.showMessageDialog(parentFrame, "Error editing POI: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
                else if (!adminCodeField.getText().equals(code)) {
                    AlertWindow alert = new AlertWindow();
                }
            }
        });
        
        delete.addActionListener(new ActionListener() {     //when delete button is clicked
            public void actionPerformed(ActionEvent evt) {
                int result;
                
                if ((poi.getUserCreated()) || !poi.getUserCreated() && adminCodeField.getText().equals(code)) {       //if user-created, or built-in with valid adminCode
                    if (poi.getUserCreated()) {
                        result = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete current POI?", "Delete POI", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
                    }
                    else {
                        result = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete current POI?", "ADMIN: Delete POI", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
                    }

                    if (result == JOptionPane.OK_OPTION) {
                        try {
                            //delete object
                            DeletePOIObject instance = new DeletePOIObject(parentFrame, poiId, "src\\poi.json");
                            System.out.println("Deleted");
                            parent.refreshScreen(mapBuilding, mapFloor);
                        } 
                        catch (IOException e) {
                            e.printStackTrace();
                            JOptionPane.showMessageDialog(parentFrame, "Error editing POI: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                }
                else if (!adminCodeField.getText().equals(code)) {
                    AlertWindow alert = new AlertWindow();
                }
            }
        });
        
        //display panel on frame
        frame.getContentPane().add(panel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setBounds(0, 0, 400, 550);
        frame.setVisible(true);
    }

    /**
     * Adds new POI object to JSon file of current user
     * 
     * @param poi
     * @throws IOException if error opening JSon file
     */
    private void writePOIToJSON(PointOfInterest poi) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        File jsonFile = new File("src\\poi.json");
        JsonNode rootNode = objectMapper.readTree(jsonFile);        //get rootNode of tree
        ArrayNode pointsOfInterestNode = (ArrayNode) rootNode.get("points_of_interest");

        for (JsonNode pointNode : pointsOfInterestNode) {       //iterate through tree, delete object when found
            int pointId = pointNode.get("id").asInt();
            if (pointId == poi.getId()) {
                //remove the object from the array
                ((ObjectNode) pointNode).put("favorite", poi.getFavorite());
                break;
            }
        }

        objectMapper.writeValue(jsonFile, rootNode);
    }
    
    /**
     * Creates a panel to add information for new POI
     * Constructs PointOfInterest object and adds to JSon file for current user
     * 
     * @param point Coordinates of cursor click location
     */
    private void showNewPOIPanel(Point point){
        //initialize Swing elements
        JTextField poiNameField = new JTextField(15);
        JTextField roomNumberField = new JTextField(15);
        JTextField descriptionField = new JTextField(15);
        JTextField adminCodeField = new JTextField(15);
        JCheckBox checkDevEdit = new JCheckBox("Admin Edit");
        checkDevEdit.setHorizontalTextPosition(SwingConstants.LEFT);
        JPanel panel = new JPanel(new GridLayout(0, 1));
        JPanel lowerPanel = new JPanel(new GridLayout(1,2));
        
        //add elements to lowerPanel
        lowerPanel.add(new JLabel("Admin Code: "));
        lowerPanel.add(adminCodeField);
        
        //add elements to panel
        panel.add(new JLabel("Name: "));
        panel.add(poiNameField);
        panel.add(new JLabel("Room Number: "));
        panel.add(roomNumberField);
        panel.add(new JLabel("Description: "));
        panel.add(descriptionField);
        panel.add(checkDevEdit);
        panel.add(lowerPanel);
        
        //confirmation dialog
        int result = JOptionPane.showConfirmDialog(this, panel, "New POI", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            String poiName = poiNameField.getText();
            String roomNumber = roomNumberField.getText();
            String description = descriptionField.getText();

            int x = (int) (point.getX() / scale) - offsetX;
            int y = (int) (point.getY() / scale) - offsetY;


            //get the building and floor information
            Map parentMap = (Map) getParent();
            String building = parentMap.getBuilding();
            int floor = parentMap.getFloor();

            
            SearchPOI searchPOI = new SearchPOI("src\\poi.json");       //instantiate the SearchPOI class

            
            List<PointOfInterest> pointsOfInterest = searchPOI.getAllPOIs();        //read the existing POIs

            //create a new POI and set its properties
            PointOfInterest newPOI = new PointOfInterest();
            int newId = pointsOfInterest.size() + 1;
            newPOI.setId(newId);
            newPOI.setName(poiName);
            newPOI.setRoom_number(roomNumber);
            newPOI.setDescription(description);
            newPOI.setX_coordinate(x);
            newPOI.setY_coordinate(y);
            newPOI.setType("User Created");
            newPOI.setBuilding(building);
            newPOI.setFloor(floor);
            newPOI.setFavorite(false);
            
            if (!checkDevEdit.isSelected()) {       //if not adding built-in POI
                newPOI.setUserCreated(true);        //mark as user-created
                pointsOfInterest.add(newPOI);
            }
            else {
                if (adminCodeField.getText().equals(code)){       //if checkbox true and code valid
                    newPOI.setUserCreated(false);
                    pointsOfInterest.add(newPOI);
                    
                }
                else {
                    AlertWindow alert = new AlertWindow();      //incorrect code, alert user
                }
            }

            //save the updated list of POIs to the JSON file
            if ((!checkDevEdit.isSelected() || (checkDevEdit.isSelected() && adminCodeField.getText().equals(code)))) {       //check if POI can be added
                try {
                    ObjectMapper objectMapper = new ObjectMapper();
                    File jsonFile = new File("src\\poi.json");
                    JsonNode rootNode = objectMapper.readTree(jsonFile);
                    ((ArrayNode) rootNode.get("points_of_interest")).add(objectMapper.valueToTree(newPOI));
                    objectMapper.writeValue(jsonFile, rootNode);
                    centerPOI(newPOI);
                    displayPOIPanel(newPOI);
                    //pointsOfInterest.add(newPOI);
                    //printAllPOIs(pointsOfInterest);
                    System.out.println("New POI '" + poiName + "' created at (" + x + ", " + y + ")");
                    parent.refreshScreen(mapBuilding, mapFloor);
                }
                catch (IOException e) {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(this, "Error adding new POI: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
            
            centerPOI(newPOI);
            displayPOIPanel(newPOI);
        }
    }
    
    /**
     * Draws circle on map at given coordinates
     * 
     * @param g2d Graphics2D object used to draw circle
     * @param x X-coordinate of center of circle on map
     * @param y Y-coordinate of center of circle on map
     * @param color color of circle to be drawn
     */
    public void drawCircle(Graphics2D g2d, int x, int y, Color color) {     //draw circle on map
        int diameter = 40;
        int radius = diameter / 2;
        int centerX = (int) (x + offsetX);
        int centerY = (int) (y + offsetY);
        int circleX = centerX - radius;
        int circleY = centerY - radius;
        g2d.setColor(color);
        g2d.fillOval(circleX, circleY, diameter, diameter);
    }
    
    /**
     * Displays all POIs on current map
     * 
     * @param currentBuilding current building being displayed
     * @param currentFloor current floor if building being displayed
     */
    public void displayAllPOIs(String currentBuilding, int currentFloor) {
        this.mapBuilding = currentBuilding;
        this.mapFloor = currentFloor;
        SearchPOI searchPOI = new SearchPOI("src\\poi.json");
        List<PointOfInterest> pois = searchPOI.getAllPOIs();
        accessiblePOIs.clear();
        for (PointOfInterest poi : pois) {
            String poiTypeLowerCase;
            if (poi.getType() != null) {
                poiTypeLowerCase = poi.getType().toLowerCase();
            }
            else {
                poiTypeLowerCase = null;
            }
            if (poiTypeLowerCase != null){
                if (poi.getBuilding().equals(mapBuilding) && poi.getFloor() == mapFloor && 
                    (poiTypeLowerCase.equals("elevator") || poiTypeLowerCase.equals("stairwell") || poiTypeLowerCase.equals("washroom") || poiTypeLowerCase.equals("building"))) {
                    accessiblePOIs.add(poi);
                }
            }
        }
        repaint();
    }
    
    /**
     * Updates POIs on current map being displayed
     * 
     * @param selectedTypes types of POIs to be displayed
     * @param building current building being displayed
     * @param floor current floor of building being displayed
     * @param showFavorites True if favorites should be displayed on map
     * @param userCreated True if user created POIs should be displayed on map
     */
    public void updatePOIDisplay(Set<String> selectedTypes, String building, int floor, boolean showFavorites, boolean userCreated) {
        SearchPOI searchPOI = new SearchPOI("src\\poi.json");
        List<PointOfInterest> pois = searchPOI.getAllPOIs();
        this.mapBuilding = building;
        this.mapFloor = floor;
        visiblePOIs.clear();
        for (PointOfInterest poi : pois) {
            if (poi.getBuilding().equals(mapBuilding) && poi.getFloor() == mapFloor && selectedTypes.contains(poi.getType())) {
                visiblePOIs.add(poi);
            }
        }
        this.showFavorites = showFavorites;
        this.userCreated = userCreated;
        repaint();
        System.out.println("Visible POIs: " + visiblePOIs);

    }
    
    /**
     * Clear all POIs displayed on current map
     */
    public void clearAllPOICircles() {
        visiblePOIs.clear();
        repaint();
    }
}