package com.cropit.cropit.util;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentInformation;
import org.springframework.web.multipart.MultipartFile;

import java.util.Calendar;

public class FileUtils {

    private FileUtils() {
    }

    public static void setPDFNameFromFileName(PDDocument pdf, MultipartFile file) {
        PDDocumentInformation pdDocumentInformation = new PDDocumentInformation();
        pdDocumentInformation.setTitle(file.getOriginalFilename());
        pdDocumentInformation.setCreationDate(Calendar.getInstance());
        pdf.setDocumentInformation(pdDocumentInformation);
    }
}
