package data_management;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import com.cardio_generator.generators.ECGDataGenerator;
import com.cardio_generator.outputs.OutputStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import static org.mockito.Mockito.*;

public class FullWorkflowIntegrationTest {

    private ECGDataGenerator ecgDataGenerator;
    private OutputStrategy outputStrategy;

    @BeforeEach
    public void setUp() {
        outputStrategy = mock(OutputStrategy.class);
        ecgDataGenerator = new ECGDataGenerator(1);  // Assuming 1 patient for the test
    }

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
        // We won't assert the exact value of data because it's randomly generated
    }
}
