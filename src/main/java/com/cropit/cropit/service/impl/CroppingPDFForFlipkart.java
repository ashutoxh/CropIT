package com.cropit.cropit.service.impl;

import com.cropit.cropit.entity.TextPositionSequence;
import com.cropit.cropit.service.CroppingService;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@Qualifier("Flipkart")
public class CroppingPDFForFlipkart implements CroppingService {

    @Value("${flipkart.pdf.x}")
    private float x;
    @Value("${flipkart.pdf.y}")
    private float y;
    @Value("${flipkart.pdf.height}")
    private float height;
    @Value("${flipkart.pdf.width}")
    private float width;

    @Value("${flipkart.pdf.offset}")
    private float offset;

    private static String WORD_TO_FIND = "Tax Invoice";

    @Override
    public PDDocument cropFile(PDDocument pdf) throws IOException {
        PDDocument finalPDF = new PDDocument();
        finalPDF.setDocumentInformation(pdf.getDocumentInformation()); //Get proper doc name
        for (int pageNumber = 1; pageNumber <= pdf.getNumberOfPages(); pageNumber++) {
            PDFTextStripper textStripper = new PDFTextStripper();
            textStripper.setStartPage(pageNumber);
            textStripper.setEndPage(pageNumber);
            String content = textStripper.getText(pdf);
            if (content.contains(WORD_TO_FIND)) {
                PDPage page = pdf.getPage(pageNumber-1);
                PDRectangle pageDimention = page.getMediaBox();
                TextPositionSequence foldHereLocation = CroppingService.findWord(pdf, pageNumber, WORD_TO_FIND);
                page.setCropBox(new PDRectangle(x, pageDimention.getUpperRightY() - foldHereLocation.getY() + offset , width, height));
                finalPDF.addPage(page);
            }
        }
        return finalPDF;
    }
}
