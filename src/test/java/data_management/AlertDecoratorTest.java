package data_management;

import com.alerts.Alert;
import com.alerts.decorator.PriorityAlertDecorator;
import com.alerts.decorator.RepeatedAlertDecorator;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Unit tests for alert decorators.
 */
class AlertDecoratorTest {

    /**
     * Tests the RepeatedAlertDecorator to ensure it correctly formats the alert message with repetition information.
     */
    @Test
    void testRepeatedAlertDecorator() {
        Alert baseAlert = new Alert(12345, "Test Condition", System.currentTimeMillis());
        RepeatedAlertDecorator repeatedAlert = new RepeatedAlertDecorator(baseAlert, 3);

        assertEquals(baseAlert.getFormattedAlert() + " (Repeated 3 times)", repeatedAlert.getFormattedAlert());
    }

    /**
     * Tests the PriorityAlertDecorator to ensure it correctly formats the alert message with priority information.
     */
    @Test
    void testPriorityAlertDecorator() {
        Alert baseAlert = new Alert(12345, "Test Condition", System.currentTimeMillis());
        PriorityAlertDecorator priorityAlert = new PriorityAlertDecorator(baseAlert, "High");

        assertEquals("[Priority: High] " + baseAlert.getFormattedAlert(), priorityAlert.getFormattedAlert());
    }
}
