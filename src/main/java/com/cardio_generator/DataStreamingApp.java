package com.cardio_generator;

import com.alerts.AlertGenerator;
import com.alerts.conditions.BasicConditionEvaluator;
import com.alerts.conditions.BasicHealthScoreCalculator;
import com.alerts.conditions.BasicBloodPressureTrendEvaluator;
import com.alerts.factory.BloodOxygenAlertFactory;
import com.alerts.strategy.OxygenSaturationStrategy;
import com.data_management.DataStorage;
import com.data_management.Patient;
import com.cardio_generator.outputs.ConsoleOutputStrategy;
import com.cardio_generator.outputs.OutputStrategy;

import java.io.IOException;

/**
 * Main class for the Data Streaming Application. Sets up the necessary components and evaluates patient data to generate alerts.
 */
public class DataStreamingApp {

    /**
     * The main method that runs the Data Streaming Application.
     *
     * @param args Command line arguments.
     * @throws IOException If an input or output exception occurs.
     */
    public static void main(String[] args) throws IOException {
        // Use the singleton instance of DataStorage
        DataStorage dataStorage = DataStorage.getInstance();
        OutputStrategy outputStrategy = new ConsoleOutputStrategy(); // Use the concrete implementation

        // Pass the outputStrategy to the BasicConditionEvaluator
        BasicConditionEvaluator conditionEvaluator = new BasicConditionEvaluator(outputStrategy);
        BasicHealthScoreCalculator healthScoreCalculator = new BasicHealthScoreCalculator();
        BasicBloodPressureTrendEvaluator bloodPressureTrendEvaluator = new BasicBloodPressureTrendEvaluator();
        OxygenSaturationStrategy oxygenSaturationStrategy = new OxygenSaturationStrategy(dataStorage);
        BloodOxygenAlertFactory bloodOxygenAlertFactory = new BloodOxygenAlertFactory(oxygenSaturationStrategy);

        AlertGenerator alertGenerator = new AlertGenerator(
            dataStorage, outputStrategy, conditionEvaluator, healthScoreCalculator, bloodPressureTrendEvaluator, bloodOxygenAlertFactory
        );

        // Create a patient with ID 12345
        Patient patient = new Patient(12345);
        alertGenerator.evaluateData(patient);
    }
}
