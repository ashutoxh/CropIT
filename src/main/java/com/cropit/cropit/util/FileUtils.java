package com.cropit.cropit.util;

import org.apache.pdfbox.multipdf.PDFMergerUtility;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentInformation;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;

public class FileUtils {

    private FileUtils() {
    }

    public static void setPDFNameFromFileName(PDDocument pdf, MultipartFile file) {
        PDDocumentInformation pdDocumentInformation = new PDDocumentInformation();
        pdDocumentInformation.setTitle(file.getOriginalFilename());
        pdDocumentInformation.setCreationDate(Calendar.getInstance());
        pdf.setDocumentInformation(pdDocumentInformation);
    }
    public static PDDocument mergeFilesIntoOne(List<MultipartFile> inputFileList) throws IOException {
        PDDocument mergedFile = new PDDocument();
        PDFMergerUtility mergerUtility = new PDFMergerUtility();
        for(MultipartFile file : inputFileList){
            mergerUtility.appendDocument(mergedFile, PDDocument.load(file.getInputStream()));
        }
        return mergedFile;
    }
}
