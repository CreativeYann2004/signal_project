package com.alerts;

import java.util.List;

import com.data_management.DataStorage;
import com.data_management.Patient;
import com.data_management.PatientRecord;

/**
 * The {@code AlertGenerator} class is responsible for monitoring patient data
 * and generating alerts when certain predefined conditions are met. This class
 * relies on a {@link DataStorage} instance to access patient data and evaluate
 * it against specific health criteria.
 */
public class AlertGenerator {
    private DataStorage dataStorage;

    /**
     * Constructs an {@code AlertGenerator} with a specified {@code DataStorage}.
     * The {@code DataStorage} is used to retrieve patient data that this class
     * will monitor and evaluate.
     *
     * @param dataStorage the data storage system that provides access to patient
     *                    data
     */
    public AlertGenerator(DataStorage dataStorage) {
        this.dataStorage = dataStorage;
    }

    /**
 * Evaluates the specified patient's data to determine if any alert conditions are met.
 * If a condition is met, an alert is triggered via the {@link #triggerAlert} method.
 * This method should define the specific conditions under which an alert will be triggered.
 *
 * @param patient the patient data to evaluate for alert conditions
 */
public void evaluateData(Patient patient) {
    long now = System.currentTimeMillis();
    List<PatientRecord> recentRecords = patient.getRecords(now - 3600000, now); // last hour records
    for (PatientRecord record : recentRecords) {
        if ("HeartRate".equals(record.getRecordType())) {
            if (record.getMeasurementValue() > 100 || record.getMeasurementValue() < 60) {
                triggerAlert(new Alert(Integer.toString(patient.getPatientId()), 
                                    record.getMeasurementValue() > 100 ? "High heart rate" : "Low heart rate", 
                                    record.getTimestamp()));
            }
        } else if ("BloodPressure".equals(record.getRecordType())) {
            if (record.getMeasurementValue() > 140 || record.getMeasurementValue() < 90) {
                triggerAlert(new Alert(Integer.toString(patient.getPatientId()), 
                                    record.getMeasurementValue() > 140 ? "High blood pressure" : "Low blood pressure", 
                                    record.getTimestamp()));
            }
        }
    }
}

    /**
     * Triggers an alert for the monitoring system. This method can be extended to
     * notify medical staff, log the alert, or perform other actions. The method
     * currently assumes that the alert information is fully formed when passed as
     * an argument.
     *
     * @param alert the alert object containing details about the alert condition
     */
    private void triggerAlert(Alert alert) {
        // Here we could extend functionality to notify medical staff or log the alert
        System.out.println("Alert triggered for Patient ID: " + alert.getPatientId() +
                        " Condition: " + alert.getCondition() +
                        " Timestamp: " + alert.getTimestamp());
}
