package com.example.lease_by.service.impl;

import com.example.lease_by.service.ImageService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static java.nio.file.StandardOpenOption.CREATE;
import static java.nio.file.StandardOpenOption.TRUNCATE_EXISTING;

@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {

    @Value("src/main/resources/static/image/rental")
    private final String bucket;

    @Override
    @SneakyThrows
    public void upload(String imagePath, InputStream content) {
        Path fullImagePath = Path.of(bucket, imagePath);

        try(content) {
            Files.createDirectories(fullImagePath.getParent());
            Files.write(fullImagePath, content.readAllBytes(), CREATE, TRUNCATE_EXISTING);
        }
    }

    @Override
    @SneakyThrows
    public Resource getImage(String image) {
        Path path = Paths.get(getClass().getResource("/static/image/rental/" + image).toURI());

        return new ByteArrayResource(Files.readAllBytes(path));
    }
}
