/**
 * 
 * Student Name: Ashna Mittal
 * Student ID: 251206758
 */

import java.io.*;
import java.util.*;

public class ConnectedComponents {
    // Define the eight possible directions from any pixel (for 8-connectedness)
    private static final int[][] DIRECTIONS = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}, {-1, -1}, {-1, 1}, {1, -1}, {1, 1}};

    public static void main(String[] args) throws IOException {
        // Path to the input file containing the image representation
        String fPath = "infile.txt";
        // Read the image file and store each line as a String in a List
        List<String> lines = readImageFile(fPath);
        // Determine the number of rows and columns in the image
        int rows = lines.size();
        int cols = lines.get(0).length();
        // Initialise a disjoint set to manage connected components
        uandf ds = new uandf(rows * cols);

        // First pass: Identify connected components and unify them in the disjoint set
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                // Check if the current pixel is part of a component ('+')
                if (lines.get(i).charAt(j) == '+') {
                    // Calculate a unique ID for the current pixel based on its position
                    int current = i * cols + j;
                    // Check all neighbouring pixels to find connections
                    for (int[] direction : DIRECTIONS) {
                        int ni = i + direction[0], nj = j + direction[1];
                        // Ensure the neighbouring pixel is within bounds and also part of a component
                        if (ni >= 0 && ni < rows && nj >= 0 && nj < cols && lines.get(ni).charAt(nj) == '+') {
                            int neighbor = ni * cols + nj;
                            // Unify the current pixel's component with its neighbor's component
                            ds.union_sets(current, neighbor);
                        }
                    }
                }
            }
        }

        // Second pass: Assign labels to each component
        char[][] labeledImage = new char[rows][cols];
        Map<Integer, Character> componentLabels = new HashMap<>();
        int currentLabel = 0; // To assign unique labels starting from 'a'
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                // Only label '+' pixels
                if (lines.get(i).charAt(j) == '+') {
                    int component = ds.find_set(i * cols + j);
                    // Assign a new label if this component doesn't have one yet
                    if (!componentLabels.containsKey(component)) {
                        componentLabels.put(component, (char) ('a' + currentLabel++));
                    }
                    // Label the pixel with its component's label
                    labeledImage[i][j] = componentLabels.get(component);
                } else {
                    // Mark background pixels distinctly
                    labeledImage[i][j] = ' ';
                }
            }
        }
        
        // Create a binary image representation for printing
        int[][] binaryImage = new int[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                // Convert '+' to 1 and any other character to 0
                binaryImage[i][j] = lines.get(i).charAt(j) == '+' ? 1 : 0;
            }
        }

        // Print the original binary image
        System.out.println("1) The input binary image:");
        printBinaryImage(binaryImage);

        // Print the labeled image showing connected components
        System.out.println("\n2) The connected component image:");
        printLabeledImage(labeledImage);

        // Calculate and print sizes and labels of components, sorted by size
        System.out.println("\n3) A list sorted by component size:");
        Map<Character, Integer> labelSizes = printComponentSizes(ds, rows, cols, componentLabels);

        // Print images filtered to show components larger than a certain size
        System.out.println("\n4) Connected components greater than size 2:");
        printFilteredLabeledImage(labeledImage, labelSizes, 3);
        System.out.println("\n5) Connected components greater than size 11:");
        printFilteredLabeledImage(labeledImage, labelSizes, 12);
    }

    // Utility method to print the binary image
    private static void printBinaryImage(int[][] binaryImage) {
        for (int[] row : binaryImage) {
            for (int val : row) {
                System.out.print(val);
            }
            System.out.println();
        }
    }

    // Reads the image file line by line and returns a List of Strings
    private static List<String> readImageFile(String filePath) throws IOException {
        List<String> lines = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                lines.add(line);
            }
        }
        return lines;
    }

    // Calculates and prints the sizes and labels of the components
    private static Map<Character, Integer> printComponentSizes(uandf ds, int rows, int cols, Map<Integer, Character> componentLabels) {
        Map<Integer, Integer> componentSizes = new HashMap<>();
        // Calculate the size of each component
        for (int i = 0; i < rows * cols; i++) {
            int root = ds.find_set(i);
            componentSizes.merge(root, 1, Integer::sum);
        }

        Map<Character, Integer> labelSizes = new HashMap<>();
        // Map each component's size to its label
        for (Map.Entry<Integer, Character> entry : componentLabels.entrySet()) {
            labelSizes.put(entry.getValue(), componentSizes.get(entry.getKey()));
        }

        // Sort and print the component sizes and labels
        labelSizes.entrySet().stream()
                .sorted(Map.Entry.<Character, Integer>comparingByValue().reversed())
                .forEach(e -> System.out.println("Size: " + e.getValue() + ", Label: " + e.getKey()));

        return labelSizes;
    }

    // Prints the labelled image
    private static void printLabeledImage(char[][] labeledImage) {
        for (char[] row : labeledImage) {
            for (char label : row) {
                System.out.print(label == ' ' ? ' ' : label);
            }
            System.out.println();
        }
    }

    // Prints a version of the labelled image filtered by component size
    private static void printFilteredLabeledImage(char[][] labeledImage, Map<Character, Integer> labelSizes, int threshold) {
        for (int i = 0; i < labeledImage.length; i++) {
            for (int j = 0; j < labeledImage[i].length; j++) {
                // Print the label if the component size meets the threshold, otherwise print a space
                if (labeledImage[i][j] == ' ' || labelSizes.get(labeledImage[i][j]) >= threshold) {
                    System.out.print(labeledImage[i][j]);
                } else {
                    System.out.print(' ');
                }
            }
            System.out.println();
        }
    }
}
