package com.visoft;

import org.apache.commons.io.FileUtils;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.IOException;
import java.time.format.DateTimeFormatter;

@RunWith(SpringRunner.class)
@SpringBootTest()
@ActiveProfiles("test")
public abstract class AbstractTest {

    protected Logger LOG = LoggerFactory.getLogger(this.getClass());

    protected void saveBytesAsFile(String path, byte [] bytes) {
        try {
            FileUtils.writeByteArrayToFile(new File(path), bytes);
        } catch (IOException ex) {
            LOG.error("Error : " + ex.getMessage());
        }
    }

    protected byte [] readBytesFormFile(File file) {
        try {
            return FileUtils.readFileToByteArray(file);
        } catch (IOException ex) {
            LOG.error("Error : " + ex.getMessage());
        }
        return null;
    }
}