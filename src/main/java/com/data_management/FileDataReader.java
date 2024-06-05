package com.data_management;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.charset.StandardCharsets;
import java.util.stream.Stream;

public class FileDataReader implements DataReader {
    private final String directoryPath;

    public FileDataReader(String directoryPath) {
        this.directoryPath = directoryPath;
    }

    @Override
    public void readData(DataStorage dataStorage) throws IOException {
        Files.walk(Paths.get(directoryPath))
            .filter(Files::isRegularFile)
            .forEach(file -> {
                try (Stream<String> lines = Files.lines(file, StandardCharsets.UTF_8)) {
                    lines.forEach(line -> parseAndStore(line, dataStorage));
                } catch (IOException e) {
                    System.err.println("Failed to read file: " + file + ", error: " + e.getMessage());
                }
            });
    }

    public void parseAndStore(String line, DataStorage dataStorage) {
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
