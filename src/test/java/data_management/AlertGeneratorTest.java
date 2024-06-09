package data_management;

import com.alerts.*;
import com.alerts.conditions.BasicConditionEvaluator;
import com.alerts.conditions.BasicHealthScoreCalculator;
import com.alerts.conditions.BasicBloodPressureTrendEvaluator;
import com.alerts.factory.BloodOxygenAlertFactory;
import com.alerts.strategy.OxygenSaturationStrategy;
import com.cardio_generator.outputs.OutputStrategy;
import com.data_management.DataStorage;
import com.data_management.Patient;
import com.data_management.PatientRecord;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for the AlertGenerator class.
 */
public class AlertGeneratorTest {

    @Mock
    private DataStorage dataStorage;

    @Mock
    private OutputStrategy outputStrategy;

    @Mock
    private BasicConditionEvaluator conditionEvaluator;

    @Mock
    private BasicHealthScoreCalculator healthScoreCalculator;

    @Mock
    private BasicBloodPressureTrendEvaluator bloodPressureTrendEvaluator;

    @Mock
    private BloodOxygenAlertFactory bloodOxygenAlertFactory;

    private AlertGenerator alertGenerator;
    private Patient patient;

    /**
     * Sets up the test environment before each test.
     */
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        OxygenSaturationStrategy oxygenSaturationStrategy = new OxygenSaturationStrategy(dataStorage);
        bloodOxygenAlertFactory = new BloodOxygenAlertFactory(oxygenSaturationStrategy);

        alertGenerator = new AlertGenerator(
            dataStorage, outputStrategy, conditionEvaluator, healthScoreCalculator, bloodPressureTrendEvaluator, bloodOxygenAlertFactory
        );
        patient = new Patient(12345);
    }

    /**
     * Tests the generation of a blood pressure alert.
     */
    @Test
    public void testBloodPressureAlert() {
        PatientRecord record = new PatientRecord(12345, 190, 50, "BloodPressure", System.currentTimeMillis());
        when(dataStorage.getRecords(eq(12345), anyLong(), anyLong())).thenReturn(Arrays.asList(record));

        alertGenerator.evaluateData(patient);

        ArgumentCaptor<Integer> patientIdCaptor = ArgumentCaptor.forClass(Integer.class);
        ArgumentCaptor<Long> timestampCaptor = ArgumentCaptor.forClass(Long.class);
        ArgumentCaptor<String> typeCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> messageCaptor = ArgumentCaptor.forClass(String.class);

        verify(outputStrategy).output(patientIdCaptor.capture(), timestampCaptor.capture(), typeCaptor.capture(), messageCaptor.capture());

        assertEquals(12345, patientIdCaptor.getValue());
        assertEquals("Alert", typeCaptor.getValue());
        assertTrue(messageCaptor.getValue().contains("Critical blood pressure level detected"));
    }

    /**
     * Tests the generation of a heart rate alert.
     */
    @Test
    public void testHeartRateAlert() {
        PatientRecord record = new PatientRecord(12345, 130, "HeartRate", System.currentTimeMillis());
        when(dataStorage.getRecords(eq(12345), anyLong(), anyLong())).thenReturn(Arrays.asList(record));

        alertGenerator.evaluateData(patient);

        ArgumentCaptor<Integer> patientIdCaptor = ArgumentCaptor.forClass(Integer.class);
        ArgumentCaptor<Long> timestampCaptor = ArgumentCaptor.forClass(Long.class);
        ArgumentCaptor<String> typeCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> messageCaptor = ArgumentCaptor.forClass(String.class);

        verify(outputStrategy).output(patientIdCaptor.capture(), timestampCaptor.capture(), typeCaptor.capture(), messageCaptor.capture());

        assertEquals(12345, patientIdCaptor.getValue());
        assertEquals("Alert", typeCaptor.getValue());
        assertTrue(messageCaptor.getValue().contains("Abnormal heart rate detected"));
    }

    /**
     * Tests the generation of a composite health score alert.
     */
    @Test
    public void testCompositeHealthScoreAlert() {
        when(dataStorage.getRecords(eq(12345), anyLong(), anyLong())).thenReturn(Arrays.asList());
        when(healthScoreCalculator.calculateCompositeHealthScore(anyList())).thenReturn(0.6);

        alertGenerator.evaluateData(patient);

        ArgumentCaptor<Integer> patientIdCaptor = ArgumentCaptor.forClass(Integer.class);
        ArgumentCaptor<Long> timestampCaptor = ArgumentCaptor.forClass(Long.class);
        ArgumentCaptor<String> typeCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> messageCaptor = ArgumentCaptor.forClass(String.class);

        verify(outputStrategy).output(patientIdCaptor.capture(), timestampCaptor.capture(), typeCaptor.capture(), messageCaptor.capture());

        assertEquals(12345, patientIdCaptor.getValue());
        assertEquals("Alert", typeCaptor.getValue());
        assertTrue(messageCaptor.getValue().contains("Health score indicates potential distress"));
    }
}
