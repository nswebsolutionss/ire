package com.ire.database;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

public class DatabaseWrapperMain {

    private static final Logger LOGGER = LogManager.getLogger("database-wrapper");

    /**
     * TODO: Better pattern for executing bash commands is needed
     * @param args
     */
    public static void main(final String... args) {

        LOGGER.info("Arguments passed: {}", Arrays.toString(args));

        final DatabaseWrapperConfig databaseWrapperConfig = new DatabaseWrapperConfig();
        final String task = args[0];

        try {
            switch (task) {

                case "DEPLOY":
                    deployDb(databaseWrapperConfig);
                    break;
                case "START":
                    throw new UnsupportedOperationException("Implement me");
                case "STOP":
                    throw new UnsupportedOperationException("Implement me");
                case "MIGRATE":
                    throw new UnsupportedOperationException("Implement me");
                case "RESET":
                    throw new UnsupportedOperationException("Implement me");
                default:
                    throw new IllegalArgumentException("DatabaseWrapper task is not valid: [" + task + "]. Valid tasks" +
                            " are: DEPLOY, START, STOP, MIGRATE, RESET");
            }

        } catch (final IllegalArgumentException e) {
            LOGGER.error("Caught Exception", e);
            throw e;
        }
    }

    private static void deployDb(DatabaseWrapperConfig config) {
        List<String> cmd = new ArrayList<>();

        // If we're deplying, we need to make sure there isnt a data dir and postgres is not running
        cmd.add("rm -r " + config.getDataDir());
        cmd.add("pkill postgres");

        cmd.add("mkdir -p " + config.getLogDir());
        cmd.add("mkdir -p " + config.getDataDir());

        cmd.add(config.getBinDir() + "/initdb -D " + config.getDataDir() + " -U " + config.getRole() + " -L " + config.getHomeDir() + "/share/");

        cmd.add(config.getBinDir() + "/pg_ctl start -w -D " + config.getDataDir() + " -l " + config.getLogFile());

        LOGGER.info("Running Task: DEPLOY with arguments: {}", cmd);
        BashProcess.command(cmd, LOGGER).execute();

        LOGGER.info("Running Task: DEPLOY with arguments: {}", cmd);

        while(true) {
            File dir = new File(config.getDataDir() + "/global");
            File[] files = dir.listFiles((dir1, name) -> name.startsWith("pg_filenode") && name.endsWith(".map"));
            if(files != null && files.length > 0) {
                break;
            }
            else {
                LockSupport.parkNanos(TimeUnit.SECONDS.toNanos(1));
            }
        }

        cmd.clear();

        cmd.add(config.getBinDir() + "/createdb -O " + config.getRole() + " -U " + config.getRole() +" -e " + config.getDbName());

        cmd.add(config.getBinDir() + "/psql -U " + config.getRole() + " -d " + config.getDbName() + " -f " + config.getSqlFilePath());
        LOGGER.info("Running Task: DEPLOY with arguments: {}", cmd);
        BashProcess.command(cmd, LOGGER).execute();

    }
}
