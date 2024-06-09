package com.alerts.decorator;

import com.alerts.Alert;

/**
 * Abstract decorator class for alerts. This class allows additional functionality to be added to alerts.
 */
public abstract class AlertDecorator extends Alert {
    protected Alert decoratedAlert;

    /**
     * Constructs an AlertDecorator with the specified alert to be decorated.
     *
     * @param decoratedAlert The alert to be decorated.
     */
    public AlertDecorator(Alert decoratedAlert) {
        super(decoratedAlert.getPatientId(), decoratedAlert.getCondition(), decoratedAlert.getTimestamp());
        this.decoratedAlert = decoratedAlert;
    }

    /**
     * Returns the formatted alert message. This method delegates the call to the decorated alert.
     *
     * @return The formatted alert message.
     */
    @Override
    public String getFormattedAlert() {
        return decoratedAlert.getFormattedAlert();
    }
}
