package com.visoft.entity.excel;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.*;

@Component
public class ExcelProcessor {

    private final Logger LOG = LoggerFactory.getLogger(this.getClass());

    @Autowired
    ExcelReader excelReader;

    @Autowired
    ExcelWriter excelWriter;

    public byte [] processFileFitToOnePage(String fileName, byte [] bytes) {

        File outputFile = null;
        try {
            outputFile = File.createTempFile("document", "." + FilenameUtils.getExtension(fileName));
            FileOutputStream outputFileStream = null;
            try {
                outputFileStream = new FileOutputStream(outputFile);
                IOUtils.copy(new BufferedInputStream(new ByteArrayInputStream(bytes)), outputFileStream);
            } finally {
                IOUtils.closeQuietly(outputFileStream);
            }
            return this.processFitToOnePage(outputFile);
        } catch (IOException ex) {
            LOG.error("Error: can not create file from bytes: " + ex.getMessage());
        } finally {
            if(outputFile != null) {
                outputFile.delete();
            }
        }
        return bytes;
    }

    public byte [] processFileFitToOnePage(File file) {
        return this.processFitToOnePage(file);
    }

    private byte [] processFitToOnePage(File file) {
        File outputFile = null;
        try {
            outputFile = File.createTempFile("document", "." + FilenameUtils.getExtension(file.getCanonicalPath()));
            FileOutputStream outputFileStream = null;
            try (InputStream inputStream = new FileInputStream(file)){
                outputFileStream = new FileOutputStream(outputFile);
                IOUtils.copy(inputStream, outputFileStream);
            } finally {
                IOUtils.closeQuietly(outputFileStream);
            }
            try {
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                // process
                Workbook workbook = excelReader.readExcelFile(outputFile);
                workbook = fitFirstSheetToOnePage(workbook);

                excelWriter.writeToFile(workbook, file);

                workbook.write(outputStream);
                return outputStream.toByteArray();
            } catch (IOException ex) {
                throw new IOException();
            } catch (Exception ex) {
                throw new IOException();
            }
        } catch (IOException ex) {
            LOG.error("Error: can not modify file: " + ex.getMessage());
        } finally {
            if(outputFile != null) {
                outputFile.delete();
            }
        }
        return null;
    }

    private Workbook fitFirstSheetToOnePage(final Workbook workbook) {
        Workbook wb = workbook;
        Sheet firstSheet = wb.getSheetAt(0);

        firstSheet.setFitToPage(true);
        PrintSetup printSetup = firstSheet.getPrintSetup();
        printSetup.setFitWidth((short) 1);
        printSetup.setFitHeight((short) 1);

        return wb;
    }

    public byte [] processFileAddLogo(String fileName, byte [] bytes) {

        File outputFile = null;
        try {
            outputFile = File.createTempFile("document", "." + FilenameUtils.getExtension(fileName));
            FileOutputStream outputFileStream = null;
            try {
                outputFileStream = new FileOutputStream(outputFile);
                IOUtils.copy(new BufferedInputStream(new ByteArrayInputStream(bytes)), outputFileStream);
            } finally {
                IOUtils.closeQuietly(outputFileStream);
            }
//            return this.processAddLogo(outputFile);
        } catch (IOException ex) {
            LOG.error("Error: can not create file from bytes: " + ex.getMessage());
        } finally {
            if(outputFile != null) {
                outputFile.delete();
            }
        }
        return bytes;
    }

    public byte [] processFileAddLogo(File file, Integer logoRow, Integer logoCell, String logoPath) {
        return this.processAddLogo(file, logoRow, logoCell, logoPath);
    }

    private byte [] processAddLogo(File file, Integer logoRow, Integer logoCell, String logoPath) {
        File outputFile = null;
        try {
            outputFile = File.createTempFile("document", "." + FilenameUtils.getExtension(file.getCanonicalPath()));
            FileOutputStream outputFileStream = null;
            try (InputStream inputStream = new FileInputStream(file)){
                outputFileStream = new FileOutputStream(outputFile);
                IOUtils.copy(inputStream, outputFileStream);
            } finally {
                IOUtils.closeQuietly(outputFileStream);
            }
            try {
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                // process
                Workbook workbook = excelReader.readExcelFile(outputFile);
                workbook = addLogo(workbook, logoRow, logoCell, logoPath);

                excelWriter.writeToFile(workbook, file);

                workbook.write(outputStream);
                return outputStream.toByteArray();
            } catch (IOException ex) {
                throw new IOException();
            } catch (Exception ex) {
                throw new IOException();
            }
        } catch (IOException ex) {
            LOG.error("Error: can not modify file: " + ex.getMessage());
        } finally {
            if(outputFile != null) {
                outputFile.delete();
            }
        }
        return null;
    }

