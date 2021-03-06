package com.read.readbibleservice.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "firebase.storage")
public class FirebaseStorageProperties {

  private String profile;
}
