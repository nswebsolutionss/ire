package com.orchestrator.tasks;

//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

import static com.orchestrator.tasks.TaskUtils.handleDoWork;

public class PostgresTask implements Task {

    private static final Logger LOGGER = LogManager.getLogger(PostgresTask.class);
    private static final List<String> cmd = new ArrayList<>();
    private static final String CURRENT_DIR = System.getProperty("user.dir");
    private static final String LOG_PARENT_DIR = CURRENT_DIR + "/log";
    private static final String LOG_DIR = LOG_PARENT_DIR + "/database";

    DatabaseConfig config = new DatabaseConfig();


    @Override
    public void createStartTask() {

        cmd.add(CURRENT_DIR + "/opt/pgsql/bin/pg_ctl" + " " +
                "-l " + LOG_DIR + "/database-log " +
                "-D " + CURRENT_DIR + "/opt/pgsqlData/data " +
                "start"
        );
        LOGGER.info("Running task: 'createStartTask' for service: 'POSTGRESQL'");
    }

    @Override
    public void createInstallTask() {
        cmd.add("mkdir -p " + LOG_DIR);
        cmd.add(CURRENT_DIR + "/opt/pgsql/bin/initdb -U " + config.getRole() + " -D " +
                CURRENT_DIR + "/opt/pgsqlData/data -L " +
                CURRENT_DIR + "/opt/pgsql/share/"
        );
        LOGGER.info("Running task: 'createInstallTask' for service: 'POSTGRESQL'");
    }

    @Override
    public void createDeployTask() {
        cmd.add(CURRENT_DIR + "/opt/pgsql/bin/createdb -O " + config.getRole() + " -U " + config.getRole() + " -e " + config.getName());
        cmd.add(CURRENT_DIR + "/opt/pgsql/bin/psql -U " + config.getRole() + " -d " + config.getName() + " -f " +  CURRENT_DIR + "/releases/orchestrator/src/main/resources/cleaning_platform.sql");
        LOGGER.info("Running task: 'createDeployTask' for service: 'POSTGRESQL'");
    }

    @Override
    public void createStopTask() {
    }


    @Override
    public void doWork() throws RuntimeException {
        handleDoWork(cmd, LOGGER);
        cmd.clear();
    }

}
