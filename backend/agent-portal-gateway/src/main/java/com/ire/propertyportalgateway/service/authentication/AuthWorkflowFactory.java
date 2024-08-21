package com.ire.propertyportalgateway.service.authentication;

@FunctionalInterface
public interface AuthWorkflowFactory {
    AuthWorkflow create();

}
