package com.cardio_generator.generators;

import java.util.Random;

import com.cardio_generator.outputs.OutputStrategy;

/**
 * This class generates blood level data for patients, including cholesterol,
 * white blood cells, and red blood cells.
 * The data is generated around a baseline for each patient for realism.
 */
public class BloodLevelsDataGenerator implements PatientDataGenerator {
    private static final Random random = new Random();
    private final double[] baselineCholesterol;
    private final double[] baselineWhiteCells;
    private final double[] baselineRedCells;

    /**
     * Constructor to initialize BloodLevelsDataGenerator with a specified number of patients.
     *
     * @param patientCount The number of patients for which to generate baseline values.
     */
    public BloodLevelsDataGenerator(int patientCount) {
        baselineCholesterol = new double[patientCount + 1];
        baselineWhiteCells = new double[patientCount + 1];
        baselineRedCells = new double[patientCount + 1];
        for (int i = 1; i <= patientCount; i++) {
            baselineCholesterol[i] = 150 + random.nextDouble() * 50;
            baselineWhiteCells[i] = 4 + random.nextDouble() * 6;
            baselineRedCells[i] = 4.5 + random.nextDouble() * 1.5;
        }
    }

    /**
     * Generates blood level data for a specific patient and outputs the data using the provided strategy.
     *
     * @param patientId      The ID of the patient for whom to generate data.
     * @param outputStrategy The strategy to use for outputting the generated data.
     */
    @Override
    public void generate(int patientId, OutputStrategy outputStrategy) {
        try {
            double cholesterol = baselineCholesterol[patientId] + (random.nextDouble() - 0.5) * 10;
            double whiteCells = baselineWhiteCells[patientId] + (random.nextDouble() - 0.5) * 1;
            double redCells = baselineRedCells[patientId] + (random.nextDouble() - 0.5) * 0.2;
            outputStrategy.output(patientId, System.currentTimeMillis(), "Cholesterol", Double.toString(cholesterol));
            outputStrategy.output(patientId, System.currentTimeMillis(), "WhiteBloodCells", Double.toString(whiteCells));
            outputStrategy.output(patientId, System.currentTimeMillis(), "RedBloodCells", Double.toString(redCells));
        } catch (Exception e) {
            System.err.println("An error occurred while generating blood levels data for patient " + patientId);
            e.printStackTrace();
        }
    }
}
