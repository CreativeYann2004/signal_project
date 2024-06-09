package data_management;

import com.alerts.Alert;
import com.alerts.decorator.PriorityAlertDecorator;
import com.alerts.decorator.RepeatedAlertDecorator;
import com.alerts.factory.AlertFactory;
import com.alerts.factory.BloodPressureAlertFactory;
import com.alerts.strategy.BloodPressureStrategy;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import com.data_management.*;

/**
 * Full system test to verify the end-to-end functionality of the alert system.
 */
class FullSystemTest {

    /**
     * Tests the entire flow of adding patient data, checking alert conditions, creating alerts, and decorating alerts.
     */
    @Test
    void testEndToEndFunctionality() {
        // Initialize system components
        DataStorage storage = DataStorage.getInstance();
        storage.clear();

        BloodPressureStrategy bloodPressureStrategy = new BloodPressureStrategy();
        AlertFactory factory = new BloodPressureAlertFactory();

        // Add patient data
        long currentTime = System.currentTimeMillis();
        storage.addPatientData(12345, 190.0, "BloodPressure", currentTime);

        // Create patient and record
        Patient patient = new Patient(12345);
        PatientRecord record = new PatientRecord(12345, 190.0, 60.0, "BloodPressure", currentTime);

        // Check strategy and create alert
        boolean alertTriggered = bloodPressureStrategy.checkAlert(patient, storage);
        assertTrue(alertTriggered, "Alert should be triggered for high blood pressure");

        Alert alert = factory.createAlert(patient, record, currentTime);
        assertNotNull(alert, "Alert should not be null");
        assertEquals("Critical blood pressure level detected", alert.getCondition());

        // Decorate alert
        Alert repeatedAlert = new RepeatedAlertDecorator(alert, 3);
        Alert priorityAlert = new PriorityAlertDecorator(repeatedAlert, "High");

        assertEquals(alert.getFormattedAlert() + " (Repeated 3 times)", repeatedAlert.getFormattedAlert());
        assertEquals("[Priority: High] " + repeatedAlert.getFormattedAlert(), priorityAlert.getFormattedAlert());
    }
}
