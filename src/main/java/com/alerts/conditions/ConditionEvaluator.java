package com.alerts.conditions;

import com.data_management.Patient;
import com.data_management.PatientRecord;
import com.alerts.Alert;

public interface ConditionEvaluator {
    boolean checkAndTriggerAlert(Patient patient, PatientRecord record, long timestamp);
    boolean checkForCombinedConditions(Patient patient, long now);
}
