package com.alerts.strategy;

import com.data_management.Patient;
import com.data_management.DataStorage;

/**
 * Interface for alert strategies. Defines a method to check whether an alert condition is met for a given patient.
 */
public interface AlertStrategy {

    /**
     * Checks whether an alert condition is met for the specified patient using the provided data storage.
     *
     * @param patient     The patient whose data is being checked.
     * @param dataStorage The data storage containing the patient's records.
     * @return true if an alert condition is met, false otherwise.
     */
    boolean checkAlert(Patient patient, DataStorage dataStorage);
}
