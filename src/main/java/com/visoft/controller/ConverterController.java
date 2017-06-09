package com.visoft.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.visoft.entity.JsonFileData;
import com.visoft.exception.RestException;
import com.visoft.entity.excel.ExcelProcessor;
import com.visoft.entity.jod.converter.JodConverter;
import org.apache.commons.codec.binary.Base64;
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
            @RequestPart("json") String jsonString,
            @RequestPart("file") MultipartFile multipartFile) throws RestException {
        byte [] outputByteArray = null;
        JsonFileData json = null;
        if (!multipartFile.isEmpty()) {
            try {
                byte[] bytes = null;
                boolean isBase64 = Base64.isBase64(multipartFile.getBytes());
                if (isBase64) {
                    bytes = Base64.decodeBase64(multipartFile.getBytes());
                } else {
                    bytes = multipartFile.getBytes();
                }
                json = new ObjectMapper().readValue(jsonString, JsonFileData.class);
                outputByteArray = jodConverter.convertToPdf(json.getFileName(), json.getMimeType(), bytes);
            } catch (Exception e) {
                throw new RestException(e);
            }
        }
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentDispositionFormData("attachment", FilenameUtils.getBaseName(json.getFileName()) + ".pdf");
        headers.setContentType(MediaType.APPLICATION_PDF);
        return new ResponseEntity<>(outputByteArray, headers, HttpStatus.OK);
    }

    @RequestMapping(value = "/convertExcelToPdf",
                    method = RequestMethod.POST)
    public String convertExcelToPdfAndSavePdf(@RequestBody JsonFileData json) throws RestException {
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

}
