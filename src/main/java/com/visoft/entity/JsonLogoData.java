package com.visoft.entity;

public class JsonLogoData {

    String excelFilePath;
    String logoPath;
    Integer logoRow;
    Integer logoCol;
    Integer logoRow2;
    Integer logoCol2;

    public JsonLogoData() {
    }

    public JsonLogoData(String excelFilePath, String logoPath, Integer logoRow,  Integer logoCol, Integer logoRow2,  Integer logoCol2) {
        this.excelFilePath = excelFilePath;
        this.logoPath = logoPath;
        this.logoRow = logoRow;
        this.logoCol = logoCol;
        this.logoRow2 = logoRow2;
        this.logoCol2 = logoCol2;
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

    public Integer getLogoCol() {
        return logoCol;
    }

    public void setLogoCol(Integer logoCol) {
        this.logoCol = logoCol;
    }

    public Integer getLogoRow2() {
        return logoRow2;
    }

    public void setLogoRow2(Integer logoRow2) {
        this.logoRow2 = logoRow2;
    }

    public Integer getLogoCol2() {
        return logoCol2;
    }

    public void setLogoCol2(Integer logoCell2) {
        this.logoCol2 = logoCell2;
    }
}
