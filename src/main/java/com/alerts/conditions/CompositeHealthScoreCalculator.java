package com.alerts.conditions;

import com.data_management.PatientRecord;
import java.util.List;

public interface CompositeHealthScoreCalculator {
    double calculateCompositeHealthScore(List<PatientRecord> records);
}
