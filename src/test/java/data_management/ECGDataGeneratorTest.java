package data_management;

import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import com.cardio_generator.generators.ECGDataGenerator;
import com.cardio_generator.outputs.OutputStrategy;

/**
 * Unit tests for the {@link ECGDataGenerator} class.
 */
public class ECGDataGeneratorTest {

    private ECGDataGenerator ecgDataGenerator;
    private OutputStrategy outputStrategyMock;

    /**
     * Sets up the test environment before each test.
     * Initializes the {@link ECGDataGenerator} and mocks the {@link OutputStrategy}.
     */
    @BeforeEach
    void setUp() {
        ecgDataGenerator = new ECGDataGenerator(10);
        outputStrategyMock = mock(OutputStrategy.class);
    }

    /**
     * Tests the generation of ECG data for a single patient.
     * Verifies that the output strategy's {@code output} method is called once.
     */
    @Test
    void testGenerateECGData() {
        int patientId = 1;

        ecgDataGenerator.generate(patientId, outputStrategyMock);

        verify(outputStrategyMock, times(1)).output(eq(patientId), anyLong(), eq("ECG"), anyString());
    }

    /**
     * Tests the generation of ECG data for multiple patients.
     * Verifies that the output strategy's {@code output} method is called for each patient.
     */
    @Test
    void testDifferentPatientIds() {
        for (int patientId = 1; patientId <= 10; patientId++) {
            ecgDataGenerator.generate(patientId, outputStrategyMock);
        }

        verify(outputStrategyMock, times(10)).output(anyInt(), anyLong(), eq("ECG"), anyString());
    }

    /**
     * Tests the simulation of the ECG waveform.
     * Verifies that the generated ECG value is within a realistic range.
     */
    @Test
    void testSimulateEcgWaveform() {
        int patientId = 1;
        double lastEcgValue = 0.0;
        double lastPhase = 0.0;

        double ecgValue = ecgDataGenerator.simulateEcgWaveform(patientId, lastEcgValue, lastPhase);

        assertTrue(ecgValue >= -1.0 && ecgValue <= 2.0, "ECG value should be within the realistic range.");
    }

    /**
     * Tests the simulation of the QRS complex.
     * Verifies that the QRS complex value is within the expected range.
     */
    @Test
    void testSimulateQrsComplex() {
        double phase = Math.PI / 2;
        double qrsValue = ecgDataGenerator.simulateQrsComplex(phase);

        assertTrue(qrsValue >= 0 && qrsValue <= 1.5, "QRS complex value should be within the expected range.");
    }

    /**
     * Tests the simulation of noise in the ECG waveform.
     * Verifies that the noise value is within a realistic range.
     */
    @Test
    void testSimulateNoise() {
        double noiseValue = ecgDataGenerator.simulateNoise();

        assertTrue(noiseValue >= -0.1 && noiseValue <= 0.1, "Noise value should be within the realistic range.");
    }

    /**
     * Tests that the output strategy is called at least once during the generation of ECG data.
     */
    @Test
    void testOutputStrategyCalled() {
        int patientId = 1;

        ecgDataGenerator.generate(patientId, outputStrategyMock);

        verify(outputStrategyMock, atLeastOnce()).output(anyInt(), anyLong(), eq("ECG"), anyString());
    }
}
