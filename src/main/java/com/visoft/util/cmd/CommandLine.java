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
            status = process.waitFor();
            result = IOUtils.toString(process.getErrorStream(), "utf-8");
        } catch (InterruptedException ex) {
            throw new RuntimeException("Could not execute: " + command, ex);
        } catch (IOException e) {
            throw new RuntimeException("Could not convert: " + command, e);
        }
        if (status != 0) {
            throw new RuntimeException("Conversion failed with status code: " + status + ". Command executed: " + command + " and result: " + result);
        }
    }
}
