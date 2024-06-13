package com.hackatic.ReadingQR.controller;

import com.google.zxing.NotFoundException;
import com.hackatic.ReadingQR.model.ApiResponse;
import com.hackatic.ReadingQR.service.QRReaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.MalformedURLException;

@RestController
public class QRReaderController {
    private final QRReaderService qrReaderService;

    @Autowired
    public QRReaderController(QRReaderService qrReaderService){
        this.qrReaderService = qrReaderService;
    }
    @GetMapping("/readQR")
    public ResponseEntity<String> readQR() throws NotFoundException, IOException {
        try {
            ApiResponse response = qrReaderService.getImageURL();
            return ResponseEntity.ok(qrReaderService.getStringFromQR(response.getImage_url()));
        }
        catch (MalformedURLException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Invalid URL format: " + e.getMessage());
        } catch (IOException e) {
            if (e.getMessage().contains("404")) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Image not found at the provided URL.");
            } else if (e.getMessage().contains("503")) {
                return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                        .body("Service temporarily unavailable. Please try again later.");
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Error downloading image: " + e.getMessage());
            }
        }
    }
}
