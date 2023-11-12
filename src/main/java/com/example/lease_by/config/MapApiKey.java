package com.example.lease_by.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Data
@Configuration
@ConfigurationProperties(prefix = "maps.api.key.geocode")
@PropertySource(value = "classpath:apiKeys.yml", factory = YamlPropertySourceFactory.class)
public class MapApiKey {
    private String google;
    private String yandex;
}
