package com.alerts.factory;

import com.alerts.Alert;
import com.data_management.Patient;
import com.data_management.PatientRecord;

/**
 * Factory class for creating heart rate alerts. Checks for abnormal heart rate levels and creates an alert if necessary.
 */
public class HeartRateAlertFactory extends AlertFactory {

    /**
     * Creates an alert for abnormal heart rate levels if the patient's record indicates such levels.
     *
     * @param patient   The patient for whom the alert is created.
     * @param record    The patient record that triggers the alert.
     * @param timestamp The time at which the alert is created.
     * @return The created alert, or null if no abnormal heart rate level is detected.
     */
    @Override
    public Alert createAlert(Patient patient, PatientRecord record, long timestamp) {
        if ("HeartRate".equals(record.getRecordType())) {
            double heartRate = record.getHeartRate();
            if (heartRate > 120 || heartRate < 50) {
                return new Alert(patient.getPatientId(), "Abnormal heart rate detected", timestamp);
            }
        }
        return null;
    }
}
