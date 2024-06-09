package com.alerts.conditions;

import com.data_management.Patient;
import com.data_management.PatientRecord;
import com.alerts.Alert;
import com.cardio_generator.outputs.OutputStrategy;

import java.util.List;

/**
 * This class evaluates various medical conditions for a patient and triggers alerts based on specified criteria.
 * It uses an output strategy to handle the triggered alerts.
 */
public class BasicConditionEvaluator implements ConditionEvaluator {

    private final OutputStrategy outputStrategy;

    /**
     * Constructor to initialize BasicConditionEvaluator with an OutputStrategy.
     *
     * @param outputStrategy The strategy to handle the output of alerts.
     */
    public BasicConditionEvaluator(OutputStrategy outputStrategy) {
        this.outputStrategy = outputStrategy;
    }

    /**
     * Checks a patient's record for any condition that meets the alert criteria and triggers an alert if necessary.
     *
     * @param patient   The patient whose record is being checked.
     * @param record    The patient record to evaluate.
     * @param timestamp The current time in milliseconds.
     * @return true if an alert was triggered, false otherwise.
     */
    @Override
    public boolean checkAndTriggerAlert(Patient patient, PatientRecord record, long timestamp) {
        if (evaluateCondition(record)) {
            String alertMessage = getAlertMessage(record);
            triggerAlert(new Alert(patient.getPatientId(), alertMessage, timestamp));
            return true;
        }
        return false;
    }

    /**
     * Evaluates the condition based on the type of the patient record.
     *
     * @param record The patient record to evaluate.
     * @return true if the condition meets the alert criteria, false otherwise.
     */
    private boolean evaluateCondition(PatientRecord record) {
        switch (record.getRecordType()) {
            case "HeartRate":
                return record.getMeasurementValue() > 100 || record.getMeasurementValue() < 50;
            case "BloodPressure":
                return record.getSystolicValue() > 180 || record.getDiastolicValue() < 60;
            case "BloodOxygenSaturation":
                return record.getMeasurementValue() < 92;
            case "ECG":
                return isIrregular(record.getBeatIntervals());
            default:
                return false;
        }
    }

    /**
     * Checks if the ECG intervals are irregular based on their variance.
     *
     * @param intervals The list of beat intervals from the ECG.
     * @return true if the intervals are irregular, false otherwise.
     */
    private boolean isIrregular(List<Double> intervals) {
        if (intervals == null || intervals.isEmpty()) {
            return false;
        }
        double mean = intervals.stream().mapToDouble(Double::doubleValue).average().orElse(0);
        double variance = intervals.stream().mapToDouble(i -> Math.pow(i - mean, 2)).average().orElse(0);
        return Math.sqrt(variance) > 50;
    }

    /**
     * Checks for combined conditions over the past hour and triggers an alert if both conditions are met.
     *
     * @param patient The patient whose records are being checked.
     * @param now     The current time in milliseconds.
     * @return true if an alert was triggered, false otherwise.
     */
    @Override
    public boolean checkForCombinedConditions(Patient patient, long now) {
        List<PatientRecord> recentRecords = patient.getRecords(now - 3600000, now);

        boolean lowBloodPressure = recentRecords.stream()
                .anyMatch(r -> "BloodPressure".equals(r.getRecordType()) && r.getSystolicValue() < 90 && r.getDiastolicValue() < 60);
        boolean lowOxygenSaturation = recentRecords.stream()
                .anyMatch(r -> "BloodOxygenSaturation".equals(r.getRecordType()) && r.getMeasurementValue() < 92);

        if (lowBloodPressure && lowOxygenSaturation) {
            triggerAlert(new Alert(patient.getPatientId(), "Critical hypotensive and hypoxemia risk detected", now));
            return true;
        }
        return false;
    }

    /**
     * Generates an alert message based on the type of the patient record.
     *
     * @param record The patient record that triggered the alert.
     * @return The alert message.
     */
    private String getAlertMessage(PatientRecord record) {
        switch (record.getRecordType()) {
            case "HeartRate":
                return "Abnormal heart rate detected";
            case "BloodPressure":
                return "Critical blood pressure level detected";
            case "BloodOxygenSaturation":
                return "Low oxygen saturation detected";
            case "ECG":
                return "ECG irregularities detected";
            default:
                return "Unknown condition";
        }
    }

    /**
     * Triggers an alert using the output strategy.
     *
     * @param alert The alert to be triggered.
     */
    private void triggerAlert(Alert alert) {
        outputStrategy.output(alert.getPatientId(), alert.getTimestamp(), "Alert", alert.getFormattedAlert());
    }
}
