package com.read.readbibleservice.config.properties;

import lombok.Data;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("security.url")
@Data
@Getter
public class UrlProperties {
    private String integration;
    private String fe;
}
