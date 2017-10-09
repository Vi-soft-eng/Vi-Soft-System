package com.visoft.util.cmd;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CommandLine {

    private final Logger LOG = LoggerFactory.getLogger(this.getClass());

    public void execCommand(String command) {
        int status;
        String result = "";
        try {
            LOG.info("Executing cmd command: " + command);
            Process process = Runtime.getRuntime().exec(command);
            // the thread will wait until the process be finished or stopped manually
            status = process.waitFor();
            result = IOUtils.toString(process.getErrorStream(), "utf-8");
        } catch (InterruptedException ex) {
            throw new RuntimeException("Could not execute: " + command, ex);
        } catch (IOException e) {
            throw new RuntimeException("Could not execute: " + command, e);
        }
        if (status != 0) {
            throw new RuntimeException("Execution failed with status code: " + status + ". Command executed: " + command + " and result: " + result);
        }
        LOG.info("The CMD command: " + command + " was terminated successfully.");
    }

    public void runApplicationCommand(String command) {
        boolean isAlive;
        try {
            LOG.info("Executing cmd command: " + command);
            Process process = Runtime.getRuntime().exec(command);
            isAlive = process.isAlive();
        } catch (IOException e) {
            throw new RuntimeException("Could not execute: " + command, e);
        }
        if (!isAlive) {
            throw new RuntimeException("Execution finished but needed process is not alive. Command executed: " + command);
        }
        LOG.info("The CMD command: " + command + " was terminated successfully.");
    }
}
