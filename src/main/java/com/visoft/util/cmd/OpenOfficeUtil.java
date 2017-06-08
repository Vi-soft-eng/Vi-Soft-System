package com.visoft.util.cmd;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

@Component
public class OpenOfficeUtil {

    @Autowired
    CommandLine cmd;

    @Value("${open.office.run.headless.mode.command}")
    private String runOpenOfficeCommand;

    @Value("${open.office.process.name}")
    private String openOfficeProcessName;

    public void runOpenOffice () throws IOException, InterruptedException {
        if (!isProcessRunning(openOfficeProcessName)) {
            execCommand(runOpenOfficeCommand);
        }
    }

    private boolean isProcessRunning (String processName) throws IOException, InterruptedException {
        ProcessBuilder processBuilder = new ProcessBuilder("tasklist.exe");
        Process process = processBuilder.start();
        String tasksList = toString(process.getInputStream());

        return tasksList.contains(processName);
    }

    private String toString (InputStream inputStream) {
        Scanner scanner = new Scanner(inputStream, "UTF-8").useDelimiter("\\A");
        String string = scanner.hasNext() ? scanner.next() : "";
        scanner.close();

        return string;
    }

    private void execCommand(String cmdCommand) {
        cmd.execCommand(cmdCommand);
    }
}
