/**
 * @author Team30
 * @version 1.0
 * 
 * Creates popup when help button is pressed
 */

import javax.swing.*;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

public class HelpPanel{
    /**
     * Create popup with and give option to redirect and open help webpage in browser
     */
    public HelpPanel() {
        System.out.println("HelpPanel method called");
        int choice = JOptionPane.showConfirmDialog(null, "Are you sure you want to redirect to Help Page?", null, JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (choice == JOptionPane.YES_OPTION) {
            try {
                File file = new File("src//help.html");
                Desktop.getDesktop().open(file);
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
