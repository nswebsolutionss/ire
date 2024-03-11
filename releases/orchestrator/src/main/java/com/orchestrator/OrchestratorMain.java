package com.orchestrator;

import com.orchestrator.tasks.PostgresTask;
import com.orchestrator.tasks.Task;

import java.util.ArrayList;

public class OrchestratorMain {

    private static final ArrayList<Task> services = new ArrayList<>();

    public static void main(String[] args) {
        services.add(new PostgresTask());

        services.forEach(Task::createInstallTask);
        services.forEach(Task::doWork);
        services.forEach(Task::createStartTask);
        services.forEach(Task::doWork);
        services.forEach(Task::createDeployTask);
        services.forEach(Task::doWork);

    }
}

