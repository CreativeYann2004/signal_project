package com.cardio_generator.outputs;

/**
 * Defines the strategy for the output of the generated patient data. 
 * Implementations of this interface determine how and where the data for each patient 
 * into a console, file or other medium.
 */

public interface OutputStrategy {
    /**
     * Outputs the generated data for a patient at a specific point in time.
     * This method should be implemented in order to format and send the data 
     * according to the specific requirements of the output medium.
     *
     * @param patientId The identifier for the patient whose data is being output.
     * @param timestamp The timestamp at which the data was generated, typically as a UNIX timestamp.
     * @param label A label that describes the type of data to be output (e.g. "heart rate", "blood pressure").
     * @param data The actual data to be output, formatted as a string.
     */
    void output(int patientId, long timestamp, String label, String data);
}
