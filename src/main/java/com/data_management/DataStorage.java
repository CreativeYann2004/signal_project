package com.data_management;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Arrays;

public class DataStorage {
    private Map<Integer, Patient> patientMap;
    private static final List<String> VALID_RECORD_TYPES = Arrays.asList("HeartRate", "BloodPressure", "BloodOxygenSaturation");

    public DataStorage() {
        this.patientMap = new HashMap<>();
    }

    public synchronized void addPatientData(int patientId, double measurementValue, String recordType, long timestamp) {
        if (Double.isNaN(measurementValue) || Double.isInfinite(measurementValue)) {
            throw new NumberFormatException("Measurement value is not a valid number: " + measurementValue);
        }
        
        if (!VALID_RECORD_TYPES.contains(recordType)) {
            throw new IllegalArgumentException("Invalid record type: " + recordType);
        }

        Patient patient = patientMap.get(patientId);
        if (patient == null) {
            patient = new Patient(patientId);
            patientMap.put(patientId, patient);
        }
        patient.addRecord(measurementValue, recordType, timestamp);
    }

    public List<PatientRecord> getRecords(int patientId, long startTime, long endTime) {
        Patient patient = patientMap.get(patientId);
        if (patient != null) {
            return patient.getRecords(startTime, endTime);
        }
        return new ArrayList<>();
    }

    public List<Patient> getAllPatients() {
        return new ArrayList<>(patientMap.values());
    }

    public static void main(String[] args) {
        System.out.println("DataStorage main method executed.");
    }
}
