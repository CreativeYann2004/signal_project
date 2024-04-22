package com.cardio_generator.generators;

import java.util.Random;

import com.cardio_generator.outputs.OutputStrategy;
/**
 * Generates simulated blood saturation data for patients within a health monitoring system.
 * This class is responsible for simulating changes in blood saturation values over time, with a history
 * of the last recorded value to ensure realistic fluctuations.
 * 
 * <p>It ensures that saturation values remain within a healthy range and provides updated values to an output strategy.
 *for further processing or logging</p>.
 */
public class BloodSaturationDataGenerator implements PatientDataGenerator {
    private static final Random random = new Random();
    private int[] lastSaturationValues;
    
    /**
     * Constructs a new BloodSaturationDataGenerator for a specified number of patients.
     * Initializes the baseline saturation values for each patient between 95 and 100.
     *
     * @param patientCount the number of patients to be monitored, must be non-negative
     */

    public BloodSaturationDataGenerator(int patientCount) {
        lastSaturationValues = new int[patientCount + 1];

        // Initialize with baseline saturation values for each patient
        for (int i = 1; i <= patientCount; i++) {
            lastSaturationValues[i] = 95 + random.nextInt(6); // Initializes with a value between 95 and 100
        }
    }
    /**
     * Generates and updates the blood saturation data for a specific patient. The method simulates small fluctuations
     * in saturation and ensures that the value remains within a healthy and realistic range (90% to 100%).
     *
     * @param patientId the ID of the patient to be updated
     * @param outputStrategy the output strategy to which the generated data should be sent
     */
    @Override
    public void generate(int patientId, OutputStrategy outputStrategy) {
        try {
            // Simulate blood saturation values
            int variation = random.nextInt(3) - 1; // -1, 0, or 1 to simulate small fluctuations
            int newSaturationValue = lastSaturationValues[patientId] + variation;

            // Ensure the saturation stays within a realistic and healthy range
            newSaturationValue = Math.min(Math.max(newSaturationValue, 90), 100);
            lastSaturationValues[patientId] = newSaturationValue;
            outputStrategy.output(patientId, System.currentTimeMillis(), "Saturation",
                    Double.toString(newSaturationValue) + "%");
        } catch (Exception e) {
            System.err.println("An error occurred while generating blood saturation data for patient " + patientId);
            e.printStackTrace(); // This will print the stack trace to help identify where the error occurred.
        }
    }
}
