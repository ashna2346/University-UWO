import javax.swing.*;

class EditPanel extends JFrame {
    public EditPanel() {
        setTitle("Edit");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.add(new JLabel("This is the Edit panel. Add any help content here."));
        add(panel);
    }
}