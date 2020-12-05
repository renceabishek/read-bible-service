package com.read.readbibleservice.config.properties;

import lombok.Data;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("email")
@Data
@Getter
public class EmailProperties {
    private String username;
    private String password;
}
