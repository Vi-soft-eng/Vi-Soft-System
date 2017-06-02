package com.visoft.entity.jod.converter;

import java.io.*;
import java.net.ConnectException;

import com.artofsolving.jodconverter.*;
import com.artofsolving.jodconverter.openoffice.connection.OpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.connection.SocketOpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.converter.OpenOfficeDocumentConverter;
import com.visoft.util.cmd.OpenOfficeUtil;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class JodConverter {

    private final Logger LOG = LoggerFactory.getLogger(this.getClass());
    private DocumentFormatRegistry documentFormatRegistry = new DefaultDocumentFormatRegistry();

    @Autowired
    OpenOfficeUtil openOfficeUtil;

    public byte[] convertToPdf(String inputFileName, String mimeType, byte[] inputByteArray) throws Exception {

        openOfficeUtil.runOpenOffice();

        String outputFileName = FilenameUtils.getBaseName(inputFileName) + ".pdf";
        String outputMimeType = "application/pdf";

        return convert(inputFileName, inputByteArray, outputFileName);
    }

    public byte[] convertXlsToXlsx(String inputFileName, String mimeType, byte[] inputByteArray) throws Exception {

        String outputFileName = FilenameUtils.getBaseName(inputFileName) + ".xlsx";
        String outputMimeType = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";

        return convert(inputFileName, inputByteArray, outputFileName);
    }

    private byte[] convert(String inputFileName, byte[] input, String outputFileName) throws Exception {

        openOfficeUtil.runOpenOffice();

        DocumentFormat inputFormat =
                documentFormatRegistry.getFormatByFileExtension(FilenameUtils.getExtension(inputFileName));
        DocumentFormat outputFormat =
                documentFormatRegistry.getFormatByFileExtension(FilenameUtils.getExtension(outputFileName));
        if(inputFormat == null) {
            throw new IllegalArgumentException("unknown document format for file: " + inputFileName);
        }
        if(outputFormat == null) {
            throw new IllegalArgumentException("unknown document format for file: " + outputFileName);
        }
        try {
            // Create InputStream and ByteArrayOutputStream
            InputStream inputStream = new BufferedInputStream(new ByteArrayInputStream(input));
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

            // Connect to an OpenOffice.org instance running on port 8100
            OpenOfficeConnection connection = new SocketOpenOfficeConnection(8100);
            connection.connect();
            // convert
            DocumentConverter converter = new OpenOfficeDocumentConverter(connection);
            converter.convert(inputStream, inputFormat, outputStream, outputFormat);
            connection.disconnect();

            return outputStream.toByteArray();

        } catch (ConnectException e) {
            LOG.error("Connection not available: " + e.getMessage());
        } catch (IOException e) {
            LOG.error("File error: " + e.getMessage());
        } catch (Exception e) {
            LOG.error("Error: " + e.getMessage(), e);
            throw new Exception(e.getMessage());
        }
        return null;
    }

    public void convertToPdfAndSave(File input, String outputFileName) throws Exception {

        openOfficeUtil.runOpenOffice();

        try {
            File inputFile = input;
            String destFileName = outputFileName;
            // Connect to an OpenOffice.org instance running on port 8100
            OpenOfficeConnection connection = new SocketOpenOfficeConnection(8100);
            connection.connect();
            File outputFile = new File(destFileName);
            // convert
            DocumentConverter converter = new OpenOfficeDocumentConverter(connection);
            converter.convert(inputFile, outputFile);
            // close the connection
            connection.disconnect();
        }
        catch (Exception e) {
            LOG.error("Error: " + e.getMessage(), e);
            throw new Exception(e.getMessage());
        }

    }
}
