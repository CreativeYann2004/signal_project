package com.cardio_generator;

import com.data_management.DataStorage;
import com.cardio_generator.HealthDataSimulator;

/**
 * The Main class provides a way to run different classes based on command-line parameters.
 */
public class Main {

    /**
     * The main method is the entry point of the application.
     * It selects which class to run based on command-line parameters.
     *
     * @param args The command-line arguments. It should contain the name of the class to run.
     *             Valid options are "DataStorage" or "HealthDataSimulator".
     */
    public static void main(String[] args) {
        // Check if command-line arguments are provided
        if (args.length > 0) {
            // Check if the first argument is "DataStorage"
            if (args[0].equals("DataStorage")) {
                // If yes, run the DataStorage class
                DataStorage.main(new String[]{});
            } 
            // Check if the first argument is "HealthDataSimulator"
            else if (args[0].equals("HealthDataSimulator")) {
                // If yes, run the HealthDataSimulator class
                HealthDataSimulator.main(new String[]{});
            } 
            // If the argument is neither "DataStorage" nor "HealthDataSimulator"
            else {
                // Display usage instructions
                System.out.println("Usage: java Main [DataStorage|HealthDataSimulator]");
            }
        } 
        // If no command-line argument is provided
        else {
            // Display usage instructions
            System.out.println("Usage: java Main [DataStorage|HealthDataSimulator]");
        }
    }
}
