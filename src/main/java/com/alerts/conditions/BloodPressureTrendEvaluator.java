package com.alerts.conditions;

import com.data_management.Patient;
import com.data_management.PatientRecord;
import java.util.List;

public interface BloodPressureTrendEvaluator {
    void evaluateBloodPressureTrends(Patient patient, long now);
    void checkBloodPressureTrends(List<PatientRecord> records, Patient patient, long now);  // Add this method
}
