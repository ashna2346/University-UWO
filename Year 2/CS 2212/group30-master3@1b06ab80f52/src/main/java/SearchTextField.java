/**
 * @author Team30
 * @version 1.0
 * 
 * Search field that shows matching POIs when search button is clicked on
 */

import javax.swing.*;
import java.awt.event.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;

public class SearchTextField extends JTextField {
    private JPopupMenu searchResultPopup;
    private SearchPOI searchPOI;
    
    /**
     * Construct text field
     * 
     * @param searchPOI searchPOI object used to search for POIs
     */
    public SearchTextField(SearchPOI searchPOI) {
        this.searchPOI = searchPOI;
        searchResultPopup = new JPopupMenu();

        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                if (!isNavigationKey(e)) {
                    updatePOIList();
                }
            }
        });

        searchResultPopup.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentHidden(ComponentEvent e) {
                SwingUtilities.invokeLater(() -> requestFocusInWindow());
            }
        });
    }
    /**
     * Check if key pressed is a navigation key
     * 
     * @param e key press event
     * @return True if key pressed is navigation key
     */
    private boolean isNavigationKey(KeyEvent e) {
        return e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_DOWN
                || e.getKeyCode() == KeyEvent.VK_ENTER || e.getKeyCode() == KeyEvent.VK_ESCAPE;
    }
    
    /**
     * Update list of POI based on current search term
     */
    private void updatePOIList() {
        String searchText = getText();
        List<PointOfInterest> pois;
        if (searchText.isEmpty()) {
            pois = searchPOI.getAllPOIs();
        } else {
            pois = searchPOI.searchForPOI(searchText);
        }

        searchResultPopup.removeAll();
        for (PointOfInterest poi : pois) {
            JMenuItem menuItem = new JMenuItem(poi.getName());
            menuItem.addActionListener(e -> {
                setText(poi.getName());
                searchResultPopup.setVisible(false);
                requestFocusInWindow(); // request focus after selecting an item
            });
            searchResultPopup.add(menuItem);
        }

        if (!pois.isEmpty()) {
            searchResultPopup.show(this, 0, this.getHeight());
            requestFocusInWindow();
        } else {
            searchResultPopup.setVisible(false);
            requestFocusInWindow(); // request focus after hiding the popup
        }
    }
}
