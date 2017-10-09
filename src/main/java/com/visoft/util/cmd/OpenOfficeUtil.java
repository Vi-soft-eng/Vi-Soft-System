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

    private final String TASK_LIST = "tasklist.exe";

    public void runOpenOffice () throws IOException, InterruptedException {
        if (!isOpenOfficeRunning()) {
            runApplication(runOpenOfficeCommand);
        }
    }

    public boolean isOpenOfficeRunning() throws IOException, InterruptedException {
        ProcessBuilder processBuilder = new ProcessBuilder(TASK_LIST);
        Process process = processBuilder.start();
        String tasksList = toString(process.getInputStream());
        return tasksList.contains(openOfficeProcessName);
    }

    private String toString (InputStream inputStream) {
        Scanner scanner = new Scanner(inputStream, "UTF-8").useDelimiter("\\A");
        String string = scanner.hasNext() ? scanner.next() : "";
        scanner.close();
        return string;
    }

    private void runApplication(String cmdCommand) {
        cmd.runApplicationCommand(cmdCommand);
    }
}
