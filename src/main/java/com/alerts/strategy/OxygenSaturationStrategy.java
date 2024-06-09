package com.alerts.strategy;

import com.data_management.DataStorage;
import com.data_management.Patient;
import com.data_management.PatientRecord;

import java.util.List;

/**
 * Strategy class for checking blood oxygen saturation alert conditions.
 * Determines if any blood oxygen saturation records within the last hour indicate low levels.
 */
public class OxygenSaturationStrategy {
    private DataStorage dataStorage;

    /**
     * Constructs an OxygenSaturationStrategy with the specified data storage.
     *
     * @param dataStorage The data storage containing patient records.
     */
    public OxygenSaturationStrategy(DataStorage dataStorage) {
        this.dataStorage = dataStorage;
    }

    /**
     * Checks whether an alert condition is met for the specified patient based on their blood oxygen saturation records in the last hour.
     *
     * @param patient The patient whose data is being checked.
     * @param record  The patient record that triggers the alert check.
     * @return true if a low blood oxygen saturation level is detected, false otherwise.
     */
    public boolean checkAlert(Patient patient, PatientRecord record) {
        List<PatientRecord> records = dataStorage.getRecords(patient.getPatientId(), System.currentTimeMillis() - 3600000, System.currentTimeMillis());
        for (PatientRecord r : records) {
            if ("BloodOxygenSaturation".equals(r.getRecordType())) {
                if (r.getMeasurementValue() < 90) {
                    return true;
                }
            }
        }
        return false;
    }
}
