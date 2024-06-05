package com.data_management;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Patient {
    private int patientId;
    private List<PatientRecord> patientRecords;

    public Patient(int patientId) {
        this.patientId = patientId;
        this.patientRecords = new ArrayList<>();
    }

    public void addRecord(double measurementValue, String recordType, long timestamp) {
        PatientRecord record = new PatientRecord(this.patientId, measurementValue, recordType, timestamp);
        this.patientRecords.add(record);
    }

    public List<PatientRecord> getRecords(long startTime, long endTime) {
        return patientRecords.stream()
                            .filter(record -> record.getTimestamp() >= startTime && record.getTimestamp() <= endTime)
                            .collect(Collectors.toList());
    }

    public int getPatientId() {
        return patientId;
    }
}
