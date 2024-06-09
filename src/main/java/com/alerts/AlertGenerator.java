package com.alerts;

import com.alerts.factory.*;
import com.alerts.strategy.OxygenSaturationStrategy;
import com.alerts.conditions.*;
import com.cardio_generator.outputs.OutputStrategy;
import com.data_management.DataStorage;
import com.data_management.Patient;
import com.data_management.PatientRecord;

import java.util.List;

/**
 * Class responsible for generating alerts based on patient data.
 */
public class AlertGenerator {
    private DataStorage dataStorage;
    private OutputStrategy outputStrategy;
    private ConditionEvaluator conditionEvaluator;
    private CompositeHealthScoreCalculator healthScoreCalculator;
    private BloodPressureTrendEvaluator bloodPressureTrendEvaluator;

    private AlertFactory bloodPressureAlertFactory;
    private AlertFactory heartRateAlertFactory;
    private BloodOxygenAlertFactory bloodOxygenAlertFactory;
    private AlertFactory compositeHealthScoreAlertFactory;

    /**
     * Constructs an AlertGenerator with the specified dependencies.
     *
     * @param dataStorage                  The data storage containing patient records.
     * @param outputStrategy               The strategy for outputting alerts.
     * @param conditionEvaluator           The evaluator for general conditions.
     * @param healthScoreCalculator        The calculator for composite health scores.
     * @param bloodPressureTrendEvaluator  The evaluator for blood pressure trends.
     * @param bloodOxygenAlertFactory      The factory for creating blood oxygen alerts.
     */
    public AlertGenerator(DataStorage dataStorage, OutputStrategy outputStrategy,
                        ConditionEvaluator conditionEvaluator, CompositeHealthScoreCalculator healthScoreCalculator,
                        BloodPressureTrendEvaluator bloodPressureTrendEvaluator, BloodOxygenAlertFactory bloodOxygenAlertFactory) {
        this.dataStorage = dataStorage;
        this.outputStrategy = outputStrategy;
        this.conditionEvaluator = conditionEvaluator;
        this.healthScoreCalculator = healthScoreCalculator;
        this.bloodPressureTrendEvaluator = bloodPressureTrendEvaluator;
        this.bloodOxygenAlertFactory = bloodOxygenAlertFactory;

        this.bloodPressureAlertFactory = new BloodPressureAlertFactory();
        this.heartRateAlertFactory = new HeartRateAlertFactory();
        this.compositeHealthScoreAlertFactory = new CompositeHealthScoreAlertFactory(healthScoreCalculator);
    }

    /**
     * Evaluates the patient data to generate alerts if necessary.
     *
     * @param patient The patient whose data is being evaluated.
     */
    public void evaluateData(Patient patient) {
        long currentTime = System.currentTimeMillis();
        List<PatientRecord> recentRecords = dataStorage.getRecords(patient.getPatientId(), currentTime - 3600000, currentTime);

        for (PatientRecord record : recentRecords) {
            Alert alert = null;

            if ("BloodPressure".equals(record.getRecordType())) {
                alert = bloodPressureAlertFactory.createAlert(patient, record, currentTime);
            } else if ("HeartRate".equals(record.getRecordType())) {
                alert = heartRateAlertFactory.createAlert(patient, record, currentTime);
            } else if ("OxygenSaturation".equals(record.getRecordType())) {
                alert = bloodOxygenAlertFactory.createAlert(patient, record, currentTime);
            }

            if (alert != null) {
                outputStrategy.output(alert.getPatientId(), alert.getTimestamp(), "Alert", alert.getFormattedAlert());
            }
        }

        Alert compositeHealthScoreAlert = compositeHealthScoreAlertFactory.createAlert(patient, null, currentTime);
        if (compositeHealthScoreAlert != null) {
            outputStrategy.output(compositeHealthScoreAlert.getPatientId(), compositeHealthScoreAlert.getTimestamp(), "Alert", compositeHealthScoreAlert.getFormattedAlert());
        }

        bloodPressureTrendEvaluator.checkBloodPressureTrends(recentRecords, patient, currentTime);
    }
}
