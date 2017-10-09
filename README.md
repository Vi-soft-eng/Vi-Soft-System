## Dependencies

mvn install:install-file -Dfile=ojdbc7.jar  -DgroupId=com.oracle -DartifactId=ojdbc7 -Dversion=12.1.0.1 -Dpackaging=jar

mvn install:install-file -Dfile=jodconverter-2.2.2.jar  -DgroupId=com.artofsolving -DartifactId=jodconverter-2.2.2 -Dversion=2.2.2 -Dpackaging=jar

1) Library: jodconverter-2.2.2.jar
You can find it in the project dir: lib/jodconverter-2.2.2.jar
The jar has to be copied to the local maven repository ..\.m2\repository\com\artofsolving\jodconverter\2.2.2\

2) Library: ojdbc7-12.1.0.1.jar
You can find it in the project dir: lib/ojdbc7-12.1.0.1.jar
The jar has to be copied to the local maven repository ..\.m2\repository\com\oracle\ojdbc7\12.1.0.1\ojdbc7-12.1.0.1.jar

3) OpenOfficePortable must be located there: C:\\OpenOfficePortable\\App\\openoffice\\program\\
You are allowed to install OpenOffice where you want. But remember to modify command
(property: "open.office.run.headless.mode.command") to run soffice.exe
in the project dir: src/main/resources/application.properties file.

4) The pom.xml file uses profiles configured in settings.xml that is located in .m2 folder.
```xml
<profiles>
    <profile>
        <id>visoft.prod</id>
        <properties>
            <db.url>url</db.url>
            <db.username>username</db.username>
            <db.password>password</db.password>
            <google.drive.default.folder.id>folder_id</google.drive.default.folder.id>
            <google.drive.service.account.user>service_account_user</google.drive.service.account.user>
            <google.api.client.id>client_id</google.api.client.id>
            <google.api.secret>secret</google.api.secret>
            <google.api.callback.uri>http://localhost:8050/visoftsystem/googleAuth/OAuth2v1/</google.api.callback.uri>
        </properties>
    </profile>
    <profile>
        <id>visoft.dev</id>
        <properties>
                <db.url>url</db.url>
                <db.username>username</db.username>
                <db.password>password</db.password>
                <google.drive.default.folder.id>folder_id</google.drive.default.folder.id>
                <google.drive.service.account.user>service_account_user</google.drive.service.account.user>
                <google.api.client.id>client_id</google.api.client.id>
                <google.api.secret>secret</google.api.secret>
                <google.api.callback.uri>http://localhost:8050/googleAuth/OAuth2v1/</google.api.callback.uri>
            </properties>
    </profile>
</profiles>
```
 
## Information
The spring boot application is configured to the port 8050 (src/main/resources/application.properties)

#### Production mode
Build project with maven and deploy war archive to Tomcat server (C:\Program Files\Apache Software Foundation\Tomcat 8.5\webapps).

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

http://localhost:8050/processExcel/changeFile/fitToOnePage (POST) -- gets JSON, modifies excel file.

    Example: http://localhost:8050/processExcelFitToOnePage
            {"fileName":"excel.xlsx", "mimeType":"application/vnd.openxmlformats-officedocument.spreadsheetml.sheet","filePath":"C:\\FILES_DIR\\"}

    The modified file will be saved there C:\\FILES_DIR\\excel.xlsx

http://localhost:8050/processExcel/changeFile/addLogo (POST) -- gets JSON, modifies excel file by adding image in the cell.

    Example: http://localhost:8050/processExcelAddLogo
         body: {"excelFilePath":"C:\\FILES_DIR\\excel.xlsx","logoPath":"C:\\LOGO3EXCEL\\logo.png","logoRow":"1","logoCell":"8","logoRow2":"2","logoCell2":"9"}';

    The modified file will be saved there C:\\FILES_DIR\\excel.xlsx
    It will insert logo image in the whole excel cell (I2).

http://localhost:8050/processExcel/changeFile/changeDirectionRightToLeft (POST) -- gets JSON, modifies excel file by changing view direction right to left.

    Example: http://localhost:8050/processExcelChangeDirectionRightToLeft
            {"fileName":"excel.xlsx", "mimeType":"application/vnd.openxmlformats-officedocument.spreadsheetml.sheet","filePath":"C:\\FILES_DIR\\"}

    The modified file will be saved there C:\\FILES_DIR\\excel.xlsx
    
http://localhost:8050/processExcel/uploadFile/fitToOnePage (POST) -- gets JSON and binary file, modifies excel file by fitting data to one page.

    Example: http://localhost:8050/processExcel/uploadFile/fitToOnePage
            {"fileName":"excel.xlsx", "mimeType":"application/vnd.openxmlformats-officedocument.spreadsheetml.sheet","filePath":""}
            binary file: BLOB
    The app returns a modified file in a http response.
    
http://localhost:8050/processExcel/uploadFile/addLogo (POST) -- gets JSON and binary file, modifies excel file by adding image in the cell.

    Example: http://localhost:8050/processExcel/uploadFile/addLogo
         {"excelFilePath":"C:\\FILES_DIR\\excel.xlsx","logoPath":"C:\\LOGO3EXCEL\\logo.png","logoRow":"1","logoCell":"8","logoRow2":"2","logoCell2":"9"}';
            binary file: BLOB
    The app returns a modified file in a http response.

http://localhost:8050/processExcel/uploadFile/changeDirectionRightToLeft (POST) -- gets JSON and binary file, modifies excel file by changing view direction right to left.

    Example: http://localhost:8050/processExcel/uploadFile/changeDirectionRightToLeft
            {"fileName":"excel.xlsx", "mimeType":"application/vnd.openxmlformats-officedocument.spreadsheetml.sheet","filePath":""}
            binary file: BLOB
    The app returns a modified file in a http response.

### Google API

To work with Google API is necessary to have prepared settings in Google APIs console.
The visoftsystem web application uses OAuth2 authentication and requires getting following properties:   
   - google.drive.default.folder.id - internal google folder id
   - google.drive.service.account.user - google service account
   - google.api.client.id -  OAuth 2.0 client id
   - google.api.secret - OAuth 2.0 secret
   - google.api.callback.uri - call back url (do not change this property)

#### How to configure visoftsystem web application Google API to use particular Google Account and work with its Drive

1) Open: http://localhost:8050/googleAuth/getUrlToAuth
2) Click on button 'Login with Google'
3) Chose or login with appropriate Google Account 
4) Allow access to manage Google Drive
5) You will be redirected to (google.api.callback.uri) http://localhost:8050/googleAuth/OAuth2v1/ to get authenticated user info