package com.visoft.entity;

public class JsonLogoData {

    String excelFilePath;
    String logoPath;
    Integer logoRow;
    Integer logoCell;
    Integer logoRow2;
    Integer logoCell2;

    public JsonLogoData() {
    }

    public JsonLogoData(String excelFilePath, String logoPath, Integer logoRow,  Integer logoCell, Integer logoRow2,  Integer logoCell2) {
        this.excelFilePath = excelFilePath;
        this.logoPath = logoPath;
        this.logoRow = logoRow;
        this.logoCell = logoCell;
        this.logoRow2 = logoRow2;
        this.logoCell2 = logoCell2;
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

    public Integer getLogoRow2() {
        return logoRow2;
    }

    public void setLogoRow2(Integer logoRow2) {
        this.logoRow2 = logoRow2;
    }

    public Integer getLogoCell2() {
        return logoCell2;
    }

    public void setLogoCell2(Integer logoCell2) {
        this.logoCell2 = logoCell2;
    }
}
