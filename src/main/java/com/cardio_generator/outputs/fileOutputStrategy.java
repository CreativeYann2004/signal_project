package com.cardio_generator.outputs;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Implements the OutputStrategy for writing output data to files.
 * This strategy dynamically creates files based on labels and writes formatted data to these files.
 */
public class FileOutputStrategy implements OutputStrategy {
    // Changed to camelCase
    private String baseDirectory; 
    // Changed 'file_map' to 'fileMap'
    public final ConcurrentHashMap<String, String> fileMap = new ConcurrentHashMap<>(); 

    /**
     * Constructs a new FileOutputStrategy with a specified base directory for the file output.
     *
     * @param baseDirectory is the directory in which the output files are created and saved
     */
    public FileOutputStrategy(String baseDirectory) {
        this.baseDirectory = baseDirectory;
    }

    /**
     * Outputs the data to a file specified by the label. If the file does not exist, it is created.
     *
     * @param patientId is the ID of the patient
     * @param timestamp is the timestamp of the data
     * @param label is the label that specifies the file to be written to
     * @param data is the data to be written
     */
    @Override
    public void output(int patientId, long timestamp, String label, String data) {
        try {
            // Create the directory
            Files.createDirectories(Paths.get(baseDirectory));
        } catch (IOException e) {
            System.err.println("Error creating base directory: " + e.getMessage());
            return;
        }

        // Set the filePath variable
        String filePath = fileMap.computeIfAbsent(label, k -> Paths.get(baseDirectory, label + ".txt").toString());

        // Write the data to the file
        try (PrintWriter out = new PrintWriter(
                Files.newBufferedWriter(Paths.get(filePath), StandardOpenOption.CREATE, StandardOpenOption.APPEND))) {
            out.printf("Patient ID: %d, Timestamp: %d, Label: %s, Data: %s%n", patientId, timestamp, label, data);
        } catch (Exception e) {
            System.err.println("Error writing to file " + filePath + ": " + e.getMessage());
        }
    }
}
