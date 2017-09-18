package com.visoft.google.json;

public class JsonGoogleFolderData {

    private String folderName;
    private String folderAdditionalParam;
    private String folderIdToCreate;

    public JsonGoogleFolderData(String folderName, String folderAdditionalParam, String folderIdToCreate) {
        this.folderName = folderName;
        this.folderAdditionalParam = folderAdditionalParam;
        this.folderIdToCreate = folderIdToCreate;
    }

    public JsonGoogleFolderData() {
    }

    public String getFolderName() {
        return folderName;
    }

    public String getFolderAdditionalParam() {
        return folderAdditionalParam;
    }

    public String getFolderIdToCreate() {
        return folderIdToCreate;
    }
}
