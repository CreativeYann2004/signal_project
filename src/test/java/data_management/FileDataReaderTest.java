package data_management;

import com.data_management.*;

import static org.mockito.Mockito.*;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for the {@link FileDataReader} class.
 */
class FileDataReaderTest {

    private DataStorage dataStorageMock;
    private FileDataReader fileDataReader;

    /**
     * Sets up the test environment before each test.
     * Initializes the mock {@link DataStorage} and the {@link FileDataReader} instance.
     */
    @BeforeEach
    void setUp() {
        dataStorageMock = mock(DataStorage.class);
        fileDataReader = new FileDataReader("testDirectory");
    }

    /**
     * Tests the reading and parsing of data from files.
     * Verifies that the parsed data is correctly added to the {@link DataStorage}.
     *
     * @throws IOException If an I/O error occurs during reading data.
     */
    @Test
    void testReadData() throws IOException {
        // Assuming parseAndStore is modified to be protected or package-private for testing purposes
        // Alternatively, use a spy to verify interactions with dataStorage

        List<String> testData = Arrays.asList(
            "1, 100.0, HeartRate, 1627836123000", // Valid timestamp within int range
            "2, 200.0, BloodPressure, 1627836123000",
            "3, 90.0, BloodOxygenSaturation, 1627836123000",
            "4, 110.0, HeartRate, 1627836123000",
            "5, 80.0, BloodPressure, 1627836123000",
            "6, 95.0, BloodOxygenSaturation, 1627836123000",
            "7, 72.0, HeartRate, 1627836123000",
            "8, 130.0, BloodPressure, 1627836123000",
            "9, 85.0, BloodOxygenSaturation, 1627836123000",
            "10, 65.0, HeartRate, 1627836123000",
            "11, 140.0, BloodPressure, 1627836123000",
            "12, 92.0, BloodOxygenSaturation, 1627836123000"
        );

        for (String line : testData) {
            fileDataReader.parseAndStore(line, dataStorageMock);
        }

        // Verify that dataStorage.addPatientData was called with the correct parameters
        verify(dataStorageMock, times(1)).addPatientData(1, 100.0, "HeartRate", 1627836123000L);
        verify(dataStorageMock, times(1)).addPatientData(2, 200.0, "BloodPressure", 1627836123000L);
        verify(dataStorageMock, times(1)).addPatientData(3, 90.0, "BloodOxygenSaturation", 1627836123000L);
        verify(dataStorageMock, times(1)).addPatientData(4, 110.0, "HeartRate", 1627836123000L);
        verify(dataStorageMock, times(1)).addPatientData(5, 80.0, "BloodPressure", 1627836123000L);
        verify(dataStorageMock, times(1)).addPatientData(6, 95.0, "BloodOxygenSaturation", 1627836123000L);
        verify(dataStorageMock, times(1)).addPatientData(7, 72.0, "HeartRate", 1627836123000L);
        verify(dataStorageMock, times(1)).addPatientData(8, 130.0, "BloodPressure", 1627836123000L);
        verify(dataStorageMock, times(1)).addPatientData(9, 85.0, "BloodOxygenSaturation", 1627836123000L);
        verify(dataStorageMock, times(1)).addPatientData(10, 65.0, "HeartRate", 1627836123000L);
        verify(dataStorageMock, times(1)).addPatientData(11, 140.0, "BloodPressure", 1627836123000L);
        verify(dataStorageMock, times(1)).addPatientData(12, 92.0, "BloodOxygenSaturation", 1627836123000L);
    }
}
