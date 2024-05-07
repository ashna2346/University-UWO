/**
 * @author Team30
 * @version 1.0
 * 
 * Class representing user object
 */

public class User {
    private String username;
    private String password;
    private String jsonFile;
    
    /**
     * Default constructor
     */
    public User() {
    }

    /**
     * Constructor for user object
     * 
     * @param username username for current user
     * @param password password for current user
     * @param jsonFile JSon file corresponding to current user
     */
    public User(String username, String password, String jsonFile) {
        this.username = username;
        this.password = password;
        this.jsonFile = jsonFile;
    }
    
    /**
     * Get username
     * 
     * @return Username
     */
    public String getUsername() {
        return username;
    }
    
    /**
     * Set username
     * 
     * @param username Username of current user
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Get password
     * 
     * @return Password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Set password
     * 
     * @param password Password of current user
     */
    public void setPassword(String password) {
        this.password = password;
    }
    
    /**
     * Get JSon file corresponding to current user
     * 
     * @return JSon file corresponding to current user
     */
    public String getJsonFile() {
        return jsonFile;
    }

    /**
     * Set JSon file corresponding to current user
     * 
     * @param jsonFile JSon file corresponding to current user
     */
    public void setJsonFile(String jsonFile) {
        this.jsonFile = jsonFile;
    }
}
