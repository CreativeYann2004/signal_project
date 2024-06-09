package com.alerts.conditions;

import com.data_management.PatientRecord;
import java.util.List;

/**
 * This class calculates a composite health score based on a list of patient records.
 * The score is determined by calculating the deviation of each record's values from standard healthy values.
 */
public class BasicHealthScoreCalculator implements CompositeHealthScoreCalculator {

    /**
     * Calculates a composite health score based on patient records.
     *
     * @param records The list of patient records to be evaluated.
     * @return The composite health score.
     */
    @Override
    public double calculateCompositeHealthScore(List<PatientRecord> records) {
        double score = 0;
        int count = 0;
        for (PatientRecord record : records) {
            double deviation = 0;
            switch (record.getRecordType()) {
                case "HeartRate":
                    deviation = Math.abs(record.getMeasurementValue() - 75) / 75.0;
                    break;
                case "BloodPressure":
                    deviation = (Math.abs(record.getSystolicValue() - 120) / 120.0
                            + Math.abs(record.getDiastolicValue() - 80) / 80.0) / 2.0;
                    break;
                case "BloodOxygenSaturation":
                    deviation = Math.abs(record.getMeasurementValue() - 95) / 95.0;
                    break;
                default:
                    break;
            }
            score += deviation;
            count++;
        }
        return count > 0 ? score / count : 0;
    }
}
