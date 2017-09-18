package com.visoft.controller;

import com.google.api.services.drive.model.File;
import com.visoft.exception.RestException;
import com.visoft.google.GoogleDriveManager;
import com.visoft.google.json.JsonGoogleFolderData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "manageGoogleDrive")
public class GoogleDriveController extends ExceptionHandlerController {

    private final Logger LOG = LoggerFactory.getLogger(this.getClass());

    @Autowired
    GoogleDriveManager googleDriveManager;
    @Value("${google.drive.default.folder.id}")
    private String googleDriveFolderId;

    @RequestMapping(value = "/getRootFolderFileList", method = RequestMethod.GET)
    public @ResponseBody ResponseEntity<List<File>> getFilesFromGoogleDrive() throws RestException {
        List<File> files;
        try {
            files = googleDriveManager.getRootFolderFiles();
        } catch (Exception ex) {
            LOG.error("Error: " + ex.getMessage());
            throw new RestException(ex);
        }

        final HttpHeaders headers = new HttpHeaders();
        return new ResponseEntity<>(files, headers, HttpStatus.OK);
    }

    @RequestMapping(value = "/getFolderFileList/{folderId}", method = RequestMethod.GET)
    public @ResponseBody ResponseEntity<List<File>> getFilesFromGoogleDriveFolder(
            @PathVariable(value = "folderId") String folderId
    ) throws RestException {
        List<File> files;
        try {
            files = googleDriveManager.getFolderFiles(folderId == null ? googleDriveFolderId : folderId);
        } catch (Exception ex) {
            LOG.error("Error: " + ex.getMessage());
            throw new RestException(ex);
        }

        final HttpHeaders headers = new HttpHeaders();
        return new ResponseEntity<>(files, headers, HttpStatus.OK);
    }

    @RequestMapping(value = "/getDefaultFolderFileList", method = RequestMethod.GET)
    public @ResponseBody ResponseEntity<List<File>> getFilesFromDefinedGoogleDriveFolder() throws RestException {
        List<File> files;
        try {
            files = googleDriveManager.getFolderFiles(googleDriveFolderId);
        } catch (Exception ex) {
            LOG.error("Error: " + ex.getMessage());
            throw new RestException(ex);
        }

        final HttpHeaders headers = new HttpHeaders();
        return new ResponseEntity<>(files, headers, HttpStatus.OK);
    }

    @RequestMapping(value = "/getFoldersByName/{folderName}", method = RequestMethod.GET)
    public @ResponseBody ResponseEntity<List<File>> getGoogleDriveFoldersByName(
            @PathVariable(value = "folderName") String folderName
    ) throws RestException {
        List<File> files;
        try {
            files = googleDriveManager.getGoogleFoldersByName(folderName, googleDriveFolderId);
        } catch (Exception ex) {
            LOG.error("Error: " + ex.getMessage());
            throw new RestException(ex);
        }

        final HttpHeaders headers = new HttpHeaders();
        return new ResponseEntity<>(files, headers, HttpStatus.OK);
    }

    @RequestMapping(value = "/getFolderById/{folderId}", method = RequestMethod.GET)
    public @ResponseBody ResponseEntity<File> getGoogleDriveFolderById(
            @PathVariable(value = "folderId") String folderId
    ) throws RestException {
        File file;
        try {
            file = googleDriveManager.getGoogleFolderById(folderId);
        } catch (Exception ex) {
            LOG.error("Error: " + ex.getMessage());
            throw new RestException(ex);
        }

        final HttpHeaders headers = new HttpHeaders();
        return new ResponseEntity<>(file, headers, HttpStatus.OK);
    }

    @RequestMapping(value = "/createFolder", method = RequestMethod.POST)
    public @ResponseBody ResponseEntity<String> getGoogleDriveFolderById(
            //{"folderName" : "New folder", "folderAdditionalParam" : "", "folderIdToCreate" : ""}
            @RequestBody JsonGoogleFolderData jsonGoogleFolderData
    ) throws RestException {
        String folderId;
        try {
            folderId = googleDriveManager.googleDriveCreateFolder(jsonGoogleFolderData.getFolderName());
        } catch (Exception ex) {
            LOG.error("Error: " + ex.getMessage());
            throw new RestException(ex);
        }

        final HttpHeaders headers = new HttpHeaders();
        return new ResponseEntity<>(folderId, headers, HttpStatus.OK);
    }

    @RequestMapping(value = "/shareFolderWithLinkById/{folderId}", method = RequestMethod.GET)
    public @ResponseBody ResponseEntity<String> getGoogleDriveFolderShareableLinkById(
            @PathVariable(value = "folderId") String folderId
    ) throws RestException {
        String shareableLink;
        try {
            shareableLink = googleDriveManager.googleDriveCreateSharedLink(folderId);
        } catch (Exception ex) {
            LOG.error("Error: " + ex.getMessage());
            throw new RestException(ex);
        }

        final HttpHeaders headers = new HttpHeaders();
        return new ResponseEntity<>(shareableLink, headers, HttpStatus.OK);
    }
}
