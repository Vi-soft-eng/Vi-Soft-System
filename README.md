## Dependencies

1) Library: jodconverter-2.2.2.jar
You can find it in the project dir: lib/jodconverter-2.2.2.jar
The jar has to be copied to the local maven repository ..\.m2\repository\com\artofsolving\jodconverter\2.2.2\

2) OpenOfficePortable must be located there: C:\\OpenOfficePortable\\OpenOfficePortable.exe
You are allowed to install OpenOffice where you want. But remember to modify command
(property: "open.office.run.headless.mode.command") to run OpenOfficePortable.exe
in the project dir: src/main/resources/application.properties file.

## Information
The spring boot application is configured to the port 8050 (src/main/resources/application.properties)

#### Production mode
Build project with maven and deploy war archive to Tomcat server.

#### Development mode
Run spring-boot:run maven plugin.

#### Available endpoints
http://localhost:8050/                  (GET)  -- to check is the application running.

    Example: http://localhost:8050/

http://localhost:8050/convertBlobToPdf  (POST) -- gets blob (EXCEL file) converts it to the PDF and returns attachement.

    Example: http://localhost:8050/convertBlobToPdf
        params: file=Excel.xlsx (attachement)
                mimeType=application/vnd.openxmlformats-officedocument.spreadsheetml.sheet
                filePath=C:\\Temp\\Excel.xlsx

http://localhost:8050/convertExcelToPdf (POST) -- gets fileName, mimeType, filePath, converts it to the PDF and saves PDF.

    Example: http://localhost:8050/convertExcelToPdf
        params: fileName=Excel.xlsx
                mimeType=application/vnd.openxmlformats-officedocument.spreadsheetml.sheet
                filePath=C:\\Temp\\Excel.xlsx

    The PDF file will be saved there C:\\Temp\\Excel.pdf

http://localhost:8050/convertExcelToPdf (POST) -- gets JSON with needed data to convert Excel to PDF and saves PDF.

    Example: http://localhost:8050/convertExcelToPdf
        {"fileName":"excel.xlsx", "mimeType":"application/vnd.openxmlformats-officedocument.spreadsheetml.sheet","filePath":"C:\\FILES_DIR\\"}

    The PDF file will be saved there C:\\Temp\\Excel.pdf

http://localhost:8050/processExcelFitToOnePage (POST) -- gets JSON, modifies excel file.

    Example: http://localhost:8050/processExcelFitToOnePage
            {"fileName":"excel.xlsx", "mimeType":"application/vnd.openxmlformats-officedocument.spreadsheetml.sheet","filePath":"C:\\FILES_DIR\\"}

    The modified file will be saved there C:\\FILES_DIR\\excel.xlsx

http://localhost:8050/processExcelAddLogo (POST) -- gets JSON, modifies excel file by adding image in the cell.

    Example: http://localhost:8050/processExcelAddLogo
         body: {"excelFilePath":"C:\\FILES_DIR\\excel.xlsx","logoPath":"C:\\LOGO3EXCEL\\logo.png","logoRow":"1","logoCell":"8"}';

    The modified file will be saved there C:\\FILES_DIR\\excel.xlsx
    It will insert logo image in the whole excel cell (I2).
