package com.cropit.cropit.service.impl;

import com.cropit.cropit.service.CroppingService;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class CroppingPDFForMeesho implements CroppingService {

    @Value("${meesho.pdf.x}")
    private static float x;
    @Value("${meesho.pdf.y}")
    private static float y;
    @Value("${meesho.pdf.height}")
    private static float height;
    @Value("${meesho.pdf.width}")
    private static float width;
    @Value("${test}")
    private static float test;

    @Override
    public PDDocument cropFile(PDDocument pdf) {
        System.out.println("x is " + x);
        System.out.println("test is " + test);
        int pageCounter = 1;  //To ignore even pages, we maintain a counter
        for(PDPage page : pdf.getPages()){
            if(pageCounter % 2 == 0) {
                pageCounter++;
                continue;
            }
            page.setCropBox(new PDRectangle(x, y, width, height));
            pageCounter++;
        }
        return pdf;
    }
}
