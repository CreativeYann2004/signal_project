package com.data_management;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;
/**
 * This class provides functions for reading and processing data from files within a specific directory.
 * It implements the {@code DataReader} interface so that it can read, analyze and store data in a {@code DataStorage}.
 */
public class FileDataReader implements DataReader {
    private final String directoryPath;
    /**
     * Constructs a FileDataReader with a specified path to the directory containing data files.
     *
     * @param directoryPath the path to the directory where data files are stored
     */
    public FileDataReader(String directoryPath) {
        this.directoryPath = directoryPath;
    }
    /**
     * Reads data from all files in the specified directory and saves them in the specified DataStorage.
     * Each file should contain lines of data that are parsed and stored.
     * Files are processed as streams in order to process large amounts of data efficiently.
     *
     * @param dataStorage the storage where parsed data will be stored
     * @throws IOException if there is an error reading files from the directory
     */
    public void readData(DataStorage dataStorage) throws IOException {
        Files.walk(Paths.get(directoryPath))
            .filter(Files::isRegularFile)
            .forEach(file -> {
                try (Stream<String> lines = Files.lines(file)) {
                    lines.forEach(line -> parseAndStore(line, dataStorage));
                } catch (IOException e) {
                    System.err.println("Failed to read file: " + file + ", error: " + e.getMessage());
                }
            });
    }
    /**
     * Analyzes a single line of data and saves it in the data memory.
     * The line is expected to be in CSV format: Patient ID, measured value, record type, timestamp.
     * Each row of data is extracted, validated and converted to the appropriate type before it is saved.
     *
     * @param line the line of text to parse
     * @param dataStorage the storage where parsed data will be stored
     */
    private void parseAndStore(String line, DataStorage dataStorage) {
        String[] parts = line.split(",");
        if (parts.length == 4) {
            try {
                int patientId = Integer.parseInt(parts[0].trim());
                double measurementValue = Double.parseDouble(parts[1].trim());
                String recordType = parts[2].trim();
                long timestamp = Long.parseLong(parts[3].trim());
                dataStorage.addPatientData(patientId, measurementValue, recordType, timestamp);
            } catch (NumberFormatException ex) {
                System.err.println("Error parsing data: " + line);
            }
        }
    }
}
