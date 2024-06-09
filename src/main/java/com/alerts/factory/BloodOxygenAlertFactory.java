package com.alerts.factory;

import com.alerts.Alert;
import com.data_management.Patient;
import com.data_management.PatientRecord;
import com.alerts.strategy.OxygenSaturationStrategy;

/**
 * Factory class for creating blood oxygen alerts. Uses an oxygen saturation strategy to determine when an alert should be created.
 */
public class BloodOxygenAlertFactory extends AlertFactory {
    private OxygenSaturationStrategy oxygenSaturationStrategy;

    /**
     * Constructs a BloodOxygenAlertFactory with the specified oxygen saturation strategy.
     *
     * @param oxygenSaturationStrategy The strategy used to check for low blood oxygen saturation.
     */
    public BloodOxygenAlertFactory(OxygenSaturationStrategy oxygenSaturationStrategy) {
        this.oxygenSaturationStrategy = oxygenSaturationStrategy;
    }

    /**
     * Creates an alert for low blood oxygen saturation if the strategy check passes.
     *
     * @param patient   The patient for whom the alert is created.
     * @param record    The patient record that triggers the alert.
     * @param timestamp The time at which the alert is created.
     * @return The created alert, or null if the strategy check does not pass.
     */
    @Override
    public Alert createAlert(Patient patient, PatientRecord record, long timestamp) {
        if (oxygenSaturationStrategy.checkAlert(patient, record)) {
            return new Alert(patient.getPatientId(), "Low blood oxygen saturation detected", timestamp);
        }
        return null;
    }
}
