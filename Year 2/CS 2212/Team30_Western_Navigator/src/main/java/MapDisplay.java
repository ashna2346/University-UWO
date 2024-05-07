/**
 * @author Team30
 * @version 1.0
 * 
 * Main class
 * Creates main window to display map, POIs, and all related information
 */

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import java.awt.event.*;
import javax.swing.JOptionPane;

public class MapDisplay {
    private static String poiFile;

    /**
     * Main method
     * 
     * @param args 
     */
    public static void main(String[] args) {

        // Create an instance of the Login class and set it as visible
        Login login = new Login();
        login.setVisible(true);

        // Listen for the login window to be closed before showing the main application
        login.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosed(java.awt.event.WindowEvent e) {
                // Show the Map frame on the event dispatch thread
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        SearchPOI searchPOI2 = new SearchPOI("src\\poi.json");
                        JFrame frame = new JFrame("Western Navigator");
                        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
                        frame.addWindowListener(new WindowAdapter() {
                            public void windowClosing(WindowEvent e) {
                                int confirmed = JOptionPane.showConfirmDialog(frame, 
                                    "Are you sure you want to exit the program?", "Exit Program Message Box",
                                    JOptionPane.YES_NO_OPTION);

                                if (confirmed == JOptionPane.YES_OPTION) {
                                    frame.dispose();
                                }
                            }
                        });
                        
                        frame.setContentPane(new Map());
                        frame.setSize(1600, 1000);
                        frame.setVisible(true);
                        //printAllPOIs(searchPOI2.getAllPOIs());
                    }
                });

                // Dispose of the login frame after a successful login
                login.dispose();
            }
        });

        // Load the data on the event dispatch thread
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                SearchPOI searchPOI = new SearchPOI("src\\poi.json");
                //System.out.println(searchPOI.toString());
            }
        });
    }
    
    /**
     * Set JSon file for current user
     * 
     * @param file Current user's JSon file
     */
    public static void setPOIFile(String file) {
        poiFile = file;
    }
    
    /**
     * Get JSon file name for current user
     * 
     * @return Current poi file name
     */
    public String getPOIFile() {
        return "src\\poi.json";
    }
}