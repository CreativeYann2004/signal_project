package com.alerts.conditions;

import com.data_management.PatientRecord;
import java.util.List;

/**
 * This interface defines a method for calculating a composite health score
 * based on a list of patient records.
 */
public interface CompositeHealthScoreCalculator {
    
    /**
     * Calculates the composite health score based on a list of patient records.
     *
     * @param records The list of patient records to evaluate.
     * @return The composite health score.
     */
    double calculateCompositeHealthScore(List<PatientRecord> records);
}
