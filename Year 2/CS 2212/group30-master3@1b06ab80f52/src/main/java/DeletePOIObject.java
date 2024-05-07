/**
 * @author Team30
 * @version 1.0
 * 
 * class to delete a POI object from JSon file
 */

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import java.io.*;
import com.fasterxml.jackson.databind.node.ArrayNode;

public class DeletePOIObject extends JFrame{
    /**
     * Deletes POI object from current user's JSon file
     * 
     * @param parentFrame parent frame to display error message
     * @param poiId ID of POI to be deleted
     * @param poiFile name of JSon file of current user
     * @throws IOException if error opening JSon file
     */
    public DeletePOIObject(JFrame parentFrame, int poiId, String poiFile) throws IOException{ 
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            File jsonFile = new File("src\\poi.json");
            JsonNode rootNode = objectMapper.readTree(jsonFile);        //get rootNode of tree
            
            ArrayNode arrayNode = (ArrayNode) rootNode.get("points_of_interest");
            
            for (int i = 0; i < arrayNode.size(); i++) {        //iterate through tree, delete object when found
                JsonNode objectNode = arrayNode.get(i);
                if (objectNode.get("id").asInt() == poiId) {
                    //remove the object from the array
                    arrayNode.remove(i);
                    break;
                }
            }
            
            //write the changes to the JSON file
            objectMapper.writeValue(jsonFile, rootNode);
        } 
        catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(parentFrame, "Error editing POI: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
