package com.alerts.decorator;

import com.alerts.Alert;

/**
 * Decorator class that adds priority level information to an alert.
 */
public class PriorityAlertDecorator extends AlertDecorator {
    private String priorityLevel;

    /**
     * Constructs a PriorityAlertDecorator with the specified alert and priority level.
     *
     * @param decoratedAlert The alert to be decorated.
     * @param priorityLevel  The priority level to be added to the alert.
     */
    public PriorityAlertDecorator(Alert decoratedAlert, String priorityLevel) {
        super(decoratedAlert);
        this.priorityLevel = priorityLevel;
    }

    /**
     * Returns the formatted alert message with the priority level.
     *
     * @return The formatted alert message with priority information.
     */
    @Override
    public String getFormattedAlert() {
        return "[Priority: " + priorityLevel + "] " + super.getFormattedAlert();
    }
}
