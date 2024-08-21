package com.ire.propertyportalgateway.service.support;

import com.ire.propertyportalgateway.service.alerts.AlertStackException;
import com.ire.propertyportalgateway.service.alerts.Alerts;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Assertions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StubAlerts implements Alerts {

    private static final Logger LOGGER = LogManager.getLogger();
    private final List<String> alerts = new ArrayList<>();
    private int cursor;

    @Override
    public void raiseAlert(String message) {
        AlertStackException e = new AlertStackException(message);
        alerts.add(message);
        LOGGER.error(message, e);
    }

    @Override
    public void raiseAlert(String message, Exception e) {
        alerts.add(message + (e == null || e.getMessage() == null || e.getMessage().isEmpty() ? "" : "Exception: " + e.getMessage()));
        LOGGER.error(message, e);
    }

    @Override
    public void raiseAlert(String message, Throwable e) {
        alerts.add(message + (e == null || e.getMessage() == null || e.getMessage().isEmpty() ? "" : "Exception: " + e.getMessage()));
    }

    public void expectAlertContaining(final String... expectedSubStrings) {
        List<String> formattedSubStrings = Arrays.asList(expectedSubStrings);

        for (int i = 0; i < alerts.size(); i++) {
            cursor = i;
            if (formattedSubStrings.stream().anyMatch(mesg -> alerts.get(cursor).contains(mesg))) {
                return;
            }
        }
        Assertions.fail(String.format(
                "Expected an alert containing %n%s %nAlerts received were: %n%s",
                String.join(",", expectedSubStrings),
                String.join("\n", alerts.subList(cursor, alerts.size()))
        ));
    }

    public void expectNoAlerts() {
        if (alerts.isEmpty()) {
            return;
        }
        Assertions.fail(String.format(
                "Expected no alerts but received: %n%s",
                String.join("\n", alerts.subList(cursor, alerts.size()))
        ));
    }
}
