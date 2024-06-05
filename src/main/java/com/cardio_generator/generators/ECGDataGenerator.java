package com.cardio_generator.generators;

import java.util.Random;
import com.cardio_generator.outputs.OutputStrategy;

public class ECGDataGenerator implements PatientDataGenerator {
    private static final Random random = new Random();
    private double[] lastEcgValues;
    private double[] lastPhases;
    private static final double PI = Math.PI;
    private static final double TWO_PI = 2 * PI;

    public ECGDataGenerator(int patientCount) {
        lastEcgValues = new double[patientCount + 1];
        lastPhases = new double[patientCount + 1];
        for (int i = 1; i <= patientCount; i++) {
            lastEcgValues[i] = 0;
            lastPhases[i] = 0;
        }
    }

    @Override
    public void generate(int patientId, OutputStrategy outputStrategy) {
        try {
            double ecgValue = simulateEcgWaveform(patientId, lastEcgValues[patientId], lastPhases[patientId]);
            outputStrategy.output(patientId, System.currentTimeMillis(), "ECG", Double.toString(ecgValue));
            lastEcgValues[patientId] = ecgValue;
        } catch (Exception e) {
            System.err.println("An error occurred while generating ECG data for patient " + patientId);
            e.printStackTrace();
        }
    }

    public double simulateEcgWaveform(int patientId, double lastEcgValue, double lastPhase) {
        double hr = 60.0 + random.nextGaussian() * 5.0;
        double t = System.currentTimeMillis() / 1000.0;
        double ecgFrequency = hr / 60.0;
        double phase = (lastPhase + ecgFrequency * TWO_PI / 1000) % TWO_PI;
        lastPhases[patientId] = phase;
        double pWave = 0.1 * Math.sin(phase);
        double qrsComplex = simulateQrsComplex(phase);
        double tWave = 0.2 * Math.sin(2 * phase + PI / 4);
        return pWave + qrsComplex + tWave + simulateNoise();
    }

    public double simulateQrsComplex(double phase) {
        double qrsWidth = 0.05;
        double qrsAmplitude = 1.5;
        double qrsPhase = PI / 2;
        return qrsAmplitude * Math.exp(-Math.pow((phase - qrsPhase) / qrsWidth, 2));
    }

    public double simulateNoise() {
        double baselineWander = 0.01 * Math.sin(0.1 * TWO_PI * System.currentTimeMillis() / 1000.0);
        double powerlineInterference = 0.02 * Math.sin(50 * TWO_PI * System.currentTimeMillis() / 1000.0);
        double muscleNoise = random.nextGaussian() * 0.05;
        return baselineWander + powerlineInterference + muscleNoise;
    }
}
