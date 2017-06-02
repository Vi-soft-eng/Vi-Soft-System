package com.visoft.entity;

public class JsonLogoData {

    String excelFilePath;
    String logoPath;
    Integer logoRow;
    Integer logoCell;

    public JsonLogoData() {
    }

    public JsonLogoData(String excelFilePath, String logoPath, Integer logoRow, Integer logoCell) {
        this.excelFilePath = excelFilePath;
        this.logoPath = logoPath;
        this.logoRow = logoRow;
        this.logoCell = logoCell;
    }

    public String getExcelFilePath() {
        return excelFilePath;
    }

    public void setExcelFilePath(String excelFilePath) {
        this.excelFilePath = excelFilePath;
    }

    public String getLogoPath() {
        return logoPath;
    }

    public void setLogoPath(String logoPath) {
        this.logoPath = logoPath;
    }

    public Integer getLogoRow() {
        return logoRow;
    }

    public void setLogoRow(Integer logoRow) {
        this.logoRow = logoRow;
    }

    public Integer getLogoCell() {
        return logoCell;
    }

    public void setLogoCell(Integer logoCell) {
        this.logoCell = logoCell;
    }

}
