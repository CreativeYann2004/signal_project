package com.alerts.conditions;

import com.data_management.Patient;
import com.data_management.PatientRecord;
import com.alerts.Alert;
import java.util.List;
import java.util.stream.Collectors;

/**
 * This class evaluates blood pressure trends for a given patient.
 * It checks if there is a consistent increasing or decreasing trend
 * in blood pressure readings within a specified time window.
 */
public class BasicBloodPressureTrendEvaluator implements BloodPressureTrendEvaluator {

    private static final long WINDOW_SIZE_MS = 4 * 60 * 60 * 1000;

    /**
     * Evaluates the blood pressure trends of a patient over a specified time window.
     *
     * @param patient The patient whose blood pressure trends are to be evaluated.
     * @param now The current time in milliseconds.
     */
    @Override
    public void evaluateBloodPressureTrends(Patient patient, long now) {
        List<PatientRecord> bloodPressureRecords = patient.getRecords(now - WINDOW_SIZE_MS, now).stream()
                .filter(r -> "BloodPressure".equals(r.getRecordType()))
                .sorted((r1, r2) -> Long.compare(r1.getTimestamp(), r2.getTimestamp()))
                .collect(Collectors.toList());

        if (bloodPressureRecords.size() >= 3) {
            checkBloodPressureTrends(bloodPressureRecords, patient, now);
        }
    }

    /**
     * Checks for increasing or decreasing trends in blood pressure records.
     *
     * @param records The list of blood pressure records sorted by timestamp.
     * @param patient The patient whose blood pressure trends are being checked.
     * @param now The current time in milliseconds.
     */
    public void checkBloodPressureTrends(List<PatientRecord> records, Patient patient, long now) {
        boolean increasingTrend = true;
        boolean decreasingTrend = true;

        for (int i = 0; i < records.size() - 1; i++) {
            PatientRecord current = records.get(i);
            PatientRecord next = records.get(i + 1);

            if (!(next.getSystolicValue() > current.getSystolicValue())) {
                increasingTrend = false;
            }
            if (!(next.getSystolicValue() < current.getSystolicValue())) {
                decreasingTrend = false;
            }
        }

        if (increasingTrend) {
            new Alert(patient.getPatientId(), "Increasing blood pressure trend detected", now);
        }
        if (decreasingTrend) {
            new Alert(patient.getPatientId(), "Decreasing blood pressure trend detected", now);
        }
    }
}
