package com.cardio_generator;

import com.cardio_generator.outputs.WebSocketOutputStrategy;
import com.data_management.DataStorage;
import com.data_management.WebSocketDataReader;
import com.data_management.FileDataReader;
import java.io.IOException;
import java.net.URISyntaxException;

/**
 * Main class for running various components of the Cardio Generator application.
 * Supports running different components based on the provided command-line arguments.
 */
public class Main {

    /**
     * The main method that runs various components of the Cardio Generator application.
     *
     * @param args Command line arguments to specify which component to run.
     */
    public static void main(String[] args) {
        if (args.length > 0) {
            switch (args[0]) {
                case "DataStorage":
                    DataStorage.getInstance().main(new String[]{});
                    break;
                case "HealthDataSimulator":
                    HealthDataSimulator.main(new String[]{});
                    break;
                case "WebSocketDataReader":
                    if (args.length > 1) {
                        String serverUri = args[1];
                        DataStorage dataStorage = DataStorage.getInstance();
                        try {
                            WebSocketDataReader dataReader = new WebSocketDataReader(serverUri);
                            dataReader.connectAndReadData(dataStorage);
                        } catch (URISyntaxException e) {
                            e.printStackTrace();
                        }
                    } else {
                        System.out.println("Usage: java Main WebSocketDataReader <serverUri>");
                    }
                    break;
                case "FileDataReader":
                    if (args.length > 1) {
                        String filePath = args[1];
                        DataStorage dataStorage = DataStorage.getInstance();
                        FileDataReader dataReader = new FileDataReader(filePath);

                        try {
                            dataReader.readData(dataStorage);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else {
                        System.out.println("Usage: java Main FileDataReader <filePath>");
                    }
                    break;
                case "WebSocketOutput":
                    WebSocketOutputStrategy server = new WebSocketOutputStrategy(8080);
                    server.output(1, System.currentTimeMillis(), "HeartRate", "80");
                    break;
                default:
                    System.out.println("Usage: java Main [DataStorage|HealthDataSimulator|WebSocketDataReader|FileDataReader|WebSocketOutput]");
                    break;
            }
        } else {
            System.out.println("Usage: java Main [DataStorage|HealthDataSimulator|WebSocketDataReader|FileDataReader|WebSocketOutput]");
        }
    }
}
