package com.alerts.conditions;

import com.data_management.PatientRecord;
import java.util.List;

public class BasicHealthScoreCalculator implements CompositeHealthScoreCalculator {

    @Override
    public double calculateCompositeHealthScore(List<PatientRecord> records) {
        double score = 0;
        int count = 0;
        for (PatientRecord record : records) {
            double deviation = 0;
            switch (record.getRecordType()) {
                case "HeartRate":
                    deviation = Math.abs(record.getMeasurementValue() - 75) / 75;
                    break;
                case "BloodPressure":
                    deviation = Math.abs(record.getSystolicValue() - 120) / 120;
                    deviation += Math.abs(record.getDiastolicValue() - 80) / 80;
                    deviation /= 2;
                    break;
                case "BloodOxygenSaturation":
                    deviation = Math.abs(record.getMeasurementValue() - 95) / 95;
                    break;
            }
            score += deviation;
            count++;
        }
        return count > 0 ? score / count : 0;
    }
}
