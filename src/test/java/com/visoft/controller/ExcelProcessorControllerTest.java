package com.visoft.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.visoft.AbstractTest;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.File;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.fileUpload;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class ExcelProcessorControllerTest extends AbstractTest{

    private MockMvc mockMvc;

    private String fileName;
    private String fileMimeType;
    private byte [] bytes;

    private File excelFile;

    @Value("${test.data.excel.file}")
    String pathToExcel;
    @Value("${test.data.modified.excel.file}")
    String pathToModifiedExcel;

    @Autowired
    private WebApplicationContext wac;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();

        excelFile = new File(pathToExcel);

        fileName = "excel.xlsx";
        fileMimeType = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
        bytes = readBytesFormFile(excelFile);
    }

    @Test
    public void testFitToOnePage() throws JsonProcessingException {

        String json = "{\"fileName\":\"excelFileName.xlsx\", \"mimeType\":\"application/vnd.openxmlformats-officedocument.spreadsheetml.sheet\",\"filePath\":\"\"}";

        MockMultipartFile file = new MockMultipartFile("file", fileName, fileMimeType, bytes);
        MockMultipartFile jsonPart = new MockMultipartFile("json", null, null, json.getBytes());

        try {
            MvcResult result = mockMvc.perform(
                    fileUpload("/processExcel/uploadFile/fitToOnePageByColumnsAndRows")
                    .file(file)
                    .param("json", json))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                    .andExpect(header().string("content-disposition", "form-data; name=\"attachment\"; filename=\"excelFileName.xlsx\""))
                    .andReturn();

            saveBytesAsFile(pathToModifiedExcel, result.getResponse().getContentAsByteArray());
        } catch (Exception ex) {
            LOG.error("Error: " + ex.getMessage());
        }
    }
}
