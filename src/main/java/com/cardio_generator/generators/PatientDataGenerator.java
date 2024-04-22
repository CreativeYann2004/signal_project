package com.cardio_generator.generators;

import com.cardio_generator.outputs.OutputStrategy;
/**
 * Interface for generating patient data. This interface defines a method for generating data
 * for a specific patient ID using a specific output strategy.
 */
public interface PatientDataGenerator {
     /**
     * Generates data for the specified patient ID and sends it to the specified output strategy.
     * @param patientId The unique identifier for the patient for whom data is to be generated.
     * @param outputStrategy The output strategy to which the generated data should be sent.
     */
    void generate(int patientId, OutputStrategy outputStrategy);
}
