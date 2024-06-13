package com.hackatic.ReadingQR.service;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import com.hackatic.ReadingQR.model.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

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

    public String getStringFromQR(String url) throws IOException, NotFoundException {
        BufferedImage bufferedImage = downloadImage(url);
        if(bufferedImage == null){
            throw new IOException("Could not download image from URL");
        }
        return decodeQR(bufferedImage);
    }

    private String decodeQR(BufferedImage bufferedImage) throws NotFoundException {
        try {
            BinaryBitmap binaryBitmap = new BinaryBitmap(new HybridBinarizer(new BufferedImageLuminanceSource(bufferedImage)));
            Result result = new MultiFormatReader().decode(binaryBitmap);
            return result.getText();
        }
        catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public BufferedImage downloadImage(String url) throws IOException {
        try(InputStream inputStream = new URL(url).openStream()){
            return ImageIO.read(inputStream);
        }
    }


}
