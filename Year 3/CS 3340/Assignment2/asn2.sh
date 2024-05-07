#!/bin/bash
# Student Name: Ashna Mittal
# Student ID: 251206758

echo "Current user: $(whoami)"
echo "Current date: $(date)"
echo "Current directory: $(pwd)"

# Compile the Java program
javac ConnectedComponents.java
javac uandf.java

# Run the compiled Java program
java ConnectedComponents
