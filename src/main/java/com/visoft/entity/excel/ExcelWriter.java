/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.visoft.entity.excel;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Component;

@Component
public class ExcelWriter {
    public void writeToFile(Workbook workbook, File file) throws IOException {
        FileOutputStream outputExcelFile;
        outputExcelFile = new FileOutputStream(new File(file.getPath()));
        workbook.write(outputExcelFile);
        if (outputExcelFile != null) {
            outputExcelFile.flush();
            outputExcelFile.close();
        }
    }
}
