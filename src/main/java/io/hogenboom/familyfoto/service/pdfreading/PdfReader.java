package io.hogenboom.familyfoto.service.pdfreading;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.File;
import java.io.IOException;

public class PdfReader {
    public void readPdf(String file) {
        try (PDDocument document = PDDocument.load(new File(file))) {


            if (!document.isEncrypted()) {
                PDFTextStripper stripper = new PDFTextStripper();
                String text = stripper.getText(document);
                System.out.println("Text:" + text);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
