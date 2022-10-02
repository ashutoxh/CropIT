package com.cropit.cropit.controller;

import com.cropit.cropit.service.CroppingService;
import com.cropit.cropit.util.FileUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

@RestController
@RequestMapping("/crop/pdf")
public class PDFController {
    private static
    final Logger logger = LoggerFactory.getLogger(PDFController.class);

    @Autowired
    private CroppingService service;

    @PostMapping(value = "/v1/meesho", produces = MediaType.APPLICATION_PDF_VALUE, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void cropPDFForMeesho(@RequestParam("pdf") MultipartFile inputFile, HttpServletResponse response) throws IOException {
        PDDocument pdf = PDDocument.load(inputFile.getInputStream());
        FileUtils.setPDFNameFromFileName(pdf, inputFile);
        logger.info("Meesho: PDF provided is {} with no of pages {} and size in kb {}", pdf.getDocumentInformation().getTitle(), pdf.getPages().getCount(), inputFile.getSize() / 1000);
        pdf = service.cropFile(pdf);
        sendPDFResponse(pdf, response);
    }

    private void sendPDFResponse(PDDocument pdf, HttpServletResponse response) throws IOException {
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            pdf.save(outputStream);
            byte[] bytes = outputStream.toByteArray();
            response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=Cropped_" + pdf.getDocumentInformation().getTitle());
            response.setHeader(HttpHeaders.CONTENT_LENGTH, "" + bytes.length);
            response.getOutputStream().write(bytes);
        }
    }
}