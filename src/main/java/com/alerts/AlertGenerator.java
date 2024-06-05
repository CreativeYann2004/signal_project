package com.alerts;

import com.alerts.conditions.BloodPressureTrendEvaluator;
import com.alerts.conditions.CompositeHealthScoreCalculator;
import com.alerts.conditions.ConditionEvaluator;
import com.cardio_generator.outputs.OutputStrategy;
import com.data_management.DataStorage;
import com.data_management.Patient;
import com.data_management.PatientRecord;

import java.util.List;

public class AlertGenerator {
    private DataStorage dataStorage;
    private OutputStrategy outputStrategy;
    private ConditionEvaluator conditionEvaluator;
    private CompositeHealthScoreCalculator healthScoreCalculator;
    private BloodPressureTrendEvaluator bloodPressureTrendEvaluator;

    public AlertGenerator(DataStorage dataStorage, OutputStrategy outputStrategy,
                        ConditionEvaluator conditionEvaluator, CompositeHealthScoreCalculator healthScoreCalculator,
                        BloodPressureTrendEvaluator bloodPressureTrendEvaluator) {
        this.dataStorage = dataStorage;
        this.outputStrategy = outputStrategy;
        this.conditionEvaluator = conditionEvaluator;
        this.healthScoreCalculator = healthScoreCalculator;
        this.bloodPressureTrendEvaluator = bloodPressureTrendEvaluator;
    }

    public void evaluateData(Patient patient) {
        long currentTime = System.currentTimeMillis();
        List<PatientRecord> recentRecords = dataStorage.getRecords(patient.getPatientId(), currentTime - 3600000, currentTime);

        double healthScore = healthScoreCalculator.calculateCompositeHealthScore(recentRecords);

        if (healthScore >= 0.5) {
            outputStrategy.output(patient.getPatientId(), currentTime, "Alert", 
                "Alert for Patient ID: " + patient.getPatientId() + " | Condition: Health score indicates potential distress | Timestamp: " + currentTime);
        }

        for (PatientRecord record : recentRecords) {
            if (conditionEvaluator.checkAndTriggerAlert(patient, record, currentTime)) {
                outputStrategy.output(patient.getPatientId(), currentTime, "Alert", 
                    "Alert for Patient ID: " + patient.getPatientId() + " | Condition: Specific condition detected | Timestamp: " + currentTime);
            }
        }

        if (conditionEvaluator.checkForCombinedConditions(patient, currentTime)) {
            outputStrategy.output(patient.getPatientId(), currentTime, "Alert", 
                "Alert for Patient ID: " + patient.getPatientId() + " | Condition: Combined conditions detected | Timestamp: " + currentTime);
        }

        bloodPressureTrendEvaluator.checkBloodPressureTrends(recentRecords, patient, currentTime);
    }
}
