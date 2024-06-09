package data_management;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import com.cardio_generator.generators.ECGDataGenerator;
import com.cardio_generator.outputs.OutputStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

/**
 * Integration tests for the full workflow of generating ECG data and verifying output through {@link OutputStrategy}.
 */
public class FullWorkflowIntegrationTest {

    private ECGDataGenerator ecgDataGenerator;
    private OutputStrategy outputStrategy;

    /**
     * Sets up the test environment before each test.
     * Initializes the mock {@link OutputStrategy} and the {@link ECGDataGenerator} instance.
     */
    @BeforeEach
    public void setUp() {
        outputStrategy = mock(OutputStrategy.class);
        ecgDataGenerator = new ECGDataGenerator(1);  // Assuming 1 patient for the test
    }

    /**
     * Tests the generation of ECG data and verifies that the output strategy is called with the correct parameters.
     */
    @Test
    public void testGenerate() {
        int patientId = 1;

        // Act
        ecgDataGenerator.generate(patientId, outputStrategy);

        // Assert
        ArgumentCaptor<Integer> patientIdCaptor = ArgumentCaptor.forClass(Integer.class);
        ArgumentCaptor<Long> timestampCaptor = ArgumentCaptor.forClass(Long.class);
        ArgumentCaptor<String> labelCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> dataCaptor = ArgumentCaptor.forClass(String.class);

        verify(outputStrategy).output(patientIdCaptor.capture(), timestampCaptor.capture(), labelCaptor.capture(), dataCaptor.capture());

        assertEquals(patientId, patientIdCaptor.getValue());
        assertEquals("ECG", labelCaptor.getValue());
    }
}
