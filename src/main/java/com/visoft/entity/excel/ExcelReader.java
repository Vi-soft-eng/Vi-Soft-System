package com.visoft.entity.excel;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.*;

@Component
public class ExcelReader {

    private final Logger LOG = LoggerFactory.getLogger(this.getClass());

    public Workbook readExcelFile(File file) {
        try (InputStream inputStream = new FileInputStream(file)) {
            // Get the workbook instance for XLSX file
            Workbook workbook = WorkbookFactory.create(inputStream);
            if (file.isFile() && file.exists()) {
                LOG.info("The file " + file.getAbsolutePath() + " open successfully.");
            } else {
                throw new IOException("Error to open: " + file.getAbsolutePath() + " file.");
            }
            return workbook;
        } catch (IOException ex) {
            LOG.error("Error: can not read EXCEL file: " + ex.getMessage());
        } catch (Exception ex) {
            LOG.error("Error: can not read EXCEL file: " + ex.getMessage());
        }
        return null;
    }

    public Workbook readExcelFile(byte [] bytes) {
        try (InputStream inputStream =  new ByteArrayInputStream(bytes); ) {
            // Get the workbook instance for XLSX file
            Workbook workbook = WorkbookFactory.create(inputStream);
            return workbook;
        } catch (IOException ex) {
            LOG.error("Error: can not convert bytes to EXCEL: " + ex.getMessage());
        } catch (Exception ex) {
            LOG.error("Error: can not convert bytes to EXCEL: " + ex.getMessage());
        }
        return null;
    }
}
