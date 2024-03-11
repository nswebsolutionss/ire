package com.orchestrator.tasks;

public interface Task {

    void createStartTask();

    void createInstallTask();

    void createDeployTask();

    void createStopTask();
    void doWork();
}
