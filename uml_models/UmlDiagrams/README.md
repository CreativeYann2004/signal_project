## Documentation of the sequence diagram:
Alarm generation processw
## Overview
The sequence diagram shows the interaction between the DataStorage, AlertGenerator and AlertManager components within the health monitoring system 
when a threshold value is exceeded.

## Process flow
Threshold value exceeded:
The process is triggered when a data point exceeds a predefined threshold, signaling a potential health risk.

## Data processing by AlertGenerator:
Send data: 
* DataStorage sends the relevant patient data to the AlertGenerator.
* Retrieve historical data: The AlertGenerator requests historical data from DataStorage to compare the current reading with previous trends.
* Evaluate data: The AlertGenerator evaluates the incoming data based on defined criteria to determine if an alert is warranted.
* Stabilize condition: If the condition stabilizes or if it is determined to be a false alarm, the process is stopped.
* Create alert: If the criteria for an alert are met, the AlertGenerator creates an alert message.

## Alert Management:
* Send alert: The AlertGenerator sends the alert to the AlertManager.
* Resend alert: If the condition persists without confirmation, the AlertManager can resend the alert.

## Acknowledge alarm: 
The AlertManager receives and acknowledges the alarm. This acknowledgement prevents further messages from being sent for the same alarm.
* Resolve: The alert is resolved as soon as the condition is fulfilled or no longer exists.

## Conclusion:
The sequence diagram successfully outlines the critical steps in the alarm generation process and demonstrates the system's ability to detect, validate and respond to health risks in real time. The diagram clearly illustrates how the data flows between the components and how the decision making process occurs in alarm management.

