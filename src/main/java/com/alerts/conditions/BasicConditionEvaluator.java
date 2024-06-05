package com.alerts.conditions;

import com.data_management.Patient;
import com.data_management.PatientRecord;
import com.alerts.Alert;
import com.cardio_generator.outputs.OutputStrategy;

import java.util.List;

public class BasicConditionEvaluator implements ConditionEvaluator {

    private final OutputStrategy outputStrategy;

    public BasicConditionEvaluator(OutputStrategy outputStrategy) {
        this.outputStrategy = outputStrategy;
    }

    @Override
    public boolean checkAndTriggerAlert(Patient patient, PatientRecord record, long timestamp) {
        if (evaluateCondition(record)) {
            String alertMessage = getAlertMessage(record);
            triggerAlert(new Alert(patient.getPatientId(), alertMessage, timestamp));
            return true;
        }
        return false;
    }

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

    private boolean isIrregular(List<Double> intervals) {
        if (intervals == null || intervals.isEmpty()) {
            return false;
        }
        double mean = intervals.stream().mapToDouble(Double::doubleValue).average().orElse(0);
        double variance = intervals.stream().mapToDouble(i -> Math.pow(i - mean, 2)).average().orElse(0);
        return Math.sqrt(variance) > 50;
    }

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

    private void triggerAlert(Alert alert) {
        outputStrategy.output(alert.getPatientId(), alert.getTimestamp(), "Alert", alert.getFormattedAlert());
    }
}
