package com.alerts.factory;

import com.alerts.Alert;
import com.data_management.Patient;
import com.data_management.PatientRecord;

/**
 * Abstract factory class for creating alerts. Subclasses should implement the method to create specific types of alerts.
 */
public abstract class AlertFactory {

    /**
     * Creates an alert based on the patient information, patient record, and timestamp.
     *
     * @param patient    The patient for whom the alert is created.
     * @param record     The patient record that triggers the alert.
     * @param timestamp  The time at which the alert is created.
     * @return The created alert.
     */
    public abstract Alert createAlert(Patient patient, PatientRecord record, long timestamp);
}
