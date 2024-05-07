/**
 * @author Team30
 * @version 1.0
 * 
 * Class to represent search bar with text field and search button
 * Used to search for POIs in any map
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SearchBar extends JPanel {
    private JTextField searchField;
    private JButton searchButton;
    
    /**
     * Creates search bar instance
     */
    public SearchBar() {
        setLayout(new FlowLayout(FlowLayout.RIGHT));

        searchField = new JTextField(20);
        searchButton = new JButton("Search");

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String searchText = searchField.getText();
                // Implement your POI search logic here
                System.out.println("Searching for: " + searchText);
            }
        });

        add(searchField);
        add(searchButton);
    }
}
