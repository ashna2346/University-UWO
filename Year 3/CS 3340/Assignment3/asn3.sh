#!/bin/bash
# Student Name: Ashna Mittal
# Student ID: 251206758

echo "Current user: $(whoami)"
echo "Current date: $(date)"
echo "Current directory: $(pwd)"

# Compile the Java program
javac HeapADT.java Heap.java Edge.java prims.java

# Run the compiled Java program
java prims
