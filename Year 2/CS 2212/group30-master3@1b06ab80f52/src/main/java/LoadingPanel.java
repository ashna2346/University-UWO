/*
import javax.swing.*;
import java.awt.*;

public class LoadingPanel extends JPanel {
    private static final long serialVersionUID = 1L;
    private static LoadingPanel instance;
    private JLabel label;

    public LoadingPanel() {
        super(new BorderLayout());

        // Add a label to the center of the panel to show loading text
        label = new JLabel("Loading...");
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setVerticalAlignment(SwingConstants.CENTER);
        add(label, BorderLayout.CENTER);

        // Set the size of the panel to 200x100
        setPreferredSize(new Dimension(200, 100));

        // Set the background color of the panel to white
        setBackground(Color.WHITE);

        // Set the border of the panel
        setBorder(BorderFactory.createLineBorder(Color.GRAY));
    }

    // Method to get the singleton instance of the LoadingPanel
    public static LoadingPanel getInstance() {
        if (instance == null) {
            instance = new LoadingPanel();
        }
        return instance;
    }

    // Method to show the loading panel
    public void showLoading(Component parent) {
        // Create a new JDialog to display the panel
        JDialog dialog = new JDialog((Frame) null, false);
        dialog.setUndecorated(true);
        dialog.getContentPane().add(this);
        dialog.pack();
        dialog.setLocationRelativeTo(parent);
        dialog.setVisible(true);
    }

    // Method to hide the loading panel
    public void hideLoading() {
        // Get the parent JDialog of the panel and dispose of it
        Container parent = getParent();
        if (parent instanceof JDialog) {
            JDialog dialog = (JDialog) parent;
            dialog.dispose();
        }
    }
}
*/