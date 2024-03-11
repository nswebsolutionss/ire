package com.orchestrator.tasks;

import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class TaskUtils {

    public static void handleDoWork(final List<String> cmds, final Logger logger) {
        StringBuilder outputBuilder = new StringBuilder();
        StringBuilder errorBuilder = new StringBuilder();
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
                        if (outputBuilder.length() > 0) {
                            logger.info(outputBuilder);
                        }
                        if (errorBuilder.length() > 0) {
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
        for (byte b : exitVal.getInputStream().readAllBytes()) {
            outputBuilder.append((char) b);
        }
        for (byte b : exitVal.getErrorStream().readAllBytes()) {
            errorBuilder.append((char) b);
        }
    }
}
