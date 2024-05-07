import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.util.List;
import java.util.*;

/**
 *
 * @author Team30
 * @version 1.0
 * 
 * This class creates the Maps and displays the functional requirements necessary for the application to function 
 */
public class Map extends JPanel {
    private MapPanel mapPanel;
    private JPopupMenu searchResultPopup;
    private JTextField searchField;
    private BufferedImage[][] floorPlanImages;
    public int currentBuilding = 0;
    public int currentFloor = 0;
    private JButton upArrowButton;
    private JButton downArrowButton;
    private JLabel buildingLabel;
    private JLabel floorLabel;
    public JMenu poiMenu;
    public JMenu layersMenu;
    public JMenu favoriteMenu;
    public List<PointOfInterest> pois;
    public Set<String> selectedTypes = new HashSet<>();
    private Set<String> selectedFavorites = new HashSet<>();
    private Set<String> selectedUserCreated = new HashSet<>();
    private Graphics2D g2d;
    private boolean favorite;
    private boolean userCreated;
    private String poiFile;
    
    /**
     * default constructor used to display the maps of the buildings selected 
     */
    public Map() {
        MapDisplay mapDisplay = new MapDisplay();   // Create a new MapDisplay object that handles the display of the map.
        //System.out.println("sdfsdfsdfsd" + mapDisplay.getPOIFile());
        setPOIjson(mapDisplay.getPOIFile());
        searchResultPopup = new JPopupMenu();   // Create a new JPopupMenu object to display search results for POIs
        searchResultPopup.setFocusable(false);
        //System.out.println("HERE!!!" + poi.json);
        SearchPOI searchPOI2 = new SearchPOI(poiFile);  // Create a new SearchPOI object that handles the search functionality for POIs.
        
        JMenuBar menuBar = createMenuBar(); // Create a new JMenuBar object by calling the createMenuBar() method.
        floorPlanImages = new BufferedImage[4][];
        try {
            floorPlanImages[0] = new BufferedImage[]{ImageIO.read(new File("src\\images\\map_image.png"))};
            floorPlanImages[1] = new BufferedImage[]{ImageIO.read(new File("src\\images\\middlesex1.jpg")), ImageIO.read(new File("src\\images\\middlesex2.jpg")),
            ImageIO.read(new File("src\\images\\middlesex3.jpg")), ImageIO.read(new File("src\\images\\middlesex4.jpg"))};
            floorPlanImages[2] = new BufferedImage[]{ImageIO.read(new File("src\\images\\ucc1.png")), ImageIO.read(new File("src\\images\\ucc2.png")), 
            ImageIO.read(new File("src\\images\\ucc3.png")), ImageIO.read(new File("src\\images\\ucc4.png")), ImageIO.read(new File("src\\images\\ucc5.png"))};
            floorPlanImages[3] = new BufferedImage[]{ImageIO.read(new File("src\\images\\nsc1.png")), ImageIO.read(new File("src\\images\\nsc2.png")), 
            ImageIO.read(new File("src\\images\\nsc3.png"))};
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.favorite = false;
        this.userCreated = false;
        mapPanel = new MapPanel(this);
        JScrollPane scrollPane = new JScrollPane(mapPanel); // Create a new MapPanel object and wrap it in a JScrollPane object.
        FloorNavigation floorNavigation = new FloorNavigation(e -> changeFloor(1), e -> changeFloor(-1));   // Create a new FloorNavigation object that handles the navigation between floors of the building.

        setLayout(new BorderLayout());
        add(menuBar, BorderLayout.NORTH);
        add(mapPanel, BorderLayout.CENTER);
        mapPanel.displayAllPOIs(getBuilding(), getFloor()); // Set the layout of the Map object to BorderLayout and add the menu bar to the north, the map panel to the center, and display all the POIs for the current building and floor.
    }
    
    /**
     * 
     * Sets the path of the points of interest (POI) JSON file.
     * @param file the file name of the POI JSON file
    */
    public void setPOIjson(String file) {
        this.poiFile = "src\\poi.json";
    }
  
    /**
     * Adds POI layers to a given menu. The available POI layers are determined based on the building and current floor.
     * @param menu : menu design inputted as parameter; this is the menu to which the POI layers will be added
     */
    private void addLayersToMenu(JMenu menu) {
        SearchPOI searchPOI = new SearchPOI("src\\poi.json");   // Create a SearchPOI object to get all the POIs
        List<PointOfInterest> pois = searchPOI.getAllPOIs();    // Get all POIs for the current building and floor
        Set<String> availableTypes = new HashSet<>();

        String buildingName = getBuilding();    // Get the name of the current building

        for (PointOfInterest poi : pois)    // Loop through all POIs and add the available types for the current building and floor
        {
            if (poi.getBuilding().equals(buildingName) && poi.getFloor() == currentFloor + 1) {
                availableTypes.add(poi.getType());
            }
        }
        
        List<String> fixedLabels = Arrays.asList("Elevator", "Stairwell", "Washroom", "Building");  // Create a list of fixed labels for POI types that are always available

        for (String label : fixedLabels) {
            String matchingType = null;
            for (String type : availableTypes) {
                if(type!= null) {
                if (type.equalsIgnoreCase(label) && !type.equalsIgnoreCase("user created")) {
                    matchingType = type;
                    break;
                }
                }
            }
            if (matchingType != null) {
                availableTypes.remove(matchingType);
                JLabel fixedLabel = new JLabel(matchingType);
                fixedLabel.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 0));
                menu.add(fixedLabel);   // Add available POI types to the menu
            }
        }
        

