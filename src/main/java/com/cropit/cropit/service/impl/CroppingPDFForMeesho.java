package com.cropit.cropit.service.impl;

import com.cropit.cropit.entity.TextPositionSequence;
import com.cropit.cropit.service.CroppingService;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class CroppingPDFForMeesho implements CroppingService {

    @Value("${meesho.pdf.x}")
    private float x;
    @Value("${meesho.pdf.y}")
    private float y;
    @Value("${meesho.pdf.height}")
    private float height;
    @Value("${meesho.pdf.width}")
    private float width;

    @Override
    public PDDocument cropFile(PDDocument pdf) throws IOException {
        PDDocument finalPDF = new PDDocument();
        finalPDF.setDocumentInformation(pdf.getDocumentInformation()); //Get proper doc name
        for (int pageNumber = 1; pageNumber <= pdf.getNumberOfPages(); pageNumber++) {
            PDFTextStripper textStripper = new PDFTextStripper();
            textStripper.setStartPage(pageNumber);
            textStripper.setEndPage(pageNumber);
            String content = textStripper.getText(pdf);
            if (content.contains("Fold Here")) {
                PDPage page = pdf.getPage(pageNumber - 1);
                PDRectangle pageDimention = page.getMediaBox();
                TextPositionSequence foldHereLocation = CroppingService.findWord(pdf, pageNumber, "Fold Here");
                page.setCropBox(new PDRectangle(x, pageDimention.getUpperRightY() - foldHereLocation.getY(), width, height));
                finalPDF.addPage(page);
            } else if (content.contains("Destination Code")) {
                PDPage page = pdf.getPage(pageNumber - 1);
                finalPDF.addPage(page);
            }
        }
        return finalPDF;
    }
}
