package data_management;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import com.cardio_generator.outputs.WebSocketOutputStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class WebSocketOutputStrategyTest {

    private WebSocketOutputStrategy server;

    @BeforeEach
    public void setUp() {
        server = new WebSocketOutputStrategy(8080);
    }

    @Test
    public void testOutput() {
        long timestamp = System.currentTimeMillis();
        assertDoesNotThrow(() -> server.output(1, timestamp, "HeartRate", "80"));
    }
}
