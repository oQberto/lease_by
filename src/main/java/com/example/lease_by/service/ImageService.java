package com.example.lease_by.service;

import org.springframework.core.io.Resource;

import java.io.InputStream;

public interface ImageService {

    void upload(String imagePath, InputStream content);

    Resource getImage(String image);
}
