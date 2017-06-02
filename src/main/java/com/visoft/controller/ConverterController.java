package com.visoft.controller;

import com.visoft.entity.JsonFileData;
import com.visoft.exception.RestException;
import com.visoft.entity.excel.ExcelProcessor;
import com.visoft.entity.jod.converter.JodConverter;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

@RestController
public class ConverterController extends ExceptionHandlerController  {

    private final Logger LOG = LoggerFactory.getLogger(this.getClass());

    @Autowired
    ExcelProcessor excelProcessor;

    @Autowired
    JodConverter jodConverter;

    @RequestMapping(value = "/convertBlobToPdf",
                    method = RequestMethod.POST,
                    produces = "application/pdf")
    public @ResponseBody ResponseEntity<byte[]> downloadPdf(
            @RequestPart("fileName") String fileName,
            @RequestPart(required = false) String mimeType,
            @RequestPart("file") MultipartFile multipartFile) throws RestException {

        byte [] outputByteArray = null;
        if (!multipartFile.isEmpty()) {
            try {
                byte[] bytes = multipartFile.getBytes();

                // process excel file: fit data to one page
                bytes = excelProcessor.processFileFitToOnePage(fileName, bytes);

                outputByteArray = jodConverter.convertToPdf(fileName, mimeType, bytes);
            } catch (Exception e) {
                throw new RestException(e);
            }
        }

        final HttpHeaders headers = new HttpHeaders();
        headers.setContentDispositionFormData("attachment", FilenameUtils.getBaseName(fileName) + ".pdf");
        headers.setContentType(MediaType.APPLICATION_PDF);
        return new ResponseEntity<>(outputByteArray, headers, HttpStatus.OK);
    }

    @RequestMapping(value = "/convertExcelToPdf",
                    method = RequestMethod.POST,
                    produces = "application/pdf")
    public @ResponseBody ResponseEntity<byte[]> downloadPdf(
            @RequestPart("fileName") String fileName,
            @RequestPart("mimeType") String mimeType,
            @RequestPart("filePath") String filePath) throws RestException {
        byte[] bytes = null;
        if (filePath != null) {
            try {
                // process excel file: fit data to one page
                bytes = excelProcessor.processFileFitToOnePage(new File(filePath));
                bytes = jodConverter.convertToPdf(fileName, mimeType, bytes);
            } catch (Exception e) {
                throw new RestException(e);
            }
        }
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentDispositionFormData("attachment", FilenameUtils.getBaseName(fileName) + ".pdf");
        headers.setContentType(MediaType.APPLICATION_PDF);
        return new ResponseEntity<>(bytes, headers, HttpStatus.OK);
    }

    @RequestMapping(value = "/convertExcelToPdf",
            method = RequestMethod.POST)
    public String convertExcelToPdfAndSavePdf(
            @RequestBody JsonFileData json) throws RestException {
        byte[] bytes = null;
        if (json.getFilePath() != null) {
            try {
                String outputFileName = json.getFilePath() + FilenameUtils.getBaseName(json.getFileName()) + ".pdf";
                jodConverter.convertToPdfAndSave( new File(json.getFilePath() + json.getFileName()), outputFileName);
            } catch (Exception e) {
                throw new RestException(e);
            }
        }
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.TEXT_PLAIN);
        return "File converted successfully";
    }

    @RequestMapping(value = "/convertExcel2003ToExcel2007",
            method = RequestMethod.POST,
            produces = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
    public @ResponseBody ResponseEntity<byte[]> downloadExcel(
            @RequestPart("fileName") String fileName,
            @RequestPart(required = false) String mimeType,
            @RequestPart("filePath") String filePath) throws RestException {
        byte[] bytes = null;
        if (filePath != null) {
            try {
                bytes = excelProcessor.processFileFitToOnePage(new File(filePath));
                bytes = jodConverter.convertXlsToXlsx(fileName, mimeType, bytes);
            } catch (Exception e) {
                throw new RestException(e);
            }
        }
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentDispositionFormData("attachment", FilenameUtils.getBaseName(fileName) + ".xlsx");
        return new ResponseEntity<>(bytes, headers, HttpStatus.OK);
    }
}
