package com.ire.database;

public class DatabaseWrapperConfig {
    public String getBinDir() {
        return "/Users/steviehubble/Workspace/internationalRealEstate/ire/backend/opt/pgsql/bin";
    }

    public String getDbName() {
        return "ire";
    }

    public String getSqlFilePath() {
        return "/Users/steviehubble/Workspace/internationalRealEstate/ire/backend/databasewrapper/src/main/resources/user.sql";
    }


    public String getDataDir() {
        return "/Users/steviehubble/Workspace/internationalRealEstate/ire/backend/opt/pgsqlData/data";
    }

    public String getHomeDir() {
        return "/Users/steviehubble/Workspace/internationalRealEstate/ire/backend/opt/pgsql";
    }

    public String getLogFile() {
        return "/Users/steviehubble/Workspace/internationalRealEstate/ire/backend/log/postgres.log";
    }

    public String getLogDir() {
        return "/Users/steviehubble/Workspace/internationalRealEstate/ire/backend/log";
    }

    public String getRole() {
        return "superuser";
    }
}
