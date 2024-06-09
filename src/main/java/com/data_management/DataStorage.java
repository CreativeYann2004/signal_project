package com.data_management;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Arrays;

/**
 * Singleton class for managing patient data storage. Provides methods to add, clear, and retrieve patient data.
 */
public class DataStorage {
    private Map<Integer, Patient> patientMap;
    private static final List<String> VALID_RECORD_TYPES = Arrays.asList("HeartRate", "BloodPressure", "BloodOxygenSaturation");
    private static DataStorage instance;

    private DataStorage() {
        this.patientMap = new HashMap<>();
    }

    /**
     * Returns the singleton instance of the DataStorage.
     *
     * @return The singleton instance of DataStorage.
     */
    public static synchronized DataStorage getInstance() {
        if (instance == null) {
            instance = new DataStorage();
        }
        return instance;
    }

    /**
     * Clears all patient data from the storage.
     */
    public synchronized void clear() {
        patientMap.clear();
    }

    /**
     * Adds patient data to the storage.
     *
     * @param patientId         The ID of the patient.
     * @param measurementValue  The measurement value to be added.
     * @param recordType        The type of the record (e.g., HeartRate, BloodPressure).
     * @param timestamp         The timestamp of the record.
     */
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

    /**
     * Retrieves a list of patient records for the specified patient and time range.
     *
     * @param patientId The ID of the patient.
     * @param startTime The start time of the range.
     * @param endTime   The end time of the range.
     * @return A list of patient records within the specified time range.
     */
    public synchronized List<PatientRecord> getRecords(int patientId, long startTime, long endTime) {
        Patient patient = patientMap.get(patientId);
        if (patient != null) {
            return patient.getRecords(startTime, endTime);
        }
        return new ArrayList<>();
    }

    /**
     * Retrieves a list of all patients in the storage.
     *
     * @return A list of all patients.
     */
    public synchronized List<Patient> getAllPatients() {
        return new ArrayList<>(patientMap.values());
    }

    /**
     * The main method for testing the DataStorage class.
     *
     * @param args Command line arguments.
     */
    public static void main(String[] args) {
        DataStorage dataStorage = DataStorage.getInstance();
        System.out.println("DataStorage main method executed.");
    }
}
