package com.visoft.google;

import com.google.api.client.googleapis.batch.BatchRequest;
import com.google.api.client.googleapis.batch.json.JsonBatchCallback;
import com.google.api.client.googleapis.json.GoogleJsonError;
import com.google.api.client.http.HttpHeaders;
import com.google.api.client.util.DateTime;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;
import com.google.api.services.drive.model.Permission;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.*;

@Component
public class GoogleDriveManager {

    private final Logger LOG = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private GoogleDriveService driveService;

    public List<File> getRootFolderFiles() throws IOException {
        FileList result = driveService.getDriveService()
                .files()
                .list()
                .setQ("'root' in parents and trashed=false")
                .execute();
        List<File> files = result.getFiles();
        return files;
    }

    public List<File> getFolderFiles(String folderId) throws IOException {
        List<File> result = new ArrayList<>();
        Drive.Files.List request = driveService.getDriveService().files().list();

        do {
            try {
                FileList files = request
                        .setQ("'" + folderId + "' in parents and trashed=false")
                        .execute();

                result.addAll(files.getFiles());
                request.setPageToken(files.getNextPageToken());
            } catch (IOException e) {
                LOG.error("An error occurred: " + e);
                request.setPageToken(null);
            }
        } while (request.getPageToken() != null &&
                request.getPageToken().length() > 0);

        return result;
    }

    public List<File> getGoogleFoldersByName(String folderName, String rootFolderId) throws IOException {
        String query =
                String.format("name = '%s' and trashed=false and mimeType='application/vnd.google-apps.folder' and '%s' in parents",
                                folderName,
                                rootFolderId != null ? rootFolderId : "root");
        FileList result = driveService.getDriveService().files().list()
                .setQ(query)
                .execute();
        if (result.getFiles() != null) {
            List<File> file = result.getFiles();
            return file;
        }
        return null;
    }

    public File getGoogleFolderById(String folderId) throws IOException {
        File result = driveService.getDriveService().files()
                .get(folderId)
                .execute();
        if (result != null) {
            return result;
        }
        return null;
    }

    public String googleDriveCreateFolder(String folderName) throws IOException {
        File fileMetadata = new File();
        fileMetadata.setName(folderName);
        fileMetadata.setMimeType("application/vnd.google-apps.folder");
        File result = driveService.getDriveService().files()
                .create(fileMetadata)
                .execute();
        if (result != null) {
            return result.getId();
        }

        return null;
    }

    public String googleDriveDeleteFolder(String folderId) throws IOException {
        return null;
    }

    public String googleDriveCreateSharedLink(String folderId) throws IOException {

        JsonBatchCallback<Permission> callback = new JsonBatchCallback<Permission>() {
            @Override
            public void onFailure(GoogleJsonError e,
                                  HttpHeaders responseHeaders)
                    throws IOException {
                // Handle error
                LOG.error(e.getMessage());
            }

            @Override
            public void onSuccess(Permission permission,
                                  HttpHeaders responseHeaders)
                    throws IOException {
                LOG.info("Permission ID: " + permission.getId());
            }
        };

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, 14);

        BatchRequest batch = driveService.getDriveService().batch();
        Permission userPermission = new Permission()
                .setType("anyone")
                .setRole("reader")
                .setExpirationTime(new DateTime(calendar.getTime()));
        driveService.getDriveService().permissions().create(folderId, userPermission)
                .setFields("id")
                .queue(batch, callback);

        batch.execute();
        return getShareableLink(folderId);
    }

    private String getShareableLink(String folderId) throws IOException {
        Drive.Files files = driveService.getDriveService().files();
        File file = files.get(folderId).setFields("id, webViewLink").execute();

        if (file != null) {
            return file.getWebViewLink();
        }

        return null;
    }
}