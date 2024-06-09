package com.alerts.decorator;

import com.alerts.Alert;

/**
 * Decorator class that adds repeat count information to an alert.
 */
public class RepeatedAlertDecorator extends AlertDecorator {
    private int repeatCount;

    /**
     * Constructs a RepeatedAlertDecorator with the specified alert and repeat count.
     *
     * @param decoratedAlert The alert to be decorated.
     * @param repeatCount    The number of times the alert has been repeated.
     */
    public RepeatedAlertDecorator(Alert decoratedAlert, int repeatCount) {
        super(decoratedAlert);
        this.repeatCount = repeatCount;
    }

    /**
     * Returns the formatted alert message with the repeat count.
     *
     * @return The formatted alert message with repeat count information.
     */
    @Override
    public String getFormattedAlert() {
        return super.getFormattedAlert() + " (Repeated " + repeatCount + " times)";
    }
}
