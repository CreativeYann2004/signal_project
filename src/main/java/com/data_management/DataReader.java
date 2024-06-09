package com.data_management;

import java.io.IOException;
import java.net.URISyntaxException;

/**
 * Interface for data readers. Provides methods to read data and to connect and read data from a data source.
 */
public interface DataReader {

    /**
     * Reads data into the specified data storage.
     *
     * @param dataStorage The data storage where the data will be read into.
     * @throws IOException If an I/O error occurs.
     */
    void readData(DataStorage dataStorage) throws IOException;

    /**
     * Connects to a data source and reads data into the specified data storage.
     *
     * @param dataStorage The data storage where the data will be read into.
     * @throws IOException If an I/O error occurs.
     * @throws URISyntaxException If the URI syntax is incorrect.
     */
    void connectAndReadData(DataStorage dataStorage) throws IOException, URISyntaxException;
}
