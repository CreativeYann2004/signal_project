package com.data_management;

import java.util.List;

/**
 * The PatientRecord class represents a single medical record for a patient.
 * It stores various types of medical data such as measurement values, blood pressure values, and ECG beat intervals.
 */
public class PatientRecord {
    private int patientId;
    private String recordType;
    private double measurementValue;
    private double systolicValue;
    private double diastolicValue;
    private List<Double> beatIntervals;
    private long timestamp;

    /**
     * Constructs a PatientRecord with the specified patient ID, measurement value, record type, and timestamp.
     *
     * @param patientId        The ID of the patient.
     * @param measurementValue The measurement value of the record.
     * @param recordType       The type of the record (e.g., HeartRate, BloodPressure).
     * @param timestamp        The timestamp of the record.
     */
    public PatientRecord(int patientId, double measurementValue, String recordType, long timestamp) {
        this.patientId = patientId;
        this.measurementValue = measurementValue;
        this.recordType = recordType;
        this.timestamp = timestamp;
    }

    /**
     * Constructs a PatientRecord with the specified patient ID, blood pressure values, record type, and timestamp.
     *
     * @param patientId    The ID of the patient.
     * @param systolicValue The systolic blood pressure value.
     * @param diastolicValue The diastolic blood pressure value.
     * @param recordType   The type of the record (e.g., HeartRate, BloodPressure).
     * @param timestamp    The timestamp of the record.
     */
    public PatientRecord(int patientId, double systolicValue, double diastolicValue, String recordType, long timestamp) {
        this.patientId = patientId;
        this.systolicValue = systolicValue;
        this.diastolicValue = diastolicValue;
        this.recordType = recordType;
        this.timestamp = timestamp;
    }

    /**
     * Gets the patient ID.
     *
     * @return The patient ID.
     */
    public int getPatientId() {
        return patientId;
    }

    /**
     * Gets the record type.
     *
     * @return The record type.
     */
    public String getRecordType() {
        return recordType;
    }

    /**
     * Gets the measurement value.
     *
     * @return The measurement value.
     */
    public double getMeasurementValue() {
        return measurementValue;
    }

    /**
     * Gets the systolic blood pressure value.
     *
     * @return The systolic blood pressure value.
     */
    public double getSystolicValue() {
        return systolicValue;
    }

    /**
     * Gets the diastolic blood pressure value.
     *
     * @return The diastolic blood pressure value.
     */
    public double getDiastolicValue() {
        return diastolicValue;
    }

    /**
     * Gets the timestamp of the record.
     *
     * @return The timestamp of the record.
     */
    public long getTimestamp() {
        return timestamp;
    }

    /**
     * Gets the list of ECG beat intervals.
     *
     * @return The list of ECG beat intervals.
     */
    public List<Double> getBeatIntervals() {
        return beatIntervals;
    }

    /**
     * Gets the heart rate value if the record type is HeartRate.
     *
     * @return The heart rate value.
     */
    public double getHeartRate() {
        if ("HeartRate".equals(recordType)) {
            return measurementValue;
        } else {
            throw new IllegalStateException("Record type is not HeartRate");
        }
    }
}
