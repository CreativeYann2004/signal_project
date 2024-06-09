package com.cardio_generator.generators;

import java.util.Random;
import com.cardio_generator.outputs.OutputStrategy;

/**
 * This class generates alerts for patients based on simulation data within a healthcare monitoring system.
 * It manages the alert conditions for each patient and determines when alerts should be triggered or resolved based on random probabilities.
 * The class interacts with an {@link OutputStrategy} to log these alert conditions, making it a critical component
 * for the system's ability to dynamically respond to changes in patient status.
 * 
 * <p>Using this class involves creating an instance with a specific number of patients and periodically
 * calling {@code generate} to evaluate and possibly update the alert status for each patient.</p>
 */
public class AlertGenerator implements PatientDataGenerator {

    private static final Random RANDOM_GENERATOR = new Random();
    private static final double RESOLVE_PROBABILITY = 0.9;
    private static final double ALERT_RATE = 0.1;

    private boolean[] alertStates;

    /**
     * Initializes a new instance of {@code AlertGenerator} with the specified number of patients.
     * Each patient is initially set so that they have no active alerts.
     *
     * @param patientCount the number of patients to be monitored
     */
    public AlertGenerator(int patientCount) {
        if (patientCount < 0) {
            throw new IllegalArgumentException("Patient count must be non-negative.");
        }
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
        if (patientId < 0 || patientId >= alertStates.length) {
            throw new IndexOutOfBoundsException("Invalid patient ID: " + patientId);
        }

        if (alertStates[patientId]) {
            if (RANDOM_GENERATOR.nextDouble() < RESOLVE_PROBABILITY) { // 90% chance to resolve the alert
                alertStates[patientId] = false;
                outputStrategy.output(patientId, System.currentTimeMillis(), "Alert", "resolved");
            }
        } else {
            double probabilityOfAlert = -Math.expm1(-ALERT_RATE); // Probability of at least one alert in the period.
            if (RANDOM_GENERATOR.nextDouble() < probabilityOfAlert) {
                alertStates[patientId] = true;
                outputStrategy.output(patientId, System.currentTimeMillis(), "Alert", "triggered");
            }
        }
    }
}
