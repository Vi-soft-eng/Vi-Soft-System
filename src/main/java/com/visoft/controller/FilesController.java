package com.visoft.controller;

import com.visoft.entity.JsonMoveFoldersData;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import java.io.File;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RestController;
import org.apache.commons.io.FileUtils;
import java.io.IOException;

@RestController
public class FilesController extends ExceptionHandlerController{
    private final Logger LOG = LoggerFactory.getLogger(this.getClass());

    @RequestMapping(value = "/move",
            method = RequestMethod.POST, produces={"application/json; charset=UTF-8"} )
    public String copyFolderToFolder(@RequestBody JsonMoveFoldersData json) throws IOException {

        try{
            File sourceFile = new File(json.getSourceDir());
            File targetFile = new File(json.getTargetDir());

            FileUtils.copyDirectoryToDirectory(sourceFile, targetFile);

            }
        catch (IOException e) {
            LOG.error(e.getMessage(),e);
            }
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.TEXT_PLAIN);
        return "Directory moved successfully";
    }
}
