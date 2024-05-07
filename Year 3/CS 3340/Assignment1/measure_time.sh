#!/bin/bash
# Student Name: Ashna Mittal
# Student ID: 251206758
# Compile the Java programs

echo "Current user: $(whoami)"
echo "Current date: $(date)"
echo "Current directory: $(pwd)"

javac asn1_a.java
javac asn1_b.java

echo "Measuring time for the recursive Lucas number computation:"
time java asn1_a

echo "Measuring time for the efficient Lucas number computation using matrix exponentiation:"
time java asn1_b

