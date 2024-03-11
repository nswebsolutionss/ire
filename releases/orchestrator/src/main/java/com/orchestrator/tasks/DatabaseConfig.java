package com.orchestrator.tasks;

public class DatabaseConfig {
    private static final String DATABASE_ROLE = "superuser";
    private static final String DATABASE_NAME = "test";

    public String getRole()
    {
        return DATABASE_ROLE;
    }

    public String getName()
    {
        return DATABASE_NAME;
    }
}
