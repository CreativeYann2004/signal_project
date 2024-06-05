package com.data_management;

import java.util.List;

public class PatientRecord {
    private int patientId;
    private String recordType;
    private double measurementValue;
    private double systolicValue;
    private double diastolicValue;
    private List<Double> beatIntervals;
    private long timestamp;

    public PatientRecord(int patientId, double measurementValue, String recordType, long timestamp) {
        this.patientId = patientId;
        this.measurementValue = measurementValue;
        this.recordType = recordType;
        this.timestamp = timestamp;
    }

    public PatientRecord(int patientId, double systolicValue, double diastolicValue, String recordType, long timestamp) {
        this.patientId = patientId;
        this.systolicValue = systolicValue;
        this.diastolicValue = diastolicValue;
        this.recordType = recordType;
        this.timestamp = timestamp;
    }

    public int getPatientId() {
        return patientId;
    }
    
    public String getRecordType() {
        return recordType;
    }

    public double getMeasurementValue() {
        return measurementValue;
    }

    public double getSystolicValue() {
        return systolicValue;
    }

    public double getDiastolicValue() {
        return diastolicValue;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public List<Double> getBeatIntervals() {
        return beatIntervals;
    }
}
