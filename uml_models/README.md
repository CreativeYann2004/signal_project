Here is the directory with the UML digaram and the documentation: 
My UML model for the SignalProject

This documentation describes my UML model that I developed to represent the SignalProject 
a cardiovascular health data simulation and output system.
The model illustrates how different components interact to generate and manage health metrics for multiple patients 
to support the development and testing of healthcare monitoring technologies.

## System components:
- Data Generators
* AlertGenerator: monitors specific health metrics thresholds and generates alerts accordingly.
* BloodLevelsDataGenerator: Simulates blood metrics such as cholesterol and cell counts.
* BloodPressureDataGenerator: Provides continuous blood pressure readings.
* BloodSaturationDataGenerator: Generates blood oxygen saturation data.
* ECGDataGenerator: Simulates ECG waveforms that are important for monitoring the heart.
- OutputStrategies
Are implemented via the OutputStrategy interface and allow data to be displayed using various methods:
* ConsoleOutputStrategy: Outputs the data directly to the console so that it can be displayed immediately.
* FileOutputStrategy (file output strategy): Stores the data in files, organized by patient ID.
* TcpOutputStrategy (output strategy): Sends data via TCP connections, suitable for real-time applications.
* WebSocketOutputStrategy: Enables real-time data transfer via websockets.

## Central administration:
The HealthDataSimulator acts as a central controller that coordinates the initialization, management and scheduling of tasks for the various data generators. 
It ensures the smooth operation and scalability of the data simulation and adapts to the requirements of different test environments.

## Conclusion:
When creating this UML model, my goal was to define a modular and flexible architecture that is easy to understand and adapt to different healthcare scenarios. 
Future enhancements could include more detailed simulation capabilities for additional metrics and improved integration with real-time data analysis tools.
This model serves as a basic guide of the Signal project and provides a structured approach for understanding and extending the capabilities of the system.

