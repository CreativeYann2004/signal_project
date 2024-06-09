package com.alerts.conditions;

import com.data_management.Patient;
import com.data_management.PatientRecord;
import java.util.List;

/**
 * This interface defines methods to evaluate blood pressure trends in patients.
 */
public interface BloodPressureTrendEvaluator {
    
    /**
     * Evaluates the blood pressure trends of a patient over a specified time window.
     *
     * @param patient The patient whose blood pressure trends are to be evaluated.
     * @param now The current time in milliseconds.
     */
    void evaluateBloodPressureTrends(Patient patient, long now);
    
    /**
     * Checks for increasing or decreasing trends in blood pressure records.
     *
     * @param records The list of blood pressure records sorted by timestamp.
     * @param patient The patient whose blood pressure trends are being checked.
     * @param now The current time in milliseconds.
     */
    void checkBloodPressureTrends(List<PatientRecord> records, Patient patient, long now);
}
