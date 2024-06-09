package com.alerts.conditions;

import com.data_management.Patient;
import com.data_management.PatientRecord;

/**
 * This interface defines methods for evaluating patient conditions and triggering alerts based on the evaluation.
 */
public interface ConditionEvaluator {

    /**
     * Checks a patient's record for any condition that meets the alert criteria and triggers an alert if necessary.
     *
     * @param patient   The patient whose record is being checked.
     * @param record    The patient record to evaluate.
     * @param timestamp The current time in milliseconds.
     * @return true if an alert was triggered, false otherwise.
     */
    boolean checkAndTriggerAlert(Patient patient, PatientRecord record, long timestamp);

    /**
     * Checks for combined conditions over the past hour and triggers an alert if both conditions are met.
     *
     * @param patient The patient whose records are being checked.
     * @param now     The current time in milliseconds.
     * @return true if an alert was triggered, false otherwise.
     */
    boolean checkForCombinedConditions(Patient patient, long now);
}
