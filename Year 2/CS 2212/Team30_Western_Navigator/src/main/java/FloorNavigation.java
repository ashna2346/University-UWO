/**
 * @author Team30
 * @version 1.0
 * 
 * Creates buttons for floor navigation
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class FloorNavigation extends JPanel {
    private JButton upButton;
    private JButton downButton;
    
    /**
     * Handles displaying UP and DOWN floor navigation buttons
     * 
     * @param upListener checks if floor above exists
     * @param downListener checks if floor below exists
     */
    public FloorNavigation(ActionListener upListener, ActionListener downListener) {
        upButton = new JButton("\u2191");
        downButton = new JButton("\u2193");

        upButton.addActionListener(upListener);
        downButton.addActionListener(downListener);

        setLayout(new BorderLayout());
        add(upButton, BorderLayout.EAST);
        add(downButton, BorderLayout.WEST);
    }
}