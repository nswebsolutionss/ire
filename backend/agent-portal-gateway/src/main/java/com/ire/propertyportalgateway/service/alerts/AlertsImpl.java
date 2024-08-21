package com.ire.propertyportalgateway.service.alerts;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AlertsImpl implements Alerts {

    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    public void raiseAlert(String message) {
        LOGGER.error(message, new AlertStackException());
    }

    @Override
    public void raiseAlert(String message, Exception e) {
        LOGGER.error(message, e);
    }

    @Override
    public void raiseAlert(String message, Throwable e) {
        LOGGER.error(message, e);
    }
}
