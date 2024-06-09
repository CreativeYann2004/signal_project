package com.cardio_generator.generators;

import java.util.Random;

import com.cardio_generator.outputs.OutputStrategy;

/**
 * This class generates blood pressure data for patients, including systolic and diastolic pressure values.
 * The data is generated around a baseline for each patient, with small variations for realism.
 */
public class BloodPressureDataGenerator implements PatientDataGenerator {
    private static final Random random = new Random();

    private int[] lastSystolicValues;
    private int[] lastDiastolicValues;

    /**
     * Constructor to initialize BloodPressureDataGenerator with a specified number of patients.
     *
     * @param patientCount The number of patients for which to generate baseline values.
     */
    public BloodPressureDataGenerator(int patientCount) {
        lastSystolicValues = new int[patientCount + 1];
        lastDiastolicValues = new int[patientCount + 1];

        for (int i = 1; i <= patientCount; i++) {
            lastSystolicValues[i] = 110 + random.nextInt(20); // Initial random baseline for systolic pressure
            lastDiastolicValues[i] = 70 + random.nextInt(15); // Initial random baseline for diastolic pressure
        }
    }

    /**
     * Generates blood pressure data for a specific patient and outputs the data using the provided strategy.
     *
     * @param patientId      The ID of the patient for whom to generate data.
     * @param outputStrategy The strategy to use for outputting the generated data.
     */
    @Override
    public void generate(int patientId, OutputStrategy outputStrategy) {
        try {
            int systolicVariation = random.nextInt(5) - 2; // Variation in systolic pressure
            int diastolicVariation = random.nextInt(5) - 2; // Variation in diastolic pressure

            int newSystolicValue = lastSystolicValues[patientId] + systolicVariation;
            int newDiastolicValue = lastDiastolicValues[patientId] + diastolicVariation;

            newSystolicValue = Math.min(Math.max(newSystolicValue, 90), 180);
            newDiastolicValue = Math.min(Math.max(newDiastolicValue, 60), 120);

            lastSystolicValues[patientId] = newSystolicValue;
            lastDiastolicValues[patientId] = newDiastolicValue;

            outputStrategy.output(patientId, System.currentTimeMillis(), "SystolicPressure", Double.toString(newSystolicValue));
            outputStrategy.output(patientId, System.currentTimeMillis(), "DiastolicPressure", Double.toString(newDiastolicValue));
        } catch (Exception e) {
            System.err.println("An error occurred while generating blood pressure data for patient " + patientId);
            e.printStackTrace();
        }
    }
}
