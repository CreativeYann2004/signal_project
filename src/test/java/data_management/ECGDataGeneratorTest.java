package data_management;

import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import com.cardio_generator.generators.ECGDataGenerator;
import com.cardio_generator.outputs.OutputStrategy;

public class ECGDataGeneratorTest {

    private ECGDataGenerator ecgDataGenerator;
    private OutputStrategy outputStrategyMock;

    @BeforeEach
    void setUp() {
        ecgDataGenerator = new ECGDataGenerator(10);
        outputStrategyMock = mock(OutputStrategy.class);
    }

    @Test
    void testGenerateECGData() {
        int patientId = 1;

        ecgDataGenerator.generate(patientId, outputStrategyMock);

        verify(outputStrategyMock, times(1)).output(eq(patientId), anyLong(), eq("ECG"), anyString());
    }

    @Test
    void testDifferentPatientIds() {
        for (int patientId = 1; patientId <= 10; patientId++) {
            ecgDataGenerator.generate(patientId, outputStrategyMock);
        }

        verify(outputStrategyMock, times(10)).output(anyInt(), anyLong(), eq("ECG"), anyString());
    }

    @Test
    void testSimulateEcgWaveform() {
        int patientId = 1;
        double lastEcgValue = 0.0;
        double lastPhase = 0.0;

        double ecgValue = ecgDataGenerator.simulateEcgWaveform(patientId, lastEcgValue, lastPhase);

        assertTrue(ecgValue >= -1.0 && ecgValue <= 2.0, "ECG value should be within the realistic range.");
    }

    @Test
    void testSimulateQrsComplex() {
        double phase = Math.PI / 2;
        double qrsValue = ecgDataGenerator.simulateQrsComplex(phase);

        assertTrue(qrsValue >= 0 && qrsValue <= 1.5, "QRS complex value should be within the expected range.");
    }

    @Test
    void testSimulateNoise() {
        double noiseValue = ecgDataGenerator.simulateNoise();

        assertTrue(noiseValue >= -0.1 && noiseValue <= 0.1, "Noise value should be within the realistic range.");
    }

    @Test
    void testOutputStrategyCalled() {
        int patientId = 1;

        ecgDataGenerator.generate(patientId, outputStrategyMock);

        verify(outputStrategyMock, atLeastOnce()).output(anyInt(), anyLong(), eq("ECG"), anyString());
    }
}
