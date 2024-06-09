package com.alerts.strategy;

import com.data_management.Patient;
import com.data_management.DataStorage;
import com.data_management.PatientRecord;

import java.util.List;

/**
 * Strategy class for checking blood pressure alert conditions. 
 * Determines if any blood pressure records within the last hour indicate a critical level.
 */
public class BloodPressureStrategy implements AlertStrategy {

    /**
     * Checks whether an alert condition is met for the specified patient based on their blood pressure records in the last hour.
     *
     * @param patient     The patient whose data is being checked.
     * @param dataStorage The data storage containing the patient's records.
     * @return true if a critical blood pressure level is detected, false otherwise.
     */
    @Override
    public boolean checkAlert(Patient patient, DataStorage dataStorage) {
        List<PatientRecord> records = dataStorage.getRecords(patient.getPatientId(), System.currentTimeMillis() - 3600000, System.currentTimeMillis());
        for (PatientRecord record : records) {
            if ("BloodPressure".equals(record.getRecordType())) {
                if (record.getSystolicValue() > 180 || record.getDiastolicValue() < 60) {
                    return true;
                }
            }
        }
        return false;
    }
}
