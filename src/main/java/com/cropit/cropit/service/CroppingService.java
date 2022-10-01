package com.cropit.cropit.service;

import org.apache.pdfbox.pdmodel.PDDocument;

public interface CroppingService {
    PDDocument cropFile(PDDocument file);
}
