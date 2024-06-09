package com.alerts;

/**
 * This class represents an alert that is triggered based on certain patient conditions.
 */
public class Alert {
    private int patientId;
    private String condition;
    private long timestamp;

    /**
     * Constructor to initialize an Alert with patient ID, condition, and timestamp.
     *
     * @param patientId The ID of the patient.
     * @param condition The condition that triggered the alert.
     * @param timestamp The time when the alert was triggered.
     */
    public Alert(int patientId, String condition, long timestamp) {
        this.patientId = patientId;
        this.condition = condition;
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
     * Gets the condition that triggered the alert.
     *
     * @return The condition.
     */
    public String getCondition() {
        return condition;
    }

    /**
     * Gets the timestamp when the alert was triggered.
     *
     * @return The timestamp.
     */
    public long getTimestamp() {
        return timestamp;
    }

    /**
     * Formats the alert into a human-readable string.
     *
     * @return The formatted alert string.
     */
    public String getFormattedAlert() {
        return "Alert for Patient ID: " + patientId +
            " | Condition: " + condition +
            " | Timestamp: " + formatTimestamp(timestamp);
    }

    /**
     * Formats the timestamp into a human-readable date and time string.
     *
     * @param timestamp The timestamp to format.
     * @return The formatted date and time string.
     */
    private String formatTimestamp(long timestamp) {
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        java.util.Date date = new java.util.Date(timestamp);
        return sdf.format(date);
    }

    /**
     * Returns a string representation of the alert.
     *
     * @return The string representation of the alert.
     */
    @Override
    public String toString() {
        return getFormattedAlert();
    }
}
