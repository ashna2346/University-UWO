/*
import javax.swing.*;
import java.awt.*;

public class POIPanel extends JFrame {
    private PointOfInterest poi;

    public POIPanel(PointOfInterest poi) {
        setTitle(poi.getName());
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new BorderLayout());

        JLabel nameLabel = new JLabel(poi.getName(), SwingConstants.CENTER);
        nameLabel.setFont(new Font("Arial", Font.BOLD, 20));
        panel.add(nameLabel, BorderLayout.NORTH);

        JTextArea infoArea = new JTextArea();
        infoArea.setFont(new Font("Arial", Font.PLAIN, 12));
        infoArea.setEditable(false);
        infoArea.setLineWrap(true);
        infoArea.setWrapStyleWord(true);
        infoArea.setMargin(new Insets(5, 5, 5, 5));
        infoArea.setText("Building: " + poi.getBuilding()
                + "\nFloor: " + poi.getFloor()
                + "\nRoom Number: " + poi.getRoom_number()
                + "\nDescription: " + poi.getDescription()
                + "\nType: " + poi.getType());

        JScrollPane scrollPane = new JScrollPane(infoArea);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setPreferredSize(new Dimension(380, 200));

        panel.add(scrollPane, BorderLayout.CENTER);
        add(panel);
    }
}
*/
