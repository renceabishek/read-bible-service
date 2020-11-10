package com.read.readbibleservice.config.properties;

import lombok.Data;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("security.jwt")
@Data
@Getter
public class JwtProperties {

    private String signingKey;
    private String accessTokenValiditySeconds;
    private String origins;
}
