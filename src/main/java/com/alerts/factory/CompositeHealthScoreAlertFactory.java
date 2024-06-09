package com.alerts.factory;

import com.data_management.Patient;
import com.data_management.PatientRecord;
import com.alerts.Alert;
import com.alerts.conditions.CompositeHealthScoreCalculator;

import java.util.List;

/**
 * Factory class for creating composite health score alerts. Uses a composite health score calculator to determine when an alert should be created.
 */
public class CompositeHealthScoreAlertFactory extends AlertFactory {

    private final CompositeHealthScoreCalculator healthScoreCalculator;

    /**
     * Constructs a CompositeHealthScoreAlertFactory with the specified composite health score calculator.
     *
     * @param healthScoreCalculator The calculator used to compute the composite health score.
     */
    public CompositeHealthScoreAlertFactory(CompositeHealthScoreCalculator healthScoreCalculator) {
        this.healthScoreCalculator = healthScoreCalculator;
    }

    /**
     * Creates an alert if the composite health score indicates potential distress.
     *
     * @param patient   The patient for whom the alert is created.
     * @param record    The patient record that triggers the alert.
     * @param timestamp The time at which the alert is created.
     * @return The created alert, or null if the health score does not indicate potential distress.
     */
    @Override
    public Alert createAlert(Patient patient, PatientRecord record, long timestamp) {
        List<PatientRecord> recentRecords = patient.getRecords(timestamp - 3600000, timestamp);
        double healthScore = healthScoreCalculator.calculateCompositeHealthScore(recentRecords);

        if (healthScore >= 0.5) {
            return new Alert(patient.getPatientId(), "Health score indicates potential distress", timestamp);
        }
        return null;
    }
}
