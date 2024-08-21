package com.ire.propertyportalgateway.service.alerts;

public interface Alerts {
    public void raiseAlert(final String message);

    void raiseAlert(String message, Exception e);

    void raiseAlert(String message, Throwable e);
}
