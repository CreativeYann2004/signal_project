package com.data_management;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The Patient class represents a patient and their associated medical records.
 * It allows adding new records and retrieving records within a specific time range.
 */
public class Patient {
    private int patientId;
    private List<PatientRecord> patientRecords;

    /**
     * Constructs a Patient with the specified patient ID.
     *
     * @param patientId The ID of the patient.
     */
    public Patient(int patientId) {
        this.patientId = patientId;
        this.patientRecords = new ArrayList<>();
    }

    /**
     * Adds a new medical record for the patient.
     *
     * @param measurementValue The measurement value of the record.
     * @param recordType       The type of the record (e.g., HeartRate, BloodPressure).
     * @param timestamp        The timestamp of the record.
     */
    public void addRecord(double measurementValue, String recordType, long timestamp) {
        PatientRecord record = new PatientRecord(this.patientId, measurementValue, recordType, timestamp);
        this.patientRecords.add(record);
    }

    /**
     * Retrieves the medical records for the patient within the specified time range.
     *
     * @param startTime The start time of the time range.
     * @param endTime   The end time of the time range.
     * @return A list of patient records within the specified time range.
     */
    public List<PatientRecord> getRecords(long startTime, long endTime) {
        return patientRecords.stream()
                            .filter(record -> record.getTimestamp() >= startTime && record.getTimestamp() <= endTime)
                            .collect(Collectors.toList());
    }

    /**
     * Gets the ID of the patient.
     *
     * @return The patient ID.
     */
    public int getPatientId() {
        return patientId;
    }
}
