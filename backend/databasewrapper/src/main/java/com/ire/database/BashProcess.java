package com.ire.database;

import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class BashProcess {
    StringBuilder outputBuilder = new StringBuilder();
    StringBuilder errorBuilder = new StringBuilder();

    private final List<String> cmds;
    private final Logger logger;

    public BashProcess(List<String> cmds, Logger logger) {
        this.cmds = cmds;
        this.logger = logger;
    }

    public static BashProcess command(List<String> cmd, Logger logger) {
        return new BashProcess(cmd, logger);
    }

    public void execute() {
        try
        {
            cmds.forEach(
                    cmd ->
                    {
                        try
                        {
                            Process exitVal = Runtime.getRuntime().exec(cmd).onExit().get();
                            decodeIOStreams(outputBuilder, errorBuilder, exitVal);
                        }
                        catch (IOException | InterruptedException | ExecutionException e) {
                            throw new RuntimeException(e);
                        }
                        if (!outputBuilder.isEmpty()) {
                            logger.info(outputBuilder);
                        }
                        if (!errorBuilder.isEmpty()) {
                            logger.error(errorBuilder);
                        }
                    }
            );

        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }

    public static void decodeIOStreams(StringBuilder outputBuilder, StringBuilder errorBuilder, Process exitVal) throws IOException {
        for (byte b : exitVal.getErrorStream().readAllBytes()) {
            errorBuilder.append((char) b);
        }
    }
}
