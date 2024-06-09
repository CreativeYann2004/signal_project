package com.alerts.strategy;

import com.data_management.Patient;
import com.data_management.DataStorage;
import com.data_management.PatientRecord;

import java.util.List;

/**
 * Strategy class for checking heart rate alert conditions.
 * Determines if any heart rate records within the last hour indicate abnormal levels.
 */
public class HeartRateStrategy implements AlertStrategy {

    /**
     * Checks whether an alert condition is met for the specified patient based on their heart rate records in the last hour.
     *
     * @param patient     The patient whose data is being checked.
     * @param dataStorage The data storage containing the patient's records.
     * @return true if an abnormal heart rate level is detected, false otherwise.
     */
    @Override
    public boolean checkAlert(Patient patient, DataStorage dataStorage) {
        List<PatientRecord> records = dataStorage.getRecords(patient.getPatientId(), System.currentTimeMillis() - 3600000, System.currentTimeMillis());
        for (PatientRecord record : records) {
            if ("HeartRate".equals(record.getRecordType())) {
                if (record.getMeasurementValue() > 120 || record.getMeasurementValue() < 50) {
                    return true;
                }
            }
        }
        return false;
    }
}
