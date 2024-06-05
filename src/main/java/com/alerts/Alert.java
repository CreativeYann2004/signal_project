package com.alerts;

public class Alert {
    private int patientId;
    private String condition;
    private long timestamp;

    public Alert(int patientId, String condition, long timestamp) {
        this.patientId = patientId;
        this.condition = condition;
        this.timestamp = timestamp;
    }

    public int getPatientId() {
        return patientId;
    }

    public String getCondition() {
        return condition;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public String getFormattedAlert() {
        return "Alert for Patient ID: " + patientId +
            " | Condition: " + condition +
            " | Timestamp: " + formatTimestamp(timestamp);
    }

    private String formatTimestamp(long timestamp) {
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        java.util.Date date = new java.util.Date(timestamp);
        return sdf.format(date);
    }

    @Override
    public String toString() {
        return getFormattedAlert();
    }
}
