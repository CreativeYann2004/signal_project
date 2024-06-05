package data_management;

import com.alerts.AlertGenerator;
import com.alerts.conditions.BloodPressureTrendEvaluator;
import com.alerts.conditions.CompositeHealthScoreCalculator;
import com.alerts.conditions.ConditionEvaluator;
import com.cardio_generator.outputs.OutputStrategy;
import com.data_management.DataStorage;
import com.data_management.Patient;
import com.data_management.PatientRecord;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class AlertGeneratorTest {

    private DataStorage dataStorage;
    private OutputStrategy outputStrategy;
    private ConditionEvaluator conditionEvaluator;
    private CompositeHealthScoreCalculator healthScoreCalculator;
    private BloodPressureTrendEvaluator bloodPressureTrendEvaluator;
    private AlertGenerator alertGenerator;

    @BeforeEach
    public void setUp() {
        dataStorage = mock(DataStorage.class);
        outputStrategy = mock(OutputStrategy.class);
        conditionEvaluator = mock(ConditionEvaluator.class);
        healthScoreCalculator = mock(CompositeHealthScoreCalculator.class);
        bloodPressureTrendEvaluator = mock(BloodPressureTrendEvaluator.class);

        alertGenerator = new AlertGenerator(dataStorage, outputStrategy, conditionEvaluator, healthScoreCalculator, bloodPressureTrendEvaluator);
    }

    @Test
    public void testEvaluateData_NoAlertTriggered() {
        Patient patient = new Patient(1);
        List<PatientRecord> recentRecords = new ArrayList<>();
        when(dataStorage.getRecords(anyInt(), anyLong(), anyLong())).thenReturn(recentRecords);
        when(healthScoreCalculator.calculateCompositeHealthScore(recentRecords)).thenReturn(0.4);
        when(conditionEvaluator.checkAndTriggerAlert(any(Patient.class), any(PatientRecord.class), anyLong())).thenReturn(false);
        when(conditionEvaluator.checkForCombinedConditions(any(Patient.class), anyLong())).thenReturn(false);

        alertGenerator.evaluateData(patient);

        verify(outputStrategy, never()).output(anyInt(), anyLong(), anyString(), anyString());
    }

    @Test
    public void testEvaluateData_HealthScoreAlertTriggered() {
        Patient patient = new Patient(1);
        List<PatientRecord> recentRecords = new ArrayList<>();
        when(dataStorage.getRecords(anyInt(), anyLong(), anyLong())).thenReturn(recentRecords);
        when(healthScoreCalculator.calculateCompositeHealthScore(recentRecords)).thenReturn(0.6);
        when(conditionEvaluator.checkAndTriggerAlert(any(Patient.class), any(PatientRecord.class), anyLong())).thenReturn(false);
        when(conditionEvaluator.checkForCombinedConditions(any(Patient.class), anyLong())).thenReturn(false);

        alertGenerator.evaluateData(patient);

        ArgumentCaptor<Integer> patientIdCaptor = ArgumentCaptor.forClass(Integer.class);
        ArgumentCaptor<Long> timestampCaptor = ArgumentCaptor.forClass(Long.class);
        ArgumentCaptor<String> labelCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> dataCaptor = ArgumentCaptor.forClass(String.class);
        verify(outputStrategy).output(patientIdCaptor.capture(), timestampCaptor.capture(), labelCaptor.capture(), dataCaptor.capture());

        assertEquals(1, patientIdCaptor.getValue());
        assertEquals("Alert", labelCaptor.getValue());

        String actualOutput = dataCaptor.getValue();
        String actualTimestamp = actualOutput.split("\\| Timestamp: ")[1];
        String expectedOutput = "Alert for Patient ID: 1 | Condition: Health score indicates potential distress | Timestamp: " + actualTimestamp;
        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    public void testEvaluateData_ConditionAlertTriggered() {
        Patient patient = new Patient(1);
        long now = System.currentTimeMillis();
        PatientRecord record = new PatientRecord(1, 85, "HeartRate", now);
        List<PatientRecord> recentRecords = Arrays.asList(record);
        when(dataStorage.getRecords(anyInt(), anyLong(), anyLong())).thenReturn(recentRecords);
        when(conditionEvaluator.checkAndTriggerAlert(any(Patient.class), any(PatientRecord.class), anyLong())).thenReturn(true);
        when(healthScoreCalculator.calculateCompositeHealthScore(recentRecords)).thenReturn(0.4);

        alertGenerator.evaluateData(patient);

        verify(outputStrategy, times(1)).output(anyInt(), anyLong(), anyString(), anyString());
    }

    @Test
    public void testEvaluateData_CombinedConditionAlertTriggered() {
        Patient patient = new Patient(1);
        List<PatientRecord> recentRecords = new ArrayList<>();
        when(dataStorage.getRecords(anyInt(), anyLong(), anyLong())).thenReturn(recentRecords);
        when(conditionEvaluator.checkAndTriggerAlert(any(Patient.class), any(PatientRecord.class), anyLong())).thenReturn(false);
        when(conditionEvaluator.checkForCombinedConditions(any(Patient.class), anyLong())).thenReturn(true);
        when(healthScoreCalculator.calculateCompositeHealthScore(recentRecords)).thenReturn(0.4);

        alertGenerator.evaluateData(patient);

        verify(outputStrategy, times(1)).output(anyInt(), anyLong(), anyString(), anyString());
    }

    @Test
    public void testEvaluateData_BloodPressureTrendAlertTriggered() {
        Patient patient = new Patient(1);
        long now = System.currentTimeMillis();
        PatientRecord record1 = new PatientRecord(1, 120, "BloodPressure", now - 3600000);
        PatientRecord record2 = new PatientRecord(1, 130, "BloodPressure", now - 1800000);
        PatientRecord record3 = new PatientRecord(1, 140, "BloodPressure", now);
        List<PatientRecord> recentRecords = Arrays.asList(record1, record2, record3);
        when(dataStorage.getRecords(anyInt(), anyLong(), anyLong())).thenReturn(recentRecords);
        when(conditionEvaluator.checkAndTriggerAlert(any(Patient.class), any(PatientRecord.class), anyLong())).thenReturn(false);
        when(healthScoreCalculator.calculateCompositeHealthScore(recentRecords)).thenReturn(0.4);

        alertGenerator.evaluateData(patient);

        verify(bloodPressureTrendEvaluator, times(1)).checkBloodPressureTrends(anyList(), any(Patient.class), anyLong());
    }

    @Test
    public void testEvaluateData_NoRecords() {
        Patient patient = new Patient(1);
        List<PatientRecord> recentRecords = new ArrayList<>();
        when(dataStorage.getRecords(anyInt(), anyLong(), anyLong())).thenReturn(recentRecords);

        alertGenerator.evaluateData(patient);

        verify(outputStrategy, never()).output(anyInt(), anyLong(), anyString(), anyString());
    }

    @Test
    public void testEvaluateData_HealthScoreBoundary() {
        Patient patient = new Patient(1);
        List<PatientRecord> recentRecords = new ArrayList<>();
        when(dataStorage.getRecords(anyInt(), anyLong(), anyLong())).thenReturn(recentRecords);
        when(healthScoreCalculator.calculateCompositeHealthScore(recentRecords)).thenReturn(0.5);

        alertGenerator.evaluateData(patient);

        ArgumentCaptor<Integer> patientIdCaptor = ArgumentCaptor.forClass(Integer.class);
        ArgumentCaptor<Long> timestampCaptor = ArgumentCaptor.forClass(Long.class);
        ArgumentCaptor<String> labelCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> dataCaptor = ArgumentCaptor.forClass(String.class);
        verify(outputStrategy).output(patientIdCaptor.capture(), timestampCaptor.capture(), labelCaptor.capture(), dataCaptor.capture());

        assertEquals(1, patientIdCaptor.getValue());
        assertEquals("Alert", labelCaptor.getValue());

        String actualOutput = dataCaptor.getValue();
        String actualTimestamp = actualOutput.split("\\| Timestamp: ")[1];
        String expectedOutput = "Alert for Patient ID: 1 | Condition: Health score indicates potential distress | Timestamp: " + actualTimestamp;
        assertEquals(expectedOutput, actualOutput);
    }
}