        for (String type : availableTypes) {
            JCheckBoxMenuItem layerMenuItem = new JCheckBoxMenuItem(type);
            layerMenuItem.setSelected(false);

            layerMenuItem.addActionListener(new ActionListener() // Adding an ActionListener to perform actions when the layerMenuItem is clicked
            {
                @Override
                public void actionPerformed(ActionEvent e) {
                    System.out.println("Before updating: " + selectedTypes);

                    if (layerMenuItem.isSelected()) {
                        selectedTypes.add(type);
                    } else {
                        selectedTypes.remove(type);
                    }
                    System.out.println("After updating: " + selectedTypes);
                    // Call a method to update the display of POIs
                    mapPanel.updatePOIDisplay(selectedTypes, getBuilding(), getFloor(), favorite, userCreated);
                }
            });
            menu.add(layerMenuItem);
        }

        JCheckBoxMenuItem favoritesMenuItem = new JCheckBoxMenuItem("Favorites");
        favoritesMenuItem.setSelected(false);

        
        favoritesMenuItem.addActionListener(new ActionListener() // Adding an ActionListener to perform actions when the favoritesMenuItem is clicked
        {
            @Override
            
            public void actionPerformed(ActionEvent e) {
                if (favoritesMenuItem.isSelected()) {
                    setFavoritePOI(true);
                } else {
                    setFavoritePOI(false);
                }
                mapPanel.updatePOIDisplay(selectedTypes, getBuilding(), getFloor(), favorite, userCreated);
            }
        });
        menu.add(favoritesMenuItem);
    }
    
    /**
     * Sets the value of 'favorite' flag to indicate whether favorite POIs are to be displayed or not.
     * @param favorite : boolean value indicating whether favorite POIs are to be displayed or not.
     */
    private void setFavoritePOI(boolean favorite) {
        this.favorite = favorite;
    }
    
    /**
     * Sets the value of 'userCreated' flag to indicate whether user-created POIs are to be displayed or not.
     * @param userCreated : boolean value indicating whether user-created POIs are to be displayed or not.
     */
    private void setuserPOI(boolean userCreated) {
        this.userCreated = userCreated;
    }

    /**
     * Removes all the current menu items from the layersMenu and adds the filtered layers.
     */
    public void updateLayersMenu() {
        layersMenu.removeAll(); // Clear the current menu items
        addLayersToMenu(layersMenu); // Add the filtered layers
    }


    /**
     * Adds all the favorite points of interest to the given JMenu
     * @param menu : JMenu to which favorite points of interest will be added
     */
    private void addFavoritePOIsToMenu(JMenu menu) {
        SearchPOI searchPOI = new SearchPOI("src\\poi.json");   // Create a SearchPOI object to get all points of interest
        List<PointOfInterest> pois = searchPOI.getAllPOIs();    // Get all points of interest

        for (PointOfInterest poi : pois) // Loop through all points of interest
        {
            if (poi.getFavorite()) // If the point of interest is a favorite, create a JMenuItem with its name
            {
                JMenuItem menuItem = new JMenuItem(poi.getName());
                menuItem.addActionListener(new ActionListener() // Adding an ActionListener to perform actions when the JMenuItem is clicked
                {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        // Perform the same actions as for POI menu items
                        // (This code can be refactored into a separate method to avoid repetition)
                        String building = poi.getBuilding();
                        int floor = poi.getFloor();

                        // Set the MapPanel to display the selected building and floor
                        if (building.equals("Campus Map")) {
                            mapPanel.setFloorPlanImage(floorPlanImages[0][0]);
                            currentBuilding = 0;
                        } else if (building.equals("Middlesex College")) {
                            mapPanel.setFloorPlanImage(floorPlanImages[1][floor - 1]);
                            currentBuilding = 1;
                            currentFloor = floor - 1;
                        } else if (building.equals("University Community Centre")) {
                            mapPanel.setFloorPlanImage(floorPlanImages[2][floor - 1]);
                            currentBuilding = 2;
                            currentFloor = floor - 1;
                        } else if (building.equals("Natural Science Centre")) {
                            mapPanel.setFloorPlanImage(floorPlanImages[3][floor - 1]);
                            currentBuilding = 3;
                            currentFloor = floor - 1;
                        }

                        // Update the current floor and building labels
                        int x = poi.getX_coordinate();
                        int y = poi.getY_coordinate();
                        System.out.println("Selected POI: " + poi.getName() + ", X: " + poi.getX_coordinate() + ", Y: " + poi.getY_coordinate());
                        updateLayersMenu();
                        updatePOIMenu(poiMenu);
                        if(currentBuilding == 0){
                            upArrowButton.setVisible(false);
                            downArrowButton.setVisible(false);
                        }
                        else if(currentBuilding != 0 && currentFloor == 0 ){
                            upArrowButton.setVisible(true);
                            downArrowButton.setVisible(false);
                        }
                        
                        else {
                            upArrowButton.setVisible(true);
                            downArrowButton.setVisible(true);
                        }
                        mapPanel.clearAllPOICircles();
                        mapPanel.displayAllPOIs(getBuilding(), getFloor());
                        mapPanel.centerPOI(poi);
                        mapPanel.displayPOIPanel(poi);
                        updateFavoritesMenu();
                    }
                });
                menu.add(menuItem);
            }
        }
    }
    
    /**
     * Updates the favorite points of interest menu
     */
    public void updateFavoritesMenu() {
        favoriteMenu.removeAll();   // Clearing the current menu items
        addFavoritePOIsToMenu(favoriteMenu);    // Adding the updated favorite points of interest
    }

    /**
     * This method adds the list of PointOfInterest objects to the JMenu object in the GUI. It adds only the PointOfInterest objects
     * @param menu : the JMenu object to which the PointOfInterest objects will be added
     */
    private void addPOIsToMenu(JMenu menu) {
        
        SearchPOI searchPOI = new SearchPOI("src\\poi.json");   // Creating a SearchPOI object to search for all PointOfInterest objects in the given "src\\poi.json"
        List<PointOfInterest> pois = searchPOI.getAllPOIs();    // Get a list of all PointOfInterest objects

        for (PointOfInterest poi : pois) {
            if (poi.getBuilding().equals(getBuilding()) && poi.getFloor() == getFloor()) {
                JMenuItem menuItem = new JMenuItem(poi.getName());  // Create a JMenuItem object with the PointOfInterest object's name as the text
                menuItem.addActionListener(new ActionListener() // Add an ActionListener to the JMenuItem object
                {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Extract the building and floor information from the selected PointOfInterest
                    String building = poi.getBuilding();
                    int floor = poi.getFloor();

                    // Set the MapPanel to display the selected building and floor
                    if (building.equals("Campus Map")) {
                        mapPanel.setFloorPlanImage(floorPlanImages[0][0]);
                        currentBuilding = 0;
                    } else if (building.equals("Middlesex College")) {
                        mapPanel.setFloorPlanImage(floorPlanImages[1][floor - 1]);
                        currentBuilding = 1;
                        currentFloor = floor - 1;
                    } else if (building.equals("University Community Centre")) {
                        mapPanel.setFloorPlanImage(floorPlanImages[2][floor - 1]);
                        currentBuilding = 2;
                        currentFloor = floor - 1;
                    } else if (building.equals("Natural Science Centre")) {
                        mapPanel.setFloorPlanImage(floorPlanImages[3][floor - 1]);
                        currentBuilding = 3;
                        currentFloor = floor - 1;
                    }

                    // Update the current floor and building labels
                    //floorLabel.setText("Floor " + (currentFloor + 1));
                    //buildingLabel.setText(getBuildingAndFloorName());
                    int x = poi.getX_coordinate();
                    int y = poi.getY_coordinate();  // Get the coordinates of the selected PointOfInterest
                    System.out.println("Selected POI: " + poi.getName() + ", X: " + poi.getX_coordinate() + ", Y: " + poi.getY_coordinate());
                    
                    updateFavoritesMenu();   // Update the Favorites menu in the GUI
                    mapPanel.centerPOI(poi);    // Center the MapPanel on the selected PointOfInterest
                    mapPanel.displayPOIPanel(poi);  // Display the PointOfInterest panel in the GUI
                }
            });
            menu.add(menuItem);      // Add the JMenuItem object to the JMenu object
            }
        }
    }
    
    /**
     * Updates the Point of Interest (POI) menu by clearing the current menu items and adding the filtered POIs.
     * @param poiMenu : JMenu object representing the POI menu to be updated
     */
    public void updatePOIMenu(JMenu poiMenu) {
        poiMenu.removeAll(); // Clear the current menu items
        addPOIsToMenu(poiMenu); // Add the filtered POIs
    }

    /**
     * Adds the buildings to the given menu as menu items
     * @param menu : JMenu to which the buildings will be added
     */
    private void addBuildingsToMenu(JMenu menu) {    
        JMenuItem menuItem;

        menuItem = new JMenuItem("Campus Map");
        menuItem.addActionListener(new ActionListener() // The ActionListener updates the MapPanel to display the corresponding building and floor, and updates the necessary components
        {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Selected building: Campus Map");
                mapPanel.setFloorPlanImage(floorPlanImages[0][0]);  // Set the MapPanel to display the Campus Map building and floor 0
                currentBuilding = 0;
                currentFloor = 0;
                upArrowButton.setVisible(false);
                downArrowButton.setVisible(false);
                mapPanel.isPOISelected = false;
                updatePOIMenu(poiMenu);
                updateLayersMenu();
                updateFavoritesMenu();
                mapPanel.clearAllPOICircles();
                mapPanel.displayAllPOIs(getBuilding(), getFloor());
            }
        });
        menu.add(menuItem);

        // Repeating the same procedure for the other buildings
        menuItem = new JMenuItem("Middlesex College");
        menuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Selected building: Middlesex College");
                currentBuilding = 1;
                currentFloor = 0;
                mapPanel.setFloorPlanImage(floorPlanImages[currentBuilding][currentFloor]);
                upArrowButton.setVisible(true);
                downArrowButton.setVisible(false);
                mapPanel.isPOISelected = false;
                updatePOIMenu(poiMenu);
                updateLayersMenu();
                updateFavoritesMenu();
                mapPanel.clearAllPOICircles();
                mapPanel.displayAllPOIs(getBuilding(), getFloor());
            }
        });
        menu.add(menuItem);

        menuItem = new JMenuItem("University Community Centre");
        menuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Selected building: University Community Centre");
                mapPanel.setFloorPlanImage(floorPlanImages[2][0]);
                currentBuilding = 2;
                currentFloor = 0;
                upArrowButton.setVisible(true);
                downArrowButton.setVisible(false);
                mapPanel.isPOISelected = false;
                updatePOIMenu(poiMenu);
                updateLayersMenu();
                updateFavoritesMenu();
                mapPanel.clearAllPOICircles();
                mapPanel.displayAllPOIs(getBuilding(), getFloor());
                
            }
        });
        menu.add(menuItem);

            menuItem = new JMenuItem("Natural Science Centre");
            menuItem.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    System.out.println("Selected building: Natural Science Centre");
                    mapPanel.setFloorPlanImage(floorPlanImages[3][0]);
                    currentBuilding = 3;
                    currentFloor = 0;
                    upArrowButton.setVisible(true);
                    downArrowButton.setVisible(false);
                    mapPanel.isPOISelected = false;
                    updatePOIMenu(poiMenu);
                    updateLayersMenu();
                    updateFavoritesMenu();
                    mapPanel.clearAllPOICircles();
                    mapPanel.displayAllPOIs(getBuilding(), getFloor());
                    
                }
            });
            menu.add(menuItem);
    }
    
    /**
     * 
     * @return menuBar : the newly created JMenuBar
     */
    private JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        menuBar.setLayout(new GridBagLayout());
        SearchPOI searchPOI = new SearchPOI("src\\poi.json");
        
        JMenu buildingsMenu = new JMenu("Buildings");
        menuBar.add(buildingsMenu);
        
        JMenu discoverPOIsMenu = new JMenu("Discover POIs");
        menuBar.add(discoverPOIsMenu);

        favoriteMenu = new JMenu("Favorites");
        addFavoritePOIsToMenu(favoriteMenu);
        JButton favoriteButton = new JButton("Favorites");
        favoriteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Add your layers functionality here
                updateFavoritesMenu();
            }
        });
        menuBar.add(favoriteMenu);
        
        layersMenu = new JMenu("Layers");
        addLayersToMenu(layersMenu);
        JButton layersButton = new JButton("Layers");
        layersButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Add your layers functionality here
                updateLayersMenu();
            }
        });
        menuBar.add(layersMenu);
        
        // Create a new JButton for accessing favorites and add it to the menu bar
        JButton helpButton = new JButton("Help");
        helpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                HelpPanel helpFrame = new HelpPanel();
            }
        });
        menuBar.add(helpButton);

        JButton aboutButton = new JButton("About");
        aboutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame aboutFrame = new AboutPanel();
                aboutFrame.setVisible(true);
            }
        });
        menuBar.add(aboutButton);   // Adding the favorites menu to the menu bar
        

        
        upArrowButton = new JButton("↑");
        upArrowButton.addActionListener(e -> changeFloor(1));
        menuBar.add(upArrowButton);

        downArrowButton = new JButton("↓");
        downArrowButton.addActionListener(e -> changeFloor(-1));
        menuBar.add(downArrowButton);
        upArrowButton.setVisible(false);
        downArrowButton.setVisible(false);
        
        JButton refreshButton = new JButton("↻");
            refreshButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    updatePOIMenu(poiMenu);
                    updateLayersMenu();
                    updateFavoritesMenu();
                    mapPanel.clearAllPOICircles();
                    mapPanel.displayAllPOIs(getBuilding(), getFloor());
                }
            });

        setLayout(new BorderLayout());
        menuBar.add(refreshButton);
        
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.weightx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        menuBar.add(Box.createHorizontalGlue(), gbc);

        JPanel searchBarPanel = new JPanel(new BorderLayout());
        JTextField searchField = new JTextField();
        searchField.setEditable(true);
        searchField.setPreferredSize(new Dimension(200, 20)); // Set a static size for the search field

        searchBarPanel.add(searchField, BorderLayout.CENTER);

        gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.EAST;
        menuBar.add(searchBarPanel, gbc);
        
        JButton searchButton = new JButton("Search");
        searchButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String searchTerm = searchField.getText().trim();
                    List<PointOfInterest> searchResults = searchPOI.searchPOIs(searchTerm);
                    JPopupMenu searchResultsPopup = new JPopupMenu();
                    if (searchResults.isEmpty()) {
                        searchResultsPopup.add("NO POI FOUND");
                    }
                    else {
                            for (PointOfInterest poi : searchResults) {
                                JMenuItem resultItem = new JMenuItem(poi.getName());
                                resultItem.addActionListener(new ActionListener() {
                                    @Override
                                    public void actionPerformed(ActionEvent e) {
                                        PointOfInterest selectedPOI = poi;
                                        currentFloor = poi.getFloor() - 1;
                                        mapPanel.setFloorPlanImage(floorPlanImages[currentBuilding][currentFloor]);
                                        mapPanel.centerPOI(selectedPOI);
                                        mapPanel.displayPOIPanel(selectedPOI);
                                        updatePOIMenu(poiMenu);
                                        updateLayersMenu();
                                        updateFavoritesMenu();
                                        mapPanel.clearAllPOICircles();
                                        mapPanel.displayAllPOIs(getBuilding(), getFloor());
                                        if(currentBuilding == 0){
                                            upArrowButton.setVisible(false);
                                            downArrowButton.setVisible(false);
                                        }
                                        else if(currentBuilding != 0 && currentFloor == 0 ){
                                            upArrowButton.setVisible(true);
                                            downArrowButton.setVisible(false);
                                        }

                                        else {
                                            upArrowButton.setVisible(true);
                                            downArrowButton.setVisible(true);
                                        }
                                        
                                    }
                                });
                                searchResultsPopup.add(resultItem);
                            }
                            
                        }
                    searchResultsPopup.show(searchField, 0, searchField.getHeight());
                        }
                    });
            searchBarPanel.add(searchButton, BorderLayout.EAST);

            gbc = new GridBagConstraints();
            gbc.anchor = GridBagConstraints.EAST;
            menuBar.add(searchBarPanel, gbc);

        
        for (Component component : menuBar.getComponents()) {
            if (component instanceof JMenu) {
                JMenu menu = (JMenu) component;
                if (menu.getText().equals("Discover POIs")) {
                    poiMenu = menu;
                    addPOIsToMenu(menu);
                } else if (menu.getText().equals("Buildings")) {
                    addBuildingsToMenu(menu);
                } //else if (menu.getText().equals("Favorites")) {
                    //addFavoritePOIsToMenu(menu);
                //}
            }
        }
        

        return menuBar;
    }
    
    /**
     * Changes the current floor by the given delta value.
     * @param delta : the amount by which to change the current floor. A positive value indicates to go up one floor, while a negative value indicates to go down one floor.
     */
    private void changeFloor(int delta) {
        if (floorPlanImages[currentBuilding].length > 0) {
            for (int i = 0; i < floorPlanImages[currentBuilding].length; i++) {
                if (mapPanel.getFloorPlanImage() == floorPlanImages[currentBuilding][i]) {
                    currentFloor = i;
                    break;
                }
            }

            // Calculate the new floor index based on the delta value
            int newFloor = (currentFloor + delta) % floorPlanImages[currentBuilding].length;
            if (newFloor < 0) {
                newFloor += floorPlanImages[currentBuilding].length;
            }

            // Hide the corresponding arrow button if the current floor is the first or the top floor
            if (newFloor == 0) {
                downArrowButton.setVisible(false);
            } else if (newFloor == floorPlanImages[currentBuilding].length - 1) {
                upArrowButton.setVisible(false);
            } else {
                downArrowButton.setVisible(true);
                upArrowButton.setVisible(true);
            }

            // Set the new floor plan image and update relevant menus
            mapPanel.setFloorPlanImage(floorPlanImages[currentBuilding][newFloor]);
            this.currentFloor = newFloor; // Update the currentFloor variable

            updatePOIMenu(poiMenu);
            updateLayersMenu();
            updateFavoritesMenu();
            
            // Clear all POI circles and display the POIs for the new floor
            mapPanel.clearAllPOICircles();
            mapPanel.displayAllPOIs(getBuilding(), getFloor());
            mapPanel.isPOISelected = false;
        }
    }
    
   /**
    * Returns a string representing the current building and floor name.
    * @return buildingName : string representing the current building and floor name.
    */
    public String getBuildingAndFloorName() {
    String buildingName;
    switch (currentBuilding) {
        case 0:
            buildingName = "Campus Map";
            break;
        case 1:
            buildingName = "Middlesex College Floor " + (currentFloor + 1);
            break;
        case 2:
            buildingName = "University Community Centre Floor " + (currentFloor + 1);
            break;
        case 3:
            buildingName = "Natural Science Centre Floor " + (currentFloor + 1);
            break;
        default:
            buildingName = "";
    }
    return buildingName;
    }
    
    /**
     * Returns the name of the current building based on the currentBuilding instance variable.
     * @return buildingName : the name of the current building as a String
     */
    public String getBuilding() {
        String buildingName;
        switch (currentBuilding) {
            case 0:
                buildingName = "Campus Map";
                break;
            case 1:
                buildingName = "Middlesex College";
                break;
            case 2:
                buildingName = "University Community Centre";
                break;
            case 3:
                buildingName = "Natural Science Centre";
                break;
            default:
                buildingName = "";
        }
        return buildingName;
    }
    
    /**
     * 
     * @return the current floor number.
     */
    public int getFloor() {
        return currentFloor + 1;
    }
    
    /**
     * Returns the name of the building based on the building index provided.
     * @param buildingIndex : the index of the building
     * @return the name of the building
     */
    public String getNewBuilding(int buildingIndex) {
    String buildingName;
    switch (buildingIndex) {
        case 0:
            buildingName = "Campus Map";
            break;
        case 1:
            buildingName = "Middlesex College";
            break;
        case 2:
            buildingName = "University Community Centre";
            break;
        case 3:
            buildingName = "Natural Science Centre";
            break;
        default:
            buildingName = "";
        }
        return buildingName;
    }
    
    /**
     * changes the current building to the one specified by the newBuildingIndex parameter.
     * @param newBuildingIndex : index of the new building to switch to
     */
    public void changeBuilding(int newBuildingIndex) {
        currentBuilding = newBuildingIndex;
        currentFloor = 0; // Set the floor to the first floor

        SearchPOI searchPOI = new SearchPOI(poiFile);
        pois = searchPOI.getAllPOIs();

        // Update selectedTypes based on the new building and floor
        selectedTypes.clear();
        for (PointOfInterest poi : pois) {
            if (poi.getBuilding().equalsIgnoreCase(getNewBuilding(currentBuilding)) && poi.getFloor() == currentFloor + 1) {
                selectedTypes.add(poi.getType());
            }
        }
        updatePOIMenu(poiMenu);
        updateLayersMenu();
        updateFavoritesMenu();
        mapPanel.clearAllPOICircles();
        mapPanel.updatePOIDisplay(selectedTypes, getNewBuilding(currentBuilding), currentFloor, favorite, userCreated);
    }

    /**
     * Refreshes the screen by updating the POI menu, layers menu, and favorites menu, and displaying all POIs on the given floor and building.
     * @param building : String representing the name of the building to be displayed
     * @param floor : an integer representing the floor number to be displayed
     */
    public void refreshScreen(String building, int floor) {
        this.updatePOIMenu(this.poiMenu);
        this.updateLayersMenu();
        this.updateFavoritesMenu(); // Update POI menu, layers menu, and favorites menu
        mapPanel.clearAllPOICircles();  // Clear all POI circles and display all POIs on the given floor and building
        mapPanel.displayAllPOIs(building, floor);
    }
}
