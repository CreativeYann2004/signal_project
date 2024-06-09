package com.alerts.factory;

import com.alerts.Alert;
import com.data_management.Patient;
import com.data_management.PatientRecord;

/**
 * Factory class for creating blood pressure alerts. Checks for critical blood pressure levels and creates an alert if necessary.
 */
public class BloodPressureAlertFactory extends AlertFactory {

    /**
     * Creates an alert for critical blood pressure levels if the patient's record indicates such levels.
     *
     * @param patient   The patient for whom the alert is created.
     * @param record    The patient record that triggers the alert.
     * @param timestamp The time at which the alert is created.
     * @return The created alert, or null if no critical blood pressure level is detected.
     */
    @Override
    public Alert createAlert(Patient patient, PatientRecord record, long timestamp) {
        if ("BloodPressure".equals(record.getRecordType())) {
            if (record.getSystolicValue() > 180 || record.getDiastolicValue() < 60) {
                return new Alert(patient.getPatientId(), "Critical blood pressure level detected", timestamp);
            }
        }
        return null;
    }
}