    private Workbook addLogo(final Workbook workbook, Integer startRow, Integer startCol, String imgPath) throws IOException {
        Workbook wb = workbook;
        Sheet sheet = wb.getSheetAt(wb.getNumberOfSheets()-1);
        InputStream inputStream = new FileInputStream(imgPath);
        byte[] bytes = IOUtils.toByteArray(inputStream);
        int picIndex = workbook.addPicture(bytes, Workbook.PICTURE_TYPE_PNG);
        inputStream.close();
        //Returns an object that handles instantiating concrete classes
        CreationHelper helper = wb.getCreationHelper();
        //Creates the top-level drawing patriarch.
        Drawing drawing = sheet.createDrawingPatriarch();
        //Create an anchor that is attached to the worksheet
        ClientAnchor anchor = helper.createClientAnchor();
        //Locate picture on whole cell
        anchor.setCol1(startCol);
        anchor.setRow1(startRow);
        anchor.setCol2(startCol + 1);
        anchor.setRow2(startRow + 1);
        anchor.setAnchorType(ClientAnchor.AnchorType.MOVE_AND_RESIZE);
        Picture picture = drawing.createPicture(anchor, picIndex);
        //picture.resize(width, height);
        return wb;
    }

    public byte [] processFileChangeDirectionRightToLeft(String fileName, byte [] bytes) {

        File outputFile = null;
        try {
            outputFile = File.createTempFile("document", "." + FilenameUtils.getExtension(fileName));
            FileOutputStream outputFileStream = null;
            try {
                outputFileStream = new FileOutputStream(outputFile);
                IOUtils.copy(new BufferedInputStream(new ByteArrayInputStream(bytes)), outputFileStream);
            } finally {
                IOUtils.closeQuietly(outputFileStream);
            }
            return this.processChangeDirectionRightToLeft(outputFile);
        } catch (IOException ex) {
            LOG.error("Error: can not create file from bytes: " + ex.getMessage());
        } finally {
            if(outputFile != null) {
                outputFile.delete();
            }
        }
        return bytes;
    }

    public byte [] processFileChangeDirectionRightToLeft(File file) {
        return this.processChangeDirectionRightToLeft(file);
    }

    private byte [] processChangeDirectionRightToLeft(File file) {
        File outputFile = null;
        try {
            outputFile = File.createTempFile("document", "." + FilenameUtils.getExtension(file.getCanonicalPath()));
            FileOutputStream outputFileStream = null;
            try (InputStream inputStream = new FileInputStream(file)){
                outputFileStream = new FileOutputStream(outputFile);
                IOUtils.copy(inputStream, outputFileStream);
            } finally {
                IOUtils.closeQuietly(outputFileStream);
            }
            try {
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                // process
                Workbook workbook = excelReader.readExcelFile(outputFile);
                workbook = changeDirectionRightToLeft(workbook);
                try {
                    workbook.write(outputStream);
                } finally {
                    outputStream.close();
                }
                excelWriter.writeToFile(workbook, file);

                return outputStream.toByteArray();
            } catch (IOException ex) {
                throw new IOException();
            } catch (Exception ex) {
                throw new IOException();
            }
        } catch (IOException ex) {
            LOG.error("Error: can not modify file: " + ex.getMessage());
        } finally {
            if(outputFile != null) {
                outputFile.delete();
            }
        }
        return null;
    }

    private Workbook changeDirectionRightToLeft(final Workbook workbook) {
        XSSFWorkbook wb = (XSSFWorkbook) workbook;
        XSSFSheet firstSheet;
        for (int i = 0; i < wb.getNumberOfSheets(); i++) {
            firstSheet = wb.getSheetAt(i);
            firstSheet.getCTWorksheet().getSheetViews().getSheetViewArray(0).setRightToLeft(true);
        }

        return wb;
    }

}
