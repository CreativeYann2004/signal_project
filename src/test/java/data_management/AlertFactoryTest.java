package data_management;

import com.alerts.Alert;
import com.alerts.factory.AlertFactory;
import com.alerts.factory.BloodOxygenAlertFactory;
import com.alerts.factory.BloodPressureAlertFactory;
import com.alerts.strategy.OxygenSaturationStrategy;
import org.junit.jupiter.api.Test;
import com.data_management.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for alert factories.
 */
class AlertFactoryTest {

    /**
     * Tests the BloodOxygenAlertFactory to ensure it creates an alert when low blood oxygen saturation is detected.
     */
    @Test
    void testBloodOxygenAlertFactory() {
        // Create a DataStorage instance
        DataStorage storage = DataStorage.getInstance();
        storage.clear();
        
        // Initialize OxygenSaturationStrategy with DataStorage
        OxygenSaturationStrategy oxygenSaturationStrategy = new OxygenSaturationStrategy(storage);

        // Create a BloodOxygenAlertFactory with the strategy
        AlertFactory factory = new BloodOxygenAlertFactory(oxygenSaturationStrategy);
        
        // Add a patient record to the storage using a valid record type
        storage.addPatientData(12345, 85.0, "BloodOxygenSaturation", System.currentTimeMillis());

        // Create a patient and a patient record
        Patient patient = new Patient(12345);
        PatientRecord record = new PatientRecord(12345, 85.0, "BloodOxygenSaturation", System.currentTimeMillis());
        
        // Create an alert using the factory
        Alert alert = factory.createAlert(patient, record, System.currentTimeMillis());

        assertNotNull(alert, "Alert should not be null");
        assertEquals("Low blood oxygen saturation detected", alert.getCondition());
    }

    /**
     * Tests the BloodPressureAlertFactory to ensure it creates an alert when critical blood pressure levels are detected.
     */
    @Test
    void testBloodPressureAlertFactory() {
        AlertFactory factory = new BloodPressureAlertFactory();
        Patient patient = new Patient(12345);
        PatientRecord record = new PatientRecord(12345, 190.0, 60.0, "BloodPressure", System.currentTimeMillis());
        Alert alert = factory.createAlert(patient, record, System.currentTimeMillis());

        assertNotNull(alert, "Alert should not be null");
        assertEquals("Critical blood pressure level detected", alert.getCondition());
    }
}
