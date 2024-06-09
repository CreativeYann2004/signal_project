package data_management;
import org.junit.jupiter.api.TestInstance;

import com.cardio_generator.outputs.WebSocketOutputStrategy;

public class WebSocketServerTest {
    public static void main(String[] args) {
        WebSocketOutputStrategy server = new WebSocketOutputStrategy(8080);

        server.output(1, System.currentTimeMillis(), "HeartRate", "80");
    }
}
