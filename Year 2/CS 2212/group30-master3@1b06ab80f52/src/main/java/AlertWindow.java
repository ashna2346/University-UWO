/**
 * @author Team30
 * @version 1.0
 * 
 * Create new alert window when incorrect admin code entered
 */

import javax.swing.*;

public class AlertWindow extends JDialog {
    private JLabel incorrectCodeLabel;

    public AlertWindow() {
        super((JFrame)null, "Operation failed!", true);
        setSize(300, 200);

        incorrectCodeLabel = new JLabel("Incorrect authentication code! Please try again.");
        add(incorrectCodeLabel);
        
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
