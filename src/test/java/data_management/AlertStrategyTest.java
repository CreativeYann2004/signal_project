package data_management;

import com.alerts.strategy.AlertStrategy;
import com.alerts.strategy.BloodPressureStrategy;
import com.alerts.strategy.HeartRateStrategy;
import com.data_management.DataStorage;
import com.data_management.Patient;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for alert strategies.
 */
class AlertStrategyTest {

    /**
     * Tests the BloodPressureStrategy to ensure it triggers an alert for high blood pressure.
     */
    @Test
    void testBloodPressureStrategy() {
        AlertStrategy strategy = new BloodPressureStrategy();
        DataStorage storage = DataStorage.getInstance();
        storage.clear();
        storage.addPatientData(12345, 190, "BloodPressure", System.currentTimeMillis());

        Patient patient = new Patient(12345);
        boolean alert = strategy.checkAlert(patient, storage);

        assertTrue(alert, "Alert should be triggered for high blood pressure");
    }

    /**
     * Tests the HeartRateStrategy to ensure it triggers an alert for high heart rate.
     */
    @Test
    void testHeartRateStrategy() {
        AlertStrategy strategy = new HeartRateStrategy();
        DataStorage storage = DataStorage.getInstance();
        storage.clear();
        storage.addPatientData(12345, 130, "HeartRate", System.currentTimeMillis());

        Patient patient = new Patient(12345);
        boolean alert = strategy.checkAlert(patient, storage);

        assertTrue(alert, "Alert should be triggered for high heart rate");
    }
}
