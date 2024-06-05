package data_management;

import com.data_management.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;
import static org.junit.jupiter.api.Assertions.*;

class DataStorageTest {

    private DataStorage storage;

    @BeforeEach
    void setUp() {
        storage = new DataStorage();
    }

    @Test
    void testDataReaderIntegration() throws IOException {
        // Use the correct path to your test data directory
        String testDirectoryPath = "src\\test\\resources\\testData\\testData.txt";
        FileDataReader reader = new FileDataReader(testDirectoryPath);
        reader.readData(storage);

        // Assuming patient ID 1 and a timestamp range are valid for the test data
        List<PatientRecord> records = storage.getRecords(1, 1627836000000L, 1627836200000L);
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

    @Test
    void testErrorHandlingWithInvalidInput() {
        assertThrows(NumberFormatException.class, () -> {
            storage.addPatientData(1, Double.parseDouble("NaN"), "HeartRate", 1622542800000L);
        }, "Should throw NumberFormatException for non-numeric values");
    }

    @Test
    void testInvalidRecordType() {
        assertThrows(IllegalArgumentException.class, () -> {
            storage.addPatientData(1, 100.0, "InvalidType", 1622542800000L);
        }, "Should throw IllegalArgumentException for invalid record type");
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

    @Test
    void testBoundaryConditions() {
        storage.addPatientData(1, 100.0, "HeartRate", 1622542800000L);
        List<PatientRecord> records = storage.getRecords(1, 1622542800000L, 1622542800000L);
        assertEquals(1, records.size(), "Should retrieve the record exactly at the boundary");
    }

    @Test
    void testDuplicateEntries() {
        storage.addPatientData(1, 100.0, "HeartRate", 1622542800000L);
        storage.addPatientData(1, 100.0, "HeartRate", 1622542800000L);
        List<PatientRecord> records = storage.getRecords(1, 1622540000000L, 1622550000000L);
        assertEquals(2, records.size(), "Should retrieve two identical records");
    }

    @Test
    void testEmptyFields() {
        assertThrows(IllegalArgumentException.class, () -> {
            storage.addPatientData(1, 100.0, "", 1622542800000L);
        }, "Should throw IllegalArgumentException for empty record type");
    }

    @Test
    void testLargeVolumeData() {
        IntStream.range(0, 10000).forEach(i -> storage.addPatientData(1, 100.0 + i, "HeartRate", 1622542800000L + i));
        List<PatientRecord> records = storage.getRecords(1, 1622542800000L, 1622642800000L);
        assertEquals(10000, records.size(), "Should handle a large number of records");
    }
}
