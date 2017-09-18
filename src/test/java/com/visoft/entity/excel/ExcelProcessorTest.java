package com.visoft.entity.excel;

import com.visoft.AbstractTest;
import org.apache.commons.codec.binary.Base64;
import org.apache.poi.ss.usermodel.PrintSetup;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.io.File;
import java.io.IOException;

public class ExcelProcessorTest extends AbstractTest{

    @Autowired
    ExcelProcessor excelProcessor;

    @Autowired
    ExcelReader excelReader;

    @Value("${test.data.excel.file}")
    String pathToExcel;
    @Value("${test.data.modified.excel.file}")
    String pathToModifiedExcel;

    private File excelFile;

    @Before
    public void setUp() {
        excelFile = new File(pathToExcel);
    }

    @Test
    public void testFitToOnePageByColumnsFromFile() {

        byte [] bytes = excelProcessor.processFileFitToOnePage(excelFile);

        saveBytesAsFile(pathToModifiedExcel, bytes);

        Sheet firstSheet = readExcel(new File(pathToModifiedExcel)).getSheetAt(0);
        Assert.assertTrue(firstSheet.getFitToPage());

        PrintSetup printSetup = firstSheet.getPrintSetup();
        Assert.assertEquals((short) 1, printSetup.getFitWidth());
        Assert.assertEquals((short) 0, printSetup.getFitHeight());
    }

    @Test
    public void testFitToOnePageByColumnsFromByteArray() {

        byte [] bytes  = readBytesFormFile(excelFile);
        bytes = excelProcessor.processFileFitToOnePage(excelFile.getName(), bytes);

        saveBytesAsFile(pathToModifiedExcel, bytes);

        Sheet firstSheet = readExcel(bytes).getSheetAt(0);
        Assert.assertTrue(firstSheet.getFitToPage());

        PrintSetup printSetup = firstSheet.getPrintSetup();
        Assert.assertEquals((short) 1, printSetup.getFitWidth());
        Assert.assertEquals((short) 0, printSetup.getFitHeight());
    }

    @Test
    public void testFitToOnePageByColumnsAndRowsFromFile() {

        byte [] bytes = excelProcessor.processFileFitToOnePageByColumnsAndRows(excelFile);

        saveBytesAsFile(pathToModifiedExcel, bytes);

        Sheet firstSheet = readExcel(new File(pathToModifiedExcel)).getSheetAt(0);
        Assert.assertTrue(firstSheet.getFitToPage());

        PrintSetup printSetup = firstSheet.getPrintSetup();
        Assert.assertEquals((short) 1, printSetup.getFitWidth());
        Assert.assertEquals((short) 1, printSetup.getFitHeight());
    }

    @Test
    public void testFitToOnePageByColumnsBase64EncodedByteArray() {

        byte [] bytes = Base64.encodeBase64(readBytesFormFile(excelFile));
        Assert.assertTrue(Base64.isBase64(bytes));

        bytes = Base64.decodeBase64(bytes);
        Assert.assertFalse(Base64.isBase64(bytes));

        bytes = excelProcessor.processFileFitToOnePage(excelFile.getName(), bytes);

        saveBytesAsFile(pathToModifiedExcel, bytes);

        Sheet firstSheet = readExcel(bytes).getSheetAt(0);
        Assert.assertTrue(firstSheet.getFitToPage());

        PrintSetup printSetup = firstSheet.getPrintSetup();
        Assert.assertEquals((short) 1, printSetup.getFitWidth());
        Assert.assertEquals((short) 0, printSetup.getFitHeight());
    }

    @Test
    public void testFitToOnePageByColumnsAndRowsFromByteArray() {

        byte [] bytes  = readBytesFormFile(excelFile);
        bytes = excelProcessor.processFileFitToOnePageByColumnsAndRows(excelFile.getName(), bytes);

        saveBytesAsFile(pathToModifiedExcel, bytes);

        Sheet firstSheet = readExcel(bytes).getSheetAt(0);
        Assert.assertTrue(firstSheet.getFitToPage());

        PrintSetup printSetup = firstSheet.getPrintSetup();
        Assert.assertEquals((short) 1, printSetup.getFitWidth());
        Assert.assertEquals((short) 1, printSetup.getFitHeight());
    }

    @Test
    public void testFitToOnePageByColumnsAndRowsFromBase64EncodedByteArray() {

        byte [] bytes = Base64.encodeBase64(readBytesFormFile(excelFile));
        Assert.assertTrue(Base64.isBase64(bytes));

        bytes = Base64.decodeBase64(bytes);
        Assert.assertFalse(Base64.isBase64(bytes));

        bytes = excelProcessor.processFileFitToOnePageByColumnsAndRows(excelFile.getName(), bytes);

        saveBytesAsFile(pathToModifiedExcel, bytes);

        Sheet firstSheet = readExcel(bytes).getSheetAt(0);
        Assert.assertTrue(firstSheet.getFitToPage());

        PrintSetup printSetup = firstSheet.getPrintSetup();
        Assert.assertEquals((short) 1, printSetup.getFitWidth());
        Assert.assertEquals((short) 1, printSetup.getFitHeight());
    }

    private Workbook readExcel(byte [] bytes) {
        return excelReader.readExcelFile(bytes);
    }

    private Workbook readExcel(File file) {
        return excelReader.readExcelFile(file);
    }

}
