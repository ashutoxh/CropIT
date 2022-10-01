package com.cropit.cropit.controller;

import com.cropit.cropit.service.CroppingService;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentInformation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/crop/pdf")
public class PDFController {
    private static
    final Logger logger = LoggerFactory.getLogger(PDFController.class);

    @Autowired
    CroppingService service;

    @PostMapping(value = "/v1/meesho", produces = MediaType.APPLICATION_PDF_VALUE)
    public PDDocument cropPDFForMeesho(@RequestParam("pdf") MultipartFile inputFile) throws IOException {
        PDDocument pdf = PDDocument.load(inputFile.getInputStream());
        logger.info("Meesho: PDF provided is {} with no of pages {} ", inputFile.getName(), pdf.getPages().getCount());
        return service.cropFile(pdf);
    }
}
