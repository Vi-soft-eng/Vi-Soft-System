package com.visoft.controller;

import com.visoft.entity.JsonFileData;
import com.visoft.entity.JsonLogoData;
import com.visoft.entity.excel.ExcelProcessor;
import com.visoft.exception.RestException;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;

@RestController
public class ExcelProcessorController extends ExceptionHandlerController  {

    @Autowired
    ExcelProcessor excelProcessor;

    @RequestMapping(value = "/processExcelFitToOnePage",
            method = RequestMethod.POST)
    public String fitToOnePage(
            @RequestBody JsonFileData json) throws RestException {
        byte[] bytes = null;
        if (json.getFilePath() != null) {
            try {
                // process excel file: fit data to one page
                bytes = excelProcessor.processFileFitToOnePage(new File(json.getFilePath() + json.getFileName()));
            } catch (Exception e) {
                throw new RestException(e);
            }
        }
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.TEXT_PLAIN);
        return "File processed successfully";
    }

    @RequestMapping(value = "/processExcelAddLogo",
            method = RequestMethod.POST)
    public String addLogo(
            @RequestBody JsonLogoData json) throws RestException {
        byte[] bytes = null;
        if (json.getExcelFilePath() != null) {
            try {
                // process excel file: fit data to one page
                bytes = excelProcessor.processFileAddLogo(
                        new File(json.getExcelFilePath()),
                        json.getLogoRow(),
                        json.getLogoCell(),
                        json.getLogoPath());
            } catch (Exception e) {
                throw new RestException(e);
            }
        }
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.TEXT_PLAIN);
        return "File processed successfully";
    }

    @RequestMapping(value = "/getBlobChangeDirectionRightToLeft",
                    method = RequestMethod.POST,
                    produces = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
    public @ResponseBody ResponseEntity<byte []> getBlobChangeDirectionRightToLeft(
            @RequestPart(value = "fileName") String fileName,
            @RequestPart(required = false) String mimeType,
            @RequestPart("file") MultipartFile multipartFile,
            HttpServletRequest request) throws RestException {
        byte[] outputByteArray = null;

        if (!multipartFile.isEmpty()) {
            try {
                byte[] bytes = multipartFile.getBytes();
                // process excel file: fit data to one page
                outputByteArray = excelProcessor.processFileChangeDirectionRightToLeft(fileName, bytes);
            } catch (Exception e) {
                throw new RestException(e);
            }
        }

        final HttpHeaders headers = new HttpHeaders();
        headers.setContentDispositionFormData("attachment", fileName);
        headers.setContentLength(outputByteArray.length);
        return new ResponseEntity<>(outputByteArray, headers, HttpStatus.OK);
    }

    @RequestMapping(value = "/processExcelChangeDirectionRightToLeft",
            method = RequestMethod.POST)
    public String processFileChangeDirectionRightToLeft(
            @RequestBody JsonFileData json) throws RestException {
        byte[] bytes = null;
        if (json.getFilePath() != null) {
            try {
                // process excel file: fit data to one page
                bytes = excelProcessor.processFileChangeDirectionRightToLeft(new File(json.getFilePath() + json.getFileName()));
            } catch (Exception e) {
                throw new RestException(e);
            }
        }

        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.TEXT_PLAIN);
        return "File processed successfully";
    }
}
