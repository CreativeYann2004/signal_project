package data_management;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

import com.data_management.DataStorage;
import com.data_management.FileDataReader;
import com.data_management.PatientRecord;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;


class DataStorageTest {

    private DataStorage storage;

    @BeforeEach
    void setUp() {
        storage = new DataStorage();
    }
    void testDataReaderIntegration() throws IOException {
    FileDataReader reader = new FileDataReader("src/test/resources/testData");
    reader.readData(storage);
    
    List<PatientRecord> records = storage.getRecords(1, 1622540000000L, 1622550000000L);
    assertFalse(records.isEmpty(), "Records should not be empty after reading valid data files");
    assertEquals(100.0, records.get(0).getMeasurementValue(), "Check the value of the first record");
}
    @Test
    void testAddAndGetRecords() {
        // Assume these are correctly formatted data inputs.
        storage.addPatientData(1, 100.0, "HeartRate", 1622542800000L);
        storage.addPatientData(1, 120.0, "HeartRate", 1622546400000L);

        List<PatientRecord> records = storage.getRecords(1, 1622540000000L, 1622550000000L);
        assertEquals(2, records.size(), "Should retrieve two records");
        assertEquals(100.0, records.get(0).getMeasurementValue(), "First record should match");
        assertEquals(120.0, records.get(1).getMeasurementValue(), "Second record should match");
    }

    @Test
    void testGetRecordsWithNoData() {
        List<PatientRecord> records = storage.getRecords(2, 1622540000000L, 1622550000000L);
        assertTrue(records.isEmpty(), "Should return an empty list for non-existing patient");
    }

    void testErrorHandlingWithInvalidInput() {
        assertThrows(NumberFormatException.class, () -> {
            storage.addPatientData(1, Double.parseDouble("NaN"), "HeartRate", 1622542800000L);
        }, "Should throw NumberFormatException for non-numeric values");
    }
    @Test
    void testConcurrentAccess() throws InterruptedException {
        ExecutorService service = Executors.newFixedThreadPool(10);
        IntStream.range(0, 1000).forEach(i -> service.submit(() -> storage.addPatientData(1, 100.0, "HeartRate", System.currentTimeMillis())));
        service.shutdown();
        service.awaitTermination(1, TimeUnit.MINUTES);

        // Check consistency of dataStorage after concurrent modifications
        assertNotNull(storage.getRecords(1, 0, Long.MAX_VALUE));
        assertEquals(1000, storage.getRecords(1, 0, Long.MAX_VALUE).size(), "Should handle 1000 concurrent updates correctly");
}
}
