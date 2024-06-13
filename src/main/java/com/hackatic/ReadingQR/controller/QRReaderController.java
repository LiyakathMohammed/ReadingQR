package com.hackatic.ReadingQR.controller;

import com.hackatic.ReadingQR.model.ApiResponse;
import com.hackatic.ReadingQR.service.QRReaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class QRReaderController {
    private final QRReaderService qrReaderService;

    @Autowired
    public QRReaderController(QRReaderService qrReaderService){
        this.qrReaderService = qrReaderService;
    }
    @GetMapping("/readQR")
    public ResponseEntity<ApiResponse> readQR(){
        ApiResponse response = qrReaderService.getImageURL();
        return ResponseEntity.ok(response);
    }
}
