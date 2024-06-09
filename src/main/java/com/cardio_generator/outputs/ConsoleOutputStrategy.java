package com.cardio_generator.outputs;

/**
 * This class implements the OutputStrategy interface to provide a console output strategy.
 * It outputs patient data to the console in a formatted manner.
 */
public class ConsoleOutputStrategy implements OutputStrategy {
    
    /**
     * Outputs patient data to the console.
     *
     * @param patientId The ID of the patient.
     * @param timestamp The timestamp of the data.
     * @param label     The label describing the type of data.
     * @param data      The actual data value.
     */
    @Override
    public void output(int patientId, long timestamp, String label, String data) {
        System.out.printf("Patient ID: %d, Timestamp: %d, Label: %s, Data: %s%n", patientId, timestamp, label, data);
    }
}
