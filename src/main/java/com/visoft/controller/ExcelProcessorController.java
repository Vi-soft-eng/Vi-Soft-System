package com.visoft.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.visoft.entity.JsonFileData;
import com.visoft.entity.JsonLogoData;
import com.visoft.entity.excel.ExcelProcessor;
import com.visoft.exception.RestException;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;

@RestController
@RequestMapping(value = "processExcel")
public class ExcelProcessorController extends ExceptionHandlerController  {

    @Autowired
    ExcelProcessor excelProcessor;

    @RequestMapping(value = "/uploadFile/fitToOnePage",
                    method = RequestMethod.POST,
                    produces = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
    public @ResponseBody ResponseEntity<byte []> fitToOnePage (
            @RequestPart(value = "json") String jsonString,
            @RequestPart(value = "file") MultipartFile multipartFile) throws RestException {
        byte[] outputByteArray = null;
        JsonFileData json = null;
        if (!multipartFile.isEmpty()) {
            try {
                byte[] bytes;
                boolean isBase64 = Base64.isBase64(multipartFile.getBytes());
                if (isBase64) {
                    bytes = Base64.decodeBase64(multipartFile.getBytes());
                } else {
                    bytes = multipartFile.getBytes();
                }
                json = new ObjectMapper().readValue(jsonString, JsonFileData.class);
                // process excel file: fit data to one page
                outputByteArray = excelProcessor.processFileFitToOnePage(json.getFileName(), bytes);
            } catch (Exception e) {
                throw new RestException(e);
            }
        }
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentDispositionFormData("attachment", json.getFileName());
        headers.setContentLength(outputByteArray.length);
        return new ResponseEntity<>(outputByteArray, headers, HttpStatus.OK);
    }

    @RequestMapping(value = "/changeFile/fitToOnePage",
            method = RequestMethod.POST)
    public String fitToOnePage(@RequestBody JsonFileData json) throws RestException {
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

    @RequestMapping(value = "/uploadFile/fitToOnePageByColumnsAndRows",
            method = RequestMethod.POST,
            produces = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
    public @ResponseBody ResponseEntity<byte []> fitToOnePageByColumnsAndRows (
            @RequestParam(value = "json") String jsonString,
            @RequestPart(value = "file") MultipartFile multipartFile) throws RestException {
        byte[] outputByteArray = null;

        JsonFileData json = null;
        if (!multipartFile.isEmpty()) {
            try {
                byte[] bytes;
                boolean isBase64 = Base64.isBase64(multipartFile.getBytes());
                if (isBase64) {
                    bytes = Base64.decodeBase64(multipartFile.getBytes());
                } else {
                    bytes = multipartFile.getBytes();
                }
                json = new ObjectMapper().readValue(jsonString, JsonFileData.class);
                // process excel file: fit data to one page
                outputByteArray = excelProcessor.processFileFitToOnePageByColumnsAndRows(json.getFileName(), bytes);
            } catch (Exception e) {
                throw new RestException(e);
            }
        }
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentDispositionFormData("attachment", json.getFileName());
        headers.setContentLength(outputByteArray.length);
        return new ResponseEntity<>(outputByteArray, headers, HttpStatus.OK);
    }

    @RequestMapping(value = "/changeFile/fitToOnePageByColumnsAndRows",
            method = RequestMethod.POST)
    public String fitToOnePageByColumnsAndRows(@RequestBody JsonFileData json) throws RestException {
        byte[] bytes = null;
        if (json.getFilePath() != null) {
            try {
                // process excel file: fit data to one page
                bytes = excelProcessor.processFileFitToOnePageByColumnsAndRows(new File(json.getFilePath() + json.getFileName()));
            } catch (Exception e) {
                throw new RestException(e);
            }
        }
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.TEXT_PLAIN);
        return "File processed successfully";
    }

    @RequestMapping(value = "/uploadFile/addLogo",
                    method = RequestMethod.POST,
                    produces = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
    public @ResponseBody ResponseEntity<byte []> addLogo (
            @RequestPart(value = "json") String jsonString,
            @RequestPart(value = "file") MultipartFile multipartFile) throws RestException {
        byte[] outputByteArray = null;
        JsonLogoData json = null;
        if (!multipartFile.isEmpty()) {
            try {
                byte[] bytes;
                boolean isBase64 = Base64.isBase64(multipartFile.getBytes());
                if (isBase64) {
                    bytes = Base64.decodeBase64(multipartFile.getBytes());
                } else {
                    bytes = multipartFile.getBytes();
                }
                json = new ObjectMapper().readValue(jsonString, JsonLogoData.class);
                // process excel file: fit data to one page
                outputByteArray = excelProcessor.processFileAddLogo(
                        FilenameUtils.getName(json.getExcelFilePath()),
                        bytes,
                        json.getLogoRow(),
                        json.getLogoCol(),
                        json.getLogoRow2(),
                        json.getLogoCol2(),
                        json.getLogoPath());
            } catch (Exception e) {
                throw new RestException(e);
            }
        }
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentDispositionFormData("attachment", FilenameUtils.getName(json.getExcelFilePath()));
        headers.setContentLength(outputByteArray.length);
        return new ResponseEntity<>(outputByteArray, headers, HttpStatus.OK);
    }

    @RequestMapping(value = "/changeFile/addLogo",
                    method = RequestMethod.POST)
    public String addLogo(@RequestBody JsonLogoData json) throws RestException {
        byte[] bytes = null;
        if (json.getExcelFilePath() != null) {
            try {
                // process excel file: fit data to one page
                bytes = excelProcessor.processFileAddLogo(
                        new File(json.getExcelFilePath()),
                        json.getLogoRow(),
                        json.getLogoCol(),
                        json.getLogoRow2(),
                        json.getLogoCol2(),
                        json.getLogoPath());
            } catch (Exception e) {
                throw new RestException(e);
            }
        }
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.TEXT_PLAIN);
        return "File processed successfully";
    }

    @RequestMapping(value = "/uploadFile/changeDirectionRightToLeft",
                    method = RequestMethod.POST,
                    produces = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
    public @ResponseBody ResponseEntity<byte []> getBlobChangeDirectionRightToLeft (
            @RequestPart(value = "json") String jsonString,
            @RequestPart(value = "file") MultipartFile multipartFile) throws RestException {
        byte[] outputByteArray = null;
        JsonFileData json = null;
        if (!multipartFile.isEmpty()) {
            try {
                byte[] bytes;
                boolean isBase64 = Base64.isBase64(multipartFile.getBytes());
                if (isBase64) {
                    bytes = Base64.decodeBase64(multipartFile.getBytes());
                } else {
                    bytes = multipartFile.getBytes();
                }
                json = new ObjectMapper().readValue(jsonString, JsonFileData.class);
                // process excel file: fit data to one page
                outputByteArray = excelProcessor.processFileChangeDirectionRightToLeft(json.getFileName(), bytes);
            } catch (Exception e) {
                throw new RestException(e);
            }
        }
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentDispositionFormData("attachment", json.getFileName());
        headers.setContentLength(outputByteArray.length);
        return new ResponseEntity<>(outputByteArray, headers, HttpStatus.OK);
    }

    @RequestMapping(value = "/changeFile/changeDirectionRightToLeft",
                    method = RequestMethod.POST)
    public String processFileChangeDirectionRightToLeft(@RequestBody JsonFileData json) throws RestException {
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
