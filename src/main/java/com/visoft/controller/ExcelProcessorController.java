package com.visoft.controller;

import com.visoft.entity.JsonFileData;
import com.visoft.entity.JsonLogoData;
import com.visoft.entity.excel.ExcelProcessor;
import com.visoft.exception.RestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
}
