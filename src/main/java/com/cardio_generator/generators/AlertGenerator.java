package com.cardio_generator.generators;

import java.util.Random;
import com.cardio_generator.outputs.OutputStrategy;

/**
 * This class generates alarms for patients based on simulation data within a healthcare monitoring system.
 * It manages the alarm conditions for each patient and determines when alarms should be triggered or resolved based on random probabilities.
 * The class interacts with a {@link OutputStrategy} to log these alarm conditions, making it a critical component
 * for the system's ability to dynamically respond to changes in patient status.
 * 
 * <p>Using this class involves creating an instance with a specific number of patients and periodically
 * Calling {@code generate} to evaluate and possibly update the alarm status for each patient.</p>
 */
public class AlertGenerator implements PatientDataGenerator {

    public static final Random randomGenerator = new Random();

    // Tracks whether there is an active alarm for each patient, where false means it is resolved and true means it has been triggered.
    //conform to camelCase naming conventions
    private boolean[] alertStates;

    /**
     * Initializes a new instance of {@code AlertGenerator} with the specified number of patients.
     * Each patient is initially set so that they have no active alarms.
     *
     * @param patientCount the number of patients to be monitored
     */
    public AlertGenerator(int patientCount) {
        alertStates = new boolean[patientCount + 1];
    }

    /**
     * Evaluates and possibly changes the alert status for a specific patient based on defined probability thresholds.
     * This method simulates the process of deciding whether to trigger or cancel an alert.
     * 
     * @param patientId the ID of the patient if it is a valid index in {@code alertStates}
     * @param outputStrategy the strategy for logging or transmitting the alert status
     * @throws IndexOutOfBoundsException if the patientId is invalid (less than 0 or greater than the number of patients treated)
     */
    @Override
    public void generate(int patientId, OutputStrategy outputStrategy) {
        try {
            if (alertStates[patientId]) {
                if (randomGenerator.nextDouble() < 0.9) { // 90% chance to resolve the alert
                    alertStates[patientId] = false;
                    // Output the alert
                    outputStrategy.output(patientId, System.currentTimeMillis(), "Alert", "resolved");
                }
            } else {
                //conform to camelCase naming conventions
                double lambda = 0.1; // Average rate (alerts per period), adjust based on desired frequency
                double p = -Math.expm1(-lambda); // Probability of at least one alert in the period.
                boolean alertTriggered = randomGenerator.nextDouble() < p;

                if (alertTriggered) {
                    alertStates[patientId] = true;
                    // Output the alert
                    outputStrategy.output(patientId, System.currentTimeMillis(), "Alert", "triggered");
                }
            }
        } catch (Exception e) {
            System.err.println("An error occurred while generating alert data for patient " + patientId);
            e.printStackTrace();
        }
    }
}