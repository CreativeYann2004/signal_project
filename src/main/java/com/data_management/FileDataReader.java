package com.data_management;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Class for reading patient data from a file and storing it in DataStorage.
 */
public class FileDataReader implements DataReader {
    private String filePath;

    /**
     * Constructs a FileDataReader with the specified file path.
     *
     * @param filePath The path of the file to read data from.
     */
    public FileDataReader(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Reads data from the file and stores it in the specified data storage.
     *
     * @param dataStorage The data storage where the data will be stored.
     * @throws IOException If an I/O error occurs.
     */
    @Override
    public void readData(DataStorage dataStorage) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                parseAndStore(line, dataStorage);
            }
        }
    }

    /**
     * Unsupported operation for FileDataReader. Throws UnsupportedOperationException.
     *
     * @param dataStorage The data storage where the data would be stored.
     * @throws IOException                  If an I/O error occurs.
     * @throws UnsupportedOperationException Always thrown for this implementation.
     */
    @Override
    public void connectAndReadData(DataStorage dataStorage) throws IOException {
        throw new UnsupportedOperationException("FileDataReader does not support connectAndReadData");
    }

    /**
     * Parses a line of data and stores it in the specified data storage.
     *
     * @param line        The line of data to be parsed.
     * @param dataStorage The data storage where the parsed data will be stored.
     */
    public void parseAndStore(String line, DataStorage dataStorage) {
        String[] parts = line.split(",");
        int patientId = Integer.parseInt(parts[0].trim());
        double measurementValue = Double.parseDouble(parts[1].trim());
        String recordType = parts[2].trim();
        long timestamp = Long.parseLong(parts[3].trim());
        dataStorage.addPatientData(patientId, measurementValue, recordType, timestamp);
    }
}
