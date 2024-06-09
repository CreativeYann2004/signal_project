package data_management;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import com.data_management.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Integration test for WebSocket data reception and storing in {@link DataStorage}.
 */
public class IntegrationTest {

    /**
     * Tests the reception of data over WebSocket and its storage in {@link DataStorage}.
     *
     * @throws Exception If an error occurs during the test.
     */
    @Test
    public void testWebSocketDataReception() throws Exception {
        int port = 8080;
        SimpleWebSocketServer server = new SimpleWebSocketServer(port);
        server.start();

        try {
            DataStorage dataStorage = DataStorage.getInstance();
            CountDownLatch latch = new CountDownLatch(1);

            WebSocketDataReader dataReader = new WebSocketDataReader("ws://localhost:" + port);
            dataReader.setOnDataReceivedListener((data) -> {
                // Assuming the data is in the same format that WebSocketDataReader expects
                String[] parts = data.split(",");
                int patientId = Integer.parseInt(parts[0]);
                double measurementValue = Double.parseDouble(parts[1]);
                String recordType = parts[2];
                long timestamp = Long.parseLong(parts[3]);
                dataStorage.addPatientData(patientId, measurementValue, recordType, timestamp);
                latch.countDown();
            });

            dataReader.connectAndReadData(dataStorage);

            // Wait for data to be received
            boolean received = latch.await(10, TimeUnit.SECONDS);

            assertTrue(received, "No records found");

            // Assuming we're checking for records for a specific patient and time range
            int patientId = 12345; // Replace with actual patient ID
            long startTime = 0; // Replace with actual start time
            long endTime = System.currentTimeMillis(); // Replace with actual end time
            List<PatientRecord> records = dataStorage.getRecords(patientId, startTime, endTime);

            assertTrue(records.size() > 0, "No records found in data storage");

        } finally {
            server.stop();
        }
    }
}
