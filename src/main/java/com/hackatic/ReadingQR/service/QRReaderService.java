package com.hackatic.ReadingQR.service;

import com.hackatic.ReadingQR.model.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class QRReaderService {

    private final RestTemplate restTemplate;

    @Autowired
    public QRReaderService(RestTemplate restTemplate){
        this.restTemplate = restTemplate;
    }

    public ApiResponse getImageURL(){
        String url = "https://hackattic.com/challenges/reading_qr/problem?access_token=b50d4a3dba3c9045";
        return restTemplate.getForObject(url, ApiResponse.class);
    }
}
