# Welcome to Team 30’s Navigator App

Navigating a University can be a challenging task, especially for new students or visitors. Our desktop app leverages the maps made available by Western to helps the users to search and explore the interiors of the Campus buildings. The users could then look for rooms in the buildings, find points of interests, explore the offered maps, and create and save their own unique points of interests. The application also includes an editing tool that enables developers to create and edit map metadata for the application. Overall, this applications aims to enable the users to explore the interior structure of the Campus buildings of Western University with ease. 

The desktop app was developed in Java using Swing UI components. 
The libraries that we used for JSON object parsing include: 
	•	Jackson Databind : For handling JSON data
	•	Jackson Annotations: For providing control over how JSON data is mapped to and from Java objects
    
Our UI was developed and optimized to run on Windows 10. It will also run on MacOS. However, the UI may not look the same and could produce unpredictable behaviour.

BUILD

To build our desktop app, clone our Team’s Bitbucket repository onto your local system:
git clone https://repo.csd.uwo.ca/scm/compsci2212_w2023/group30.git

Then,
If you want to run the application in test mode put in:
java -jar target\group30-1.0-SNAPSHOT-jar-with-dependencies.jar test

If you don’t want to run the application in test mode put in:
java -jar target\group30-1.0-SNAPSHOT-jar-with-dependencies.jar 

The dependencies our app requires are the libraries listed above and are included in the .jar file. The programs that are required to build our desktop app are git, and maven. The admin access code for modifying built-in POI is admin@123. The multi user login credentials are listed in the users.json file starting from username: user1 and password: pass123. 

DOCUMENTATION
Please checkout our target folder that contains JavaDocs of all the classes we have created.